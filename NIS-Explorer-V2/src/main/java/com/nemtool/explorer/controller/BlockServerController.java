package com.nemtool.explorer.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nemtool.explorer.pojo.Transactions;
import com.nemtool.explorer.service.NisService;
import com.nemtool.explorer.service.TransactionsService;
import com.nemtool.explorer.serviceH2mem.AccountH2MemService;
import com.nemtool.explorer.util.HexEncoder;

/**
* api for block
* @author Masker
* @date 2020.10.09
*/
@Controller
@RequestMapping("/block")
public class BlockServerController {
	
	final int BLOCKLISTSIZE = 10;
	
	@Autowired
	private NisService nisService;
	
	@Autowired
	private AccountH2MemService accountH2MemService;
	
	@Autowired
	private TransactionsService transactionsService;
	
	/**
     * get current block info
     */
	@RequestMapping(value="/height", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String blockHeight() {
		return nisService.blockHeight();
	}
	
	/**
     * get block list (paging)
     */
	@RequestMapping(value="/list", method=RequestMethod.POST, produces="application/json;charset=UTF-8")
	@ResponseBody
	public String blockList(@RequestBody String bodyStr) {
		JSONObject body = JSON.parseObject(bodyStr);
		int page = 1;
		if (body.containsKey("page")) {
			try {
				page = body.getIntValue("page");
			} catch (Exception e) {
				return "[]";
			}
		}
		String blockHeight = nisService.blockHeight();
		int height = JSON.parseObject(blockHeight).getIntValue("height");
		height = height - (10 + BLOCKLISTSIZE*(page - 1));
		String heightObj = "{\"height\":" + height +"}";
		String result = nisService.localChainBlocksAfter(heightObj);
		if (result.isEmpty() || !JSON.parseObject(result).containsKey("data")) {
			return "[]";
		}
		JSONArray blockList = JSON.parseObject(result).getJSONArray("data");
		JSONObject r_block = new JSONObject();
		JSONArray r_blockArray = new JSONArray();
		for (int i = 0; i < blockList.size(); i++) {
			JSONObject item = blockList.getJSONObject(i);
			r_block = new JSONObject();
			r_block.put("hash", item.getString("hash"));
			r_block.put("height", item.getJSONObject("block").getString("height"));
			r_block.put("timeStamp", item.getJSONObject("block").getString("timeStamp"));
			r_block.put("txAmount", item.getJSONArray("txes").size());
			long txFee = 0;
			JSONArray txesArray = item.getJSONArray("txes");
			for (int j = 0; j < txesArray.size(); j++) {
				JSONObject tx = txesArray.getJSONObject(j).getJSONObject("tx");
				txFee += tx.getLongValue("fee");
				
				tx.put("signerAccount",accountH2MemService.publicKeyToAddress(tx.getString("signer")));
				if (tx.containsKey("otherTrans") && tx.getJSONObject("otherTrans").containsKey("signer")) {
					JSONObject otherTransJsonObject = tx.getJSONObject("otherTrans");
					otherTransJsonObject.put("sender",accountH2MemService.publicKeyToAddress(otherTransJsonObject.getString("signer")));
				}
				
				if (tx.containsKey("signatures")) {
					JSONArray signaturesArray = tx.getJSONArray("signatures");
					for (int k = 0; k < signaturesArray.size(); k++) {
						JSONObject signatures = signaturesArray.getJSONObject(k);
						signatures.put("sender",accountH2MemService.publicKeyToAddress(signatures.getString("signer")));
					}
				}
				
				if (tx.containsKey("remoteAccount")) {
					tx.put("remote",accountH2MemService.publicKeyToAddress(tx.getString("remoteAccount")));
				}
				
				if (tx.containsKey("message") && tx.getJSONObject("message").containsKey("type")) {
					String payload = tx.getJSONObject("message").getString("payload");
					payload = HexEncoder.decodeMessage(payload);
					tx.getJSONObject("message").put("payload", payload);
				}
			}
			r_block.put("txFee", txFee);
			r_block.put("harvester",accountH2MemService.publicKeyToAddress(item.getJSONObject("block").getString("signer")));
			r_block.put("txes", txesArray);
			r_blockArray.add(r_block);
		}
		Collections.reverse(r_blockArray);
		return JSON.toJSONString(r_blockArray);
	}
	
	/**
     * get block info
     */
	@RequestMapping(value="/at", method=RequestMethod.POST, produces="application/json;charset=UTF-8")
	@ResponseBody
	public String blockAt(@RequestBody String bodyStr) {
		JSONObject body = JSON.parseObject(bodyStr);
		if (!body.containsKey("height")) {
			return "";
		}
		try {
			body.getIntValue("height");
		} catch (Exception e) {
			return "";
		}
		int height = body.getIntValue("height");
		String reqData = "{\"height\":" + height + "}";
		return nisService.blockAtPublic(reqData);
	}
	
	
	/**
     * get block info when using the searching action
     */
	@RequestMapping(value="/blockAtBySearch", method=RequestMethod.POST, produces="application/json;charset=UTF-8")
	@ResponseBody
	public String blockAtBySearch(@RequestBody String bodyStr) {
		JSONObject body = JSON.parseObject(bodyStr);
		int height = 0;
		if (body.containsKey("height")) {
			try {
				height = body.getIntValue("height");
			} catch (Exception e) {
			}
		}
		if (height < 1) {
			return "{}";
		}
		// query txes in block
		List<Transactions> transactionsByHeightList = transactionsService.transactionsByHeight(height);
		JSONObject r_block = new JSONObject();
		r_block.put("txes", transactionsByHeightList);
		r_block.put("txFee", 0);
		if (height == 1) {//Nemsis Block
			String reqData = "{\"height\":" + height + "}";
			String blockAtPublic = nisService.blockAtPublic(reqData);
			JSONObject data = JSON.parseObject(blockAtPublic);
			r_block.put("height", 1);
			r_block.put("timeStamp", 0);
			r_block.put("difficulty", "#");
			r_block.put("txAmount", data.getJSONArray("transactions").size());
			r_block.put("signer", "#");
			r_block.put("hash", "#");
		} else {//non nemsis block
			// calculate all fee
			long txFee = 0;
			for (int i = 0; i < transactionsByHeightList.size(); i++) {
				Transactions transactions = transactionsByHeightList.get(i);
				txFee += transactions.getFee();
			}
			r_block.put("txFee", txFee);
			String reqData = "{\"height\":" + (height - 1) + "}";
			String blockList = nisService.localChainBlocksAfter(reqData);
			if (blockList == null || blockList.isEmpty() || JSON.parseObject(blockList).getJSONArray("data").size() == 0) {
				return JSON.toJSONString(r_block);
			}
			JSONObject block = JSON.parseObject(blockList).getJSONArray("data").getJSONObject(0);
			r_block.put("height", height);
			r_block.put("timeStamp", block.getJSONObject("block").getLongValue("timeStamp"));
			r_block.put("difficulty", block.getLongValue("difficulty"));
			r_block.put("txAmount", block.getJSONArray("txes").size());
			r_block.put("signer", accountH2MemService.publicKeyToAddress(block.getJSONObject("block").getString("signer")));
			r_block.put("hash", block.getString("hash"));
		}
		
		return JSON.toJSONString(r_block);
	}

}

