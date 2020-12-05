package com.nemtool.explorer.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nemtool.explorer.config.Config;
import com.nemtool.explorer.pojo.Mosaics;
import com.nemtool.explorer.pojo.Transactions;
import com.nemtool.explorer.pojo.Unconfirmedtransactions;
import com.nemtool.explorer.service.MosaicsService;
import com.nemtool.explorer.service.NisService;
import com.nemtool.explorer.service.TransactionsService;
import com.nemtool.explorer.service.UnconfirmedTransactionService;
import com.nemtool.explorer.serviceH2mem.AccountH2MemService;
import com.nemtool.explorer.util.ExceptionsUtil;
import com.nemtool.explorer.util.HexEncoder;

/**
*
* @author Masker
* @date 2020.10.14
*/
@Controller
@RequestMapping("/tx")
public class TxServerController {
	
	private final static Logger logger = LoggerFactory.getLogger(TxServerController.class);
	
	final int TXLISTSIZE = 10;
	
	@Autowired
	private TransactionsService transactionsService;
	
	@Autowired
	private NisService nisService;
	
	@Autowired
	private AccountH2MemService accountH2MemService;
	
	@Autowired
	private Config config;
	
	@Autowired
	private MosaicsService mosaicsService;
	
	@Autowired
	private UnconfirmedTransactionService unconfirmedTransactionService;
	
	/**
     * get transactions list
     */
	@RequestMapping(value="/list", method=RequestMethod.POST, produces="application/json;charset=UTF-8")
	@ResponseBody
	public String txList(@RequestBody String bodyStr) {
		JSONObject body = JSON.parseObject(bodyStr);
		if (!body.containsKey("type")) {
			return "[]";
		}
		String type = body.getString("type");
		int pageNum = 1;
		if (body.containsKey("page")) {
			try {
				pageNum = body.getIntValue("page");
			} catch (Exception e) {
			}
		}
		List<Transactions> transactionsList = new ArrayList<Transactions>();
		Transactions transactions = new Transactions();
		if("transfer".equals(type)) {
			transactions.setType(257);
		} else if("importance".equals(type)) {
			transactions.setType(2049);
		} else if("multisig".equals(type)) {
			transactions.setType(4100);
		} else if("namespace".equals(type)) {
			transactions.setType(8193);
		} else if("apostille".equals(type)) {
			transactions.setApostilleflag(1);
		} else if("aggregate".equals(type)) {
			transactions.setAggregateflag(1);
		}
		if (transactions != null) {
			transactionsList = transactionsService.find(transactions, pageNum, TXLISTSIZE);
		}
		if("mosaic".equals(type)) {
			transactionsList = transactionsService.findByTypeOrMos(16385, 16386, 1, pageNum, TXLISTSIZE);
		}
		
		return JSON.toJSONString(transactionsList);
	}
	
	/**
     * get tx detail
     */
	@RequestMapping(value="/tx", method=RequestMethod.POST, produces="application/json;charset=UTF-8")
	@ResponseBody
	public String tx(@RequestBody String bodyStr) {
		JSONObject body = JSON.parseObject(bodyStr);
		String hash = "";
		String signature = "";
		int height = 0;
		if (body.containsKey("hash")) {
			hash = body.getString("hash");
		}
		if (body.containsKey("signature")) {
			signature = body.getString("signature");
		}
		if (body.containsKey("height")) {
			height = body.getIntValue("height");
		}
		if (height == 0) {
			List<Transactions> transactionsList = transactionsService.findByHash(hash);
			if (transactionsList.size() == 0) {
				return "{}";
			} else if(transactionsList.get(0).getHeight() == null){
				return "{}";
			} 
			
			height = transactionsList.get(0).getHeight();
			if (height == 1) {
				return handleNemesisHeight(hash, signature);
			}
			
			return handleTx(height, hash);
		} else if (height==1) {
			return handleNemesisHeight(hash, signature);
		} else {
			return handleTx(height, hash);
		}
	}
	
	/**
     * get unconfirmed transactions list
     */
	@RequestMapping(value="/unconfirmedTXList", method=RequestMethod.POST, produces="application/json;charset=UTF-8")
	@ResponseBody
	public String unconfirmedTXList() {
		List<Unconfirmedtransactions> UnconfirmedtransactionsList = unconfirmedTransactionService.findAll();
		if (UnconfirmedtransactionsList.size() == 0) {
			return "[]";
		}
		JSONArray resultArray = new JSONArray();
		for (int i = 0; i < UnconfirmedtransactionsList.size(); i++) {
			Unconfirmedtransactions unconfirmedtransaction = UnconfirmedtransactionsList.get(i);
			if (unconfirmedtransaction.getDetail() == null) {
				continue;
			}
			try {
				JSONObject tx = JSON.parseObject(unconfirmedtransaction.getDetail());
				tx.put("sender",accountH2MemService.publicKeyToAddress(tx.getString("signer")));
				if (tx.getIntValue("type") == 4100 && tx.containsKey("otherTrans")) {
					String signer = "";
					if (tx.getJSONObject("otherTrans").containsKey("signer")) {
						signer = accountH2MemService.publicKeyToAddress(tx.getJSONObject("otherTrans").getString("signer"));
					}
					tx.getJSONObject("otherTrans").put("sender", signer);
				}
				resultArray.add(tx);
			} catch (Exception e) {
				logger.info(ExceptionsUtil.getExceptionAllinformation(e));
			}
		}
		return JSON.toJSONString(resultArray);
	}
	
	
	public String handleNemesisHeight(String hash, String signature) {
		String reqData = "{\"height\":" + 1 + "}";
		String resultStr = nisService.blockAtPublic(reqData);
		if (resultStr.isEmpty() || resultStr == null) {
			return "{}";
		}
		JSONArray transactionsArray = JSON.parseObject(resultStr).getJSONArray("transactions");
		for (int i = 0; i < transactionsArray.size(); i++) {
			JSONObject tx = transactionsArray.getJSONObject(i);
			if (signature.equals(tx.getString("signature"))) {
				JSONObject txObject = new JSONObject();
				txObject.put("timeStamp", tx.getLongValue("timeStamp"));
				txObject.put("signerAccount", accountH2MemService.publicKeyToAddress(tx.getString("signer")));
				txObject.put("amount", tx.getLongValue("amount"));
				txObject.put("recipient", tx.getString("recipient"));
				txObject.put("fee", tx.getLongValue("fee"));
				txObject.put("type", 257);
				if (tx.containsKey("message") && tx.getJSONObject("message").containsKey("type")) {
					String payload = tx.getJSONObject("message").getString("payload");
					payload = HexEncoder.decodeMessage(payload);
					tx.getJSONObject("message").put("payload", payload);
				}
				tx.put("height", 1);
				tx.put("tx", txObject);
				
				return JSON.toJSONString(tx);
			}
		}
		return "{}";
	}
	
	public JSONObject checkApostilleAndMosaicTransferFromTX(JSONObject tx) {
		JSONObject txObject = tx.getJSONObject("tx");
		// check if apostille
		if (txObject.getIntValue("type")==257 && config.getApostilleAccount().equals(txObject.getString("recipient")) && txObject.containsKey("message") 
				&& txObject.getJSONObject("message").containsKey("type") && txObject.getJSONObject("message").getIntValue("type")==1) {
			if (txObject.getJSONObject("message").getString("payload").indexOf("HEX:")==0) {
				txObject.put("apostilleFlag", 1);
			}
		}
		// check if mosaic transafer
		if (txObject.getIntValue("type")==257 && txObject.containsKey("mosaics") && txObject.getJSONArray("mosaics").size()>0) {
			txObject.put("mosaicTransferFlag", 1);
		}
		return tx;
	}
	
	public JSONObject formatMosaicDivisibility(JSONObject tx) {
		JSONObject txObject = tx.getJSONObject("tx");
		//keep namespace,name and div in map
		HashMap<String, Integer> divMap = new HashMap<String, Integer>();
		//if mosaic
		if (txObject.containsKey("mosaics") && txObject.getJSONArray("mosaics").size()>0) {
			JSONArray mosaicsArray = txObject.getJSONArray("mosaics");
			for (int i = 0; i < mosaicsArray.size(); i++) {
				JSONObject mosaic = mosaicsArray.getJSONObject(i);
				String m = mosaic.getJSONObject("mosaicId").getString("name");
				String ns = mosaic.getJSONObject("mosaicId").getString("namespaceId");
				//check if exit in divMap
				if (!divMap.containsKey(ns + ":" + m)) {
					//select from DB
					Mosaics findOneMosaic = mosaicsService.findOneMosaic(m, ns);
					divMap.put(ns + ":" + m, findOneMosaic.getDivisibility());
				}
				//put div into mosaic from divMap
				mosaic.put("divisibility", divMap.get(ns + ":" + m));
			}
		}
		//if otherTrans mosaic
		if (txObject.containsKey("otherTrans") && txObject.getJSONObject("otherTrans").containsKey("mosaics") 
				&& txObject.getJSONObject("otherTrans").getJSONArray("mosaics").size()>0) {
			JSONArray mosaicsArray = txObject.getJSONObject("otherTrans").getJSONArray("mosaics");
			for (int i = 0; i < mosaicsArray.size(); i++) {
				JSONObject mosaic = mosaicsArray.getJSONObject(i);
				String m = mosaic.getJSONObject("mosaicId").getString("name");
				String ns = mosaic.getJSONObject("mosaicId").getString("namespaceId");
				//check if exit in divMap
				if (!divMap.containsKey(ns + ":" + m)) {
					//select from DB
					Mosaics findOneMosaic = mosaicsService.findOneMosaic(m, ns);
					divMap.put(ns + ":" + m, findOneMosaic.getDivisibility());
				}
				//put div into mosaic from divMap
				mosaic.put("divisibility", divMap.get(ns + ":" + m));
			}
		}
		
		return tx;
	}

	
	public String handleTx(int height, String hash) {
		String reqData = "{\"height\":" + (height - 1) + "}";
		String result = nisService.localChainBlocksAfter(reqData);
		if (result.isEmpty() || result == null || !JSON.parseObject(result).containsKey("data") || JSON.parseObject(result).getJSONArray("data").size() == 0) {
			return "{}";
		} 
		JSONArray txesArray = JSON.parseObject(result).getJSONArray("data").getJSONObject(0).getJSONArray("txes");
		for (int i = 0; i < txesArray.size(); i++) {
			JSONObject tx = txesArray.getJSONObject(i);
			if (hash.equals(tx.getString("hash"))) {
				tx.getJSONObject("tx").put("signerAccount", accountH2MemService.publicKeyToAddress(tx.getJSONObject("tx").getString("signer")));
				if (tx.getJSONObject("tx").containsKey("otherTrans")) {
					JSONObject otherTrans = tx.getJSONObject("tx").getJSONObject("otherTrans");
					//sender
					if (otherTrans.containsKey("signer")) {
						otherTrans.put("sender", accountH2MemService.publicKeyToAddress(otherTrans.getString("signer")));
					}
					//message
					if (otherTrans.containsKey("message") && otherTrans.getJSONObject("message").containsKey("type") 
							&& otherTrans.getJSONObject("message").getIntValue("type")==1 && otherTrans.getJSONObject("message").containsKey("payload")) {
						String payload = otherTrans.getJSONObject("message").getString("payload");
						payload = HexEncoder.decodeMessage(payload);
						otherTrans.getJSONObject("message").put("payload", payload);
					}
					//modifications
					if (otherTrans.containsKey("modifications")) {
						JSONArray modificationsArray = otherTrans.getJSONArray("modifications");
						for (int j = 0; j < modificationsArray.size(); j++) {
							JSONObject modification = modificationsArray.getJSONObject(j);
							modification.put("cosignatoryAccount", accountH2MemService.publicKeyToAddress(modification.getString("cosignatoryAccount")));
						}
					}
				}
				if (tx.getJSONObject("tx").containsKey("signatures")) {
					JSONArray signaturesArray = tx.getJSONObject("tx").getJSONArray("signatures");
					for (int j = 0; j < signaturesArray.size(); j++) {
						JSONObject signatures = signaturesArray.getJSONObject(j);
						signatures.put("sender", accountH2MemService.publicKeyToAddress(signatures.getString("signer")));
					}
				}
				if (tx.getJSONObject("tx").containsKey("message") && tx.getJSONObject("tx").getJSONObject("message").containsKey("type") 
						&& tx.getJSONObject("tx").getJSONObject("message").getIntValue("type")==1 && tx.getJSONObject("tx").getJSONObject("message").containsKey("payload")) {
					String payload = tx.getJSONObject("tx").getJSONObject("message").getString("payload");
					payload = HexEncoder.decodeMessage(payload);
					tx.getJSONObject("tx").getJSONObject("message").put("payload", payload);
				}
				if (tx.getJSONObject("tx").containsKey("modifications")) {
					JSONArray modificationsArray = tx.getJSONObject("tx").getJSONArray("modifications");
					for (int j = 0; j < modificationsArray.size(); j++) {
						JSONObject m = modificationsArray.getJSONObject(j);
						m.put("cosignatoryAccount", accountH2MemService.publicKeyToAddress(m.getString("cosignatoryAccount")));
					}
				}
				if (tx.getJSONObject("tx").containsKey("remoteAccount")) {
					tx.getJSONObject("tx").put("remoteAccount", accountH2MemService.publicKeyToAddress(tx.getJSONObject("tx").getString("remoteAccount")));
				}
				tx = checkApostilleAndMosaicTransferFromTX(tx);
				tx.put("height",height);
				tx = formatMosaicDivisibility(tx);
				
				return JSON.toJSONString(tx);
			}
		}
		
		return "{}";
	}
}
