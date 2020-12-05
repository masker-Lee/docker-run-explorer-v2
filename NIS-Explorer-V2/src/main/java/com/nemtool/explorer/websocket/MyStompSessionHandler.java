package com.nemtool.explorer.websocket;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.stomp.ConnectionLostException;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nemtool.explorer.init.BlockDataFetch;
import com.nemtool.explorer.pojo.Blocks;
import com.nemtool.explorer.pojo.Mosaics;
import com.nemtool.explorer.pojo.Unconfirmedtransactions;
import com.nemtool.explorer.scheduled.BlockScheduled;
import com.nemtool.explorer.service.BlocksService;
import com.nemtool.explorer.service.MosaicsService;
import com.nemtool.explorer.service.NisService;
import com.nemtool.explorer.service.UnconfirmedTransactionService;
import com.nemtool.explorer.serviceH2mem.AccountH2MemService;
import com.nemtool.explorer.util.ExceptionsUtil;
import com.nemtool.explorer.util.HexEncoder;
import com.nemtool.explorer.util.TimeUtil;
import com.nemtool.explorer.websocket.ConnectNISWebSocket;

/** 
 * @Description: update block height websocket handler
 * @author Masker
 * @date 2020.06.30
 */ 
@Service
public class MyStompSessionHandler implements StompSessionHandler {
	
	private final static Logger logger = LoggerFactory.getLogger(MyStompSessionHandler.class);

	private boolean isConnected;
	
	private boolean isWebSocketRunning = false;
	
	@Autowired
	private BlocksService blocksService;
	
	@Autowired
	private ConnectNISWebSocket connectNISWebSocket;
	
	@Autowired
	private BlockDataFetch blockDataFetch;
	
	@Autowired
	private BlockScheduled blockScheduled;
	
	@Autowired
	private AccountH2MemService accountH2MemService;
	
	@Autowired
	private MosaicsService mosaicsService;
	
	@Autowired
	private UnconfirmedTransactionService unconService;
	
	@Autowired 
	private WebSocketService webSocketService;
	
	@Autowired
	private NisService nisService;
	
	public boolean getStatus() {
		return isWebSocketRunning;
	}
	
	@Override
	public Type getPayloadType(StompHeaders arg0) {
		return String.class;
	}

	@Override
	public void handleFrame(StompHeaders arg0, Object arg1) { }

	@Override
	public void afterConnected(StompSession session, StompHeaders arg1) {
	
		//handle new blocks 
		session.subscribe("/blocks/new", new StompFrameHandler() {
			public Type getPayloadType(StompHeaders stompHeaders) {
				return String.class;
	        }
	        public void handleFrame(StompHeaders stompHeaders, Object result) {
	        	System.out.println("/blocks/new receive data");
	        	
	        	//check blockScheduled running status
	        	while (blockScheduled.getStatus()) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						logger.info("websocket sleep error:" + ExceptionsUtil.getExceptionAllinformation(e));
					}
				}
	        	isWebSocketRunning = true;
	        	//blockscheduled is not running, websocket update block
	        	int heightWebSocket = JSON.parseObject(result.toString()).getIntValue("height");
	        	//get block from DB
	        	Blocks maxBlocksFromDB = blocksService.maxBlocksFromDB();
	        	if(heightWebSocket > maxBlocksFromDB.getHeight()) {
	        		try {
	        			logger.info("websocket update block:" + heightWebSocket);
						blockDataFetch.loadAllBlocks(maxBlocksFromDB.getHeight());
					} catch (Exception e) {
						isWebSocketRunning = false;
						logger.info("websocket error:" + ExceptionsUtil.getExceptionAllinformation(e));
					}
	        	}
	        	isWebSocketRunning = false;
	        	try {
	        		webSocketService.emitBlock(result.toString());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
	        }
		});
		
		//handle block, Mosaic websocket
		session.subscribe("/blocks", new StompFrameHandler() {
			public Type getPayloadType(StompHeaders stompHeaders) {
				return String.class;
	        }
	        public void handleFrame(StompHeaders stompHeaders, Object result) {
	        	System.out.println("/blocks receive data");
	        	if (result == null || result.toString().isEmpty()) {
					return;
				}
	        	JSONObject block = JSON.parseObject(result.toString());
	        	if (block == null || !block.containsKey("height") || !block.containsKey("signature")) {
					return;
				}
	        	//mosaic ws
	        	JSONArray blockList = blocksService.blockListFromNis(block.getIntValue("height") - 1);
	        	for (int i = 0; i < blockList.size(); i++) {
					JSONObject item = blockList.getJSONObject(i);
					if (!item.containsKey("block") || !item.containsKey("txes")) {
						return;
					}
					// query given height block
					if (item.getJSONObject("block").getIntValue("height") != block.getIntValue("height")) {
						return;
					}
					JSONArray txesArray = item.getJSONArray("txes");
					JSONArray r_mosaicArr = new JSONArray();
					List<String> mosaicQueryParams = new ArrayList<String>();
					for (int j = 0; j < txesArray.size(); j++) {
						JSONObject itemTx = txesArray.getJSONObject(j);
						if (itemTx == null || !item.containsKey("tx") || !itemTx.containsKey("hash")) {
							return;
						}
						JSONObject tx = itemTx.getJSONObject("tx");
						tx.put("height", block.getIntValue("height"));
						tx.put("hash", itemTx.getString("hash"));
						tx.put("index", j);
						r_mosaicArr.add(getMosaicFromTX(tx));
						JSONArray mosaicsList = new JSONArray();
						if (tx.containsKey("mosaics")) {
							mosaicsList.addAll(tx.getJSONArray("mosaics"));
						}
						if (tx.containsKey("otherTrans") && tx.getJSONObject("otherTrans").containsKey("mosaics")) {
							mosaicsList.addAll(tx.getJSONObject("otherTrans").getJSONArray("mosaics"));
						}
						for (int k = 0; k < mosaicsList.size(); k++) {
							JSONObject m = mosaicsList.getJSONObject(k);
							if (!m.containsKey("mosaicId") || !m.getJSONObject("mosaicId").containsKey("namespaceId") || !m.getJSONObject("mosaicId").containsKey("name")) {
								continue;
							}
							mosaicQueryParams.add(m.getJSONObject("mosaicId").getString("namespaceId") + ":" + m.getJSONObject("mosaicId").getString("name"));
						}
					}
					if (r_mosaicArr.size() == 0) {
						return ;
					}
					if (r_mosaicArr.size() != 0 && mosaicQueryParams.size() == 0) {
						try {
			        		webSocketService.emitBlock(JSON.toJSONString(r_mosaicArr));
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
					// query mosaics info and format the quantity
					if (mosaicQueryParams.size() > 0) {
						for (int j = 0; j < r_mosaicArr.size(); j++) {
							JSONObject m = r_mosaicArr.getJSONObject(j);
							if (!m.containsKey("mosaicId") || !m.getJSONObject("mosaicId").containsKey("namespaceId") || !m.getJSONObject("mosaicId").containsKey("name")) {
								continue;
							}
							Mosaics mosaic = mosaicsService.findByMosaicId(m.getJSONObject("mosaicId").getString("namespaceId") + ":" + m.getJSONObject("mosaicId").getString("name"));
							m.put("div", mosaic.getDivisibility());
						}
						try {
			        		webSocketService.emitMosaic(JSON.toJSONString(r_mosaicArr));
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				}
	        	
	        	//unconfirtransaction ws, save new transaction into DB
	        	String reqData = "{\"height\":" + block.getIntValue("height") + "}";
	        	String blockAtPublic = nisService.blockAtPublic(reqData);
	        	if (!blockAtPublic.isEmpty()) {
					JSONObject data = JSON.parseObject(blockAtPublic);
					if (data == null || !data.containsKey("transactions")) {
						return;
					}
					JSONArray transactions = data.getJSONArray("transactions");
					for (int i = 0; i < transactions.size(); i++) {
						JSONObject transaction = transactions.getJSONObject(i);
						if (transaction.containsKey("signature")) {
							int deleBySignature = unconService.deleBySignature(transaction.getString("signature"));
							if (deleBySignature > 0) {
								JSONObject item = new JSONObject();
								item.put("signature", transaction.getString("signature"));
								emit("remove", item);
							}
						}
					}
					// emit to client
					if (transactions.size() > 0) {
						try {
							webSocketService.emitTransaction("1");
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
	        	removeExpiredUnconfirmedTransactionFromDB();
	        }
		});
		
		//handle unconfirmed
		session.subscribe("/unconfirmed", new StompFrameHandler() {
			public Type getPayloadType(StompHeaders stompHeaders) {
				return String.class;
	        }
	        public void handleFrame(StompHeaders stompHeaders, Object result) {
	        	System.out.println("/unconfirmed receive data");
	        	if (result == null || result.toString().isEmpty()) {
					return;
				}
	        	JSONObject unconfirmed = JSON.parseObject(result.toString());
	        	if (unconfirmed == null || !unconfirmed.containsKey("signature") || !unconfirmed.containsKey("type") || !unconfirmed.containsKey("signer")) {
					return;
				}
	        	String signer = accountH2MemService.publicKeyToAddress(unconfirmed.getString("signer"));
	        	unconfirmed.put("signer", signer);
	        	//message
	        	if (unconfirmed.containsKey("message") && unconfirmed.getJSONObject("message").containsKey("type") && unconfirmed.getJSONObject("message").getIntValue("type") == 1) {
					unconfirmed.getJSONObject("message").put("payload", HexEncoder.decodeMessage(unconfirmed.getJSONObject("message").getString("payload")));
				}
	        	if (unconfirmed.containsKey("otherTrans") && unconfirmed.getJSONObject("otherTrans").containsKey("message") && 
	        			unconfirmed.getJSONObject("otherTrans").getJSONObject("message").containsKey("type") && unconfirmed.getJSONObject("otherTrans").getJSONObject("message").getIntValue("type") == 1) {
	        		unconfirmed.getJSONObject("otherTrans").getJSONObject("message").put("payload", HexEncoder.decodeMessage(unconfirmed.getJSONObject("otherTrans").getJSONObject("message").getString("payload")));
				}
	        	// initialize multisig transaction
	        	if (unconfirmed.getIntValue("type")==4100 && unconfirmed.containsKey("otherTrans") && unconfirmed.containsKey("signatures")) {
	        		// update inner transaction hash
	        		String data = nisService.accountUnconfirmedTransactions(signer);
	        		if (data.isEmpty()) {
						return;
					}
	        		JSONObject dataObject = JSON.parseObject(data);
	        		if (!dataObject.containsKey("data")) {
						return;
					}
	        		JSONArray dataArray = dataObject.getJSONArray("data");
	        		for (int i = 0; i < dataArray.size(); i++) {
						JSONObject tx = dataArray.getJSONObject(i);
						if (tx.containsKey("meta") && tx.getJSONObject("meta").containsKey("data") && tx.containsKey("transaction") && 
								tx.getJSONObject("transaction").containsKey("signature") && tx.getJSONObject("transaction").getString("signature").equals(unconfirmed.getString("signature"))) {
							unconfirmed.put("otherHash", tx.getJSONObject("meta").getString("data"));
							break;
						}
					}
	        		if (!unconfirmed.containsKey("otherHash") || !unconfirmed.getJSONObject("otherTrans").containsKey("signer")) {
						return;
					}
	        		// update cosignatories infomation
	        		String otherTransSigner = accountH2MemService.publicKeyToAddress(unconfirmed.getJSONObject("otherTrans").getString("signer"));
	        		unconfirmed.getJSONObject("otherTrans").put("sender", otherTransSigner);
	        		String accountData = nisService.accountByAddress(otherTransSigner);
	        		if (accountData.isEmpty()) {
						return;
					}
	        		JSONObject accountDataObject = JSON.parseObject(accountData);
	        		if (!accountDataObject.containsKey("meta") || !accountDataObject.containsKey("account")) {
						return;
					}
	        		unconfirmed.put("minSigned", 0);
	        		if (accountDataObject.getJSONObject("account").containsKey("multisigInfo") && accountDataObject.getJSONObject("account").getJSONObject("multisigInfo").containsKey("minCosignatories")) {
						unconfirmed.put("minSigned", accountDataObject.getJSONObject("account").getJSONObject("multisigInfo").getIntValue("minCosignatories"));
					}
	        		unconfirmed.put("signed", signer);
	        		unconfirmed.put("signedDate", unconfirmed.getLongValue("timeStamp"));
	        		JSONArray accountDataArray = accountDataObject.getJSONObject("meta").getJSONArray("cosignatories");
	        		for (int i = 0; i < accountDataArray.size(); i++) {
	        			JSONObject accountData1 = accountDataArray.getJSONObject(i);
	        			if (signer.equals(accountData1.getString("address"))) {
							if (unconfirmed.containsKey("unSigned")) {
								unconfirmed.put("unSigned", unconfirmed.getJSONArray("unSigned").add(accountData1.getString("address")));
							} else {
								JSONArray jsonArray = new JSONArray();
								jsonArray.add(accountData1.getString("address"));
								unconfirmed.put("unSigned", jsonArray);
							}
						}
					}
	        		// handle aggregate modification transaction
	        		if (unconfirmed.getJSONObject("otherTrans").containsKey("modifications")) {
	        			JSONArray modifications = unconfirmed.getJSONObject("otherTrans").getJSONArray("modifications");
	        			for (int i = 0; i < modifications.size(); i++) {
							if (!modifications.getJSONObject(i).containsKey("cosignatoryAccount")) {
								continue;
							}
							modifications.getJSONObject(i).put("cosignatoryAccount", accountH2MemService.publicKeyToAddress(modifications.getJSONObject(i).getString("cosignatoryAccount")));
						}
					}
	        		Unconfirmedtransactions unconfirmedtransactions = new Unconfirmedtransactions();
	        		unconfirmedtransactions.setSignature(unconfirmed.getString("signature"));
	        		unconfirmedtransactions.setTimestamp(unconfirmed.getLongValue("timeStamp"));
	        		unconfirmedtransactions.setDeadline(unconfirmed.getLongValue("deadline"));
	        		unconfirmedtransactions.setOtherhash(unconfirmed.getString("otherHash"));
	        		unconfirmedtransactions.setDetail(JSON.toJSONString(unconfirmed));
	        		unconService.save(unconfirmedtransactions);
	        		emit("add", unconfirmed);
				} else if(unconfirmed.getIntValue("type") == 4098 && unconfirmed.containsKey("otherHash") && 
						unconfirmed.getJSONObject("otherHash").containsKey("data") && unconfirmed.containsKey("signer")){// cosign multisig transaction
					List<Unconfirmedtransactions> unconfirmedtransactions = unconService.findByOtherHash(unconfirmed.getJSONObject("otherHash").getString("data"));
					long signedDate = unconfirmed.getLongValue("timeStamp");
					for (int i = 0; i < unconfirmedtransactions.size(); i++) {
						Unconfirmedtransactions item = unconfirmedtransactions.get(i);
						unconfirmed = JSON.parseObject(item.getDetail());
						if (unconfirmed == null) {
							return;
						}
						unconfirmed.put("signed", signer);
						unconfirmed.put("signedDate", signedDate);
						JSONArray newUnSigned = new JSONArray();
						for (int j = 0; j < unconfirmed.getJSONArray("unSigned").size(); j++) {
							if (!signer.equals(unconfirmed.getJSONArray("unSigned").get(j).toString())) {
								newUnSigned.add(unconfirmed.getJSONArray("unSigned").get(j).toString());
							}
						}
						unconfirmed.put("unSigned", newUnSigned);
						Unconfirmedtransactions unconfirmedtransactions2 = new Unconfirmedtransactions();
						unconfirmedtransactions2.setDetail(JSON.toJSONString(unconfirmed));
						unconService.updateBySignature(item.getSignature(), unconfirmedtransactions2);
						emit("update", unconfirmed);
					}
				} else if (unconfirmed.getIntValue("type") == 4097) {
					//convert to be multisig account (aggregate modification transaction)
					if (unconfirmed.containsKey("modifications")) {
						for (int i = 0; i < unconfirmed.getJSONArray("modifications").size(); i++) {
							if (!unconfirmed.getJSONArray("modifications").getJSONObject(i).containsKey("cosignatoryAccount")) {
								continue;
							}
							unconfirmed.getJSONArray("modifications").getJSONObject(i).put("cosignatoryAccount", accountH2MemService.publicKeyToAddress(unconfirmed.getJSONArray("modifications").getJSONObject(i).getString("cosignatoryAccount")));
						}
					}
					// save into DB
					Unconfirmedtransactions unconfirmedtransactions = new Unconfirmedtransactions();
					unconfirmedtransactions.setSignature(unconfirmed.getString("signature"));
					unconfirmedtransactions.setTimestamp(unconfirmed.getLongValue("timeStamp"));
					unconfirmedtransactions.setDeadline(unconfirmed.getLongValue("deadline"));
					unconfirmedtransactions.setDetail(JSON.toJSONString(unconfirmed));
					unconService.save(unconfirmedtransactions);
					emit("add", unconfirmed);
				} else if (unconfirmed.getIntValue("type") == 2049) {
					unconfirmed.put("remoteAccount", accountH2MemService.publicKeyToAddress(unconfirmed.getString("remoteAccount")));
					// save into DB
					Unconfirmedtransactions unconfirmedtransactions = new Unconfirmedtransactions();
					unconfirmedtransactions.setSignature(unconfirmed.getString("signature"));
					unconfirmedtransactions.setTimestamp(unconfirmed.getLongValue("timeStamp"));
					unconfirmedtransactions.setDeadline(unconfirmed.getLongValue("deadline"));
					unconfirmedtransactions.setDetail(JSON.toJSONString(unconfirmed));
					unconService.save(unconfirmedtransactions);
					emit("add", unconfirmed);
				} else {
					Unconfirmedtransactions unconfirmedtransactions = new Unconfirmedtransactions();
					unconfirmedtransactions.setSignature(unconfirmed.getString("signature"));
					unconfirmedtransactions.setTimestamp(unconfirmed.getLongValue("timeStamp"));
					unconfirmedtransactions.setDeadline(unconfirmed.getLongValue("deadline"));
					unconfirmedtransactions.setDetail(JSON.toJSONString(unconfirmed));
					unconService.save(unconfirmedtransactions);
					emit("add", unconfirmed);
				}
	        	
	        	removeExpiredUnconfirmedTransactionFromDB();
	        }
		});
		
		
		
		
	}

	@Override
	public void handleException(StompSession arg0, StompCommand arg1, StompHeaders arg2, byte[] arg3, Throwable arg4) { }

	@Override
	public void handleTransportError(StompSession session, Throwable exception) {
		 if (exception instanceof ConnectionLostException || !isConnected) {  
		        // if connection lost, make a new connect 
		        isConnected = false;  
		        while (!isConnected) try {  
		            Thread.sleep(5000);
		            //new connect 
		            connectNISWebSocket.connect();
		            
		            isConnected = true;  
		        } catch (Exception e) { 
		        	logger.error(e.toString());
		        }  
		    }  
	}
	
	
	
	
	public JSONArray getMosaicFromTX(JSONObject tx) {
		JSONArray r_mosaics = new JSONArray();
		JSONArray mosaics = new JSONArray();
		if (tx.containsKey("mosaics")) {
			mosaics.addAll(tx.getJSONArray("mosaics"));
		}
		if (tx.containsKey("otherTrans") && tx.getJSONObject("otherTrans").containsKey("mosaics")) {
			mosaics.addAll(tx.getJSONObject("otherTrans").getJSONArray("mosaics"));
		}
		JSONObject mosaic = new JSONObject();
		for (int i = 0; i < mosaics.size(); i++) {
			JSONObject m = mosaics.getJSONObject(i);
			if (!m.containsKey("mosaicId") || !m.getJSONObject("mosaicId").containsKey("namespaceId") || 
					!m.getJSONObject("mosaicId").containsKey("name")) {
				return null;
			}
			mosaic = new JSONObject();
			mosaic.put("sender", accountH2MemService.publicKeyToAddress(tx.getString("signer")));
			if (tx.containsKey("recipient")) {
				mosaic.put("recipient", tx.getString("recipient"));
			}
			mosaic.put("hash", tx.getString("hash"));
			mosaic.put("timeStamp", tx.getLongValue("timeStamp"));
			mosaic.put("mosaicID", m.getJSONObject("mosaicId").getString("namespaceId") + ":" + m.getJSONObject("mosaicId").getString("name"));
			mosaic.put("mosaicName", m.getJSONObject("mosaicId").getString("name"));
			mosaic.put("namespace", m.getJSONObject("mosaicId").getString("namespaceId"));
			mosaic.put("quantity", m.getLongValue("quantity"));
			mosaic.put("no", (tx.getInteger("height")*1000 + tx.getIntValue("index") + 1)*100 + i + 1);
			Mosaics db_mosaic = mosaicsService.findByMosaicId(mosaic.getString("mosaicID"));
			mosaic.put("div", db_mosaic.getDivisibility());
			r_mosaics.add(mosaic);
		}
		
		return r_mosaics;
	}
	
	
	/**
	 * remove expired unconfirmed transaction from DB
	 */
	public void removeExpiredUnconfirmedTransactionFromDB() {
		long timeInNem = TimeUtil.getTimeInNem();
		int deleteLtTime = unconService.deleteLtTime(timeInNem);
		if (deleteLtTime > 0) {
			emit("expired", null);
		}
	}
	
	public void emit(String action, JSONObject item) {
		JSONObject o = new JSONObject();
		o.put("action", action);
		if (item != null) {
			o.put("content", item);
		}
		try {
			webSocketService.emitUnconfirmedTransaction(JSON.toJSONString(o));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
