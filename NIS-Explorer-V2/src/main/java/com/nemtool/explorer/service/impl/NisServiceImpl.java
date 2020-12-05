package com.nemtool.explorer.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nemtool.explorer.config.Config;
import com.nemtool.explorer.service.NisService;
import com.nemtool.explorer.util.ExceptionsUtil;
import com.nemtool.explorer.util.HttpUtils;

/**
 * @Description: nis service impl
 * @author Masker
 * @date 2020.06.28
 */
@Transactional
@Service
public class NisServiceImpl implements NisService{
	
	private final static Logger logger = LoggerFactory.getLogger(NisServiceImpl.class);

	private String nisHost;
	
	@Autowired
	public NisServiceImpl(Config config) {
		this.nisHost = "http://" + config.getNisHost() + ":" + config.getNisPort();
	}

	@Override
	public String blockHeight() {
		String url = nisHost + "/chain/height";
		return HttpUtils.doGet(url, null);
	}

	@Override
	public String localChainBlocksAfter(String reqData) {
		String url = nisHost + "/local/chain/blocks-after";
		return HttpUtils.doPostJson(url, reqData);
	}

	@Override
	public String heartbeat() {
		String url = nisHost + "/heartbeat" ;
		return HttpUtils.doGet(url, null);
	}

	@Override
	public String blockAtPublic(String reqData) {
		String url = nisHost + "/block/at/public" ;
		return HttpUtils.doPostJson(url, reqData);
	}

	@Override
	public String mosaicListByAddress(String address) {
		String url = nisHost + "/account/mosaic/owned?address=" +address;
		return HttpUtils.doGet(url, null);
	}

	@Override
	public JSONArray nodePeerListReachable() {
		JSONObject result = new JSONObject();
		String url = nisHost + "/node/peer-list/reachable" ;
		String blockString = HttpUtils.doGet(url, null);
		if(blockString==null || blockString.isEmpty()){
			System.out.println("fail to get the nodes data!");
			return new JSONArray();
		}
		result = JSON.parseObject(blockString);
		if(result==null || !result.containsKey("data")){
			System.out.println("fail to get the nodes data!");
			return new JSONArray();
		}
		return result.getJSONArray("data");
	}

	@Override
	public JSONArray nodePeerListActive() {
		JSONObject result = new JSONObject();
		String url = nisHost + "/node/peer-list/active" ;
		String blockString = HttpUtils.doGet(url, null);
		if(blockString==null || blockString.isEmpty()){
			System.out.println("fail to get the nodes data!");
			return null;
		}
		result = JSON.parseObject(blockString);
		if(result==null || !result.containsKey("data")){
			System.out.println("fail to get the nodes data!");
			return null;
		}
		return result.getJSONArray("data");
	}

	@Override
	public String blockHeightByHostAndPort(String host, String port) {
		String url = "http://" + host + ":" + port + "/chain/height";
		return HttpUtils.doGet(url, null);
	}

	@Override
	public String accountByAddress(String address) {
		String url = nisHost + "/account/get?address=" + address ;
		return HttpUtils.doGet(url, null);
	}

	@Override
	public String harvestByAddress(String address, int id) {
		String url = "";
		String result = "";
		int lastId = id;
		JSONArray jsonArrayAll = new JSONArray();
		do {
			if (lastId == 0) {
				url = nisHost + "/account/harvests?address=" + address;
			} else {
				url = nisHost + "/account/harvests?address=" + address + "&id=" + lastId;
			}
			result = HttpUtils.doGet(url, null);
			try {
				JSONArray jsonArray = JSON.parseObject(result).getJSONArray("data");
				jsonArrayAll.addAll(jsonArray);
				//get last id
				for (int i = 0; i < jsonArray.size(); i++) {
					JSONObject jsonobject = jsonArray.getJSONObject(i);
					if (jsonobject != null && jsonobject.containsKey("id")) {
						lastId = jsonobject.getIntValue("id");
					}
				}
			} catch (Exception e) {
				logger.info("harvestByAddress http error: " + ExceptionsUtil.getExceptionAllinformation(e));
			}
			
		} while (!result.isEmpty() && JSON.parseObject(result).containsKey("data") && JSON.parseObject(result).getJSONArray("data").size() > 0);
		
		return JSON.toJSONString(jsonArrayAll);
	}

	@Override
	public String namespaceListByAddress(String address) {
		String url = nisHost + "/account/namespace/page?pageSize=100&address=" + address ;
		return HttpUtils.doGet(url, null);
	}

	@Override
	public String allMosaicDefinitionListByNamespace(String namespace, int id) {
		String url = "";
		String result = "";
		int lastId = id;
		JSONArray jsonArrayAll = new JSONArray();
		do {
			if (lastId == 0) {
				url = nisHost + "/namespace/mosaic/definition/page?namespace=" + namespace;
			} else {
				url = nisHost + "/namespace/mosaic/definition/page?namespace=" + namespace + "&id=" + id;
			}
			result = HttpUtils.doGet(url, null);
			try {
				JSONArray jsonArray = JSON.parseObject(result).getJSONArray("data");
				jsonArrayAll.addAll(jsonArray);
				//get last id
				for (int i = 0; i < jsonArray.size(); i++) {
					JSONObject jsonobject = jsonArray.getJSONObject(i);
					if (jsonobject != null && jsonobject.containsKey("meta")) {
						lastId = jsonobject.getJSONObject("meta").getIntValue("id");
					}
				}
			} catch (Exception e) {
				logger.info("allMosaicDefinitionListByNamespace: " + ExceptionsUtil.getExceptionAllinformation(e));
			}
			
		} while (!result.isEmpty() && JSON.parseObject(result).containsKey("data") && JSON.parseObject(result).getJSONArray("data").size() > 0);
		
		return JSON.toJSONString(jsonArrayAll);
	}

	@Override
	public String accountUnconfirmedTransactions(String address) {
		String url = nisHost + "/account/unconfirmedTransactions?address=" +address;
		return HttpUtils.doGet(url, null);
	}

	@Override
	public boolean checkUncomfirmedTransactionStatus(String address, int id, String signature) {
		String url = "";
		String result = "";
		int lastId = id;
		do {
			if (lastId == 0) {
				url = nisHost + "/account/transfers/all?address=" + address ;
			} else {
				url = nisHost + "/account/transfers/all?address=" + address + "&id=" + id;
			}
			result = HttpUtils.doGet(url, null);
			if (!result.isEmpty()) {
				JSONObject data = JSON.parseObject(result);
				if (data.containsKey("data") && data.getJSONArray("data").size() > 0) {
					for (int i = 0; i < data.getJSONArray("data").size(); i++) {
						JSONObject item = data.getJSONArray("data").getJSONObject(i);
						if (item.containsKey("transaction") && item.getJSONObject("transaction").containsKey("signature") && 
								signature.equals(item.getJSONObject("transaction").getString("signature"))) {
							return true;
						}
						lastId = item.getJSONObject("meta").getIntValue("id");
					}
				}
			}
		} while (!result.isEmpty() && JSON.parseObject(result).containsKey("data") && JSON.parseObject(result).getJSONArray("data").size() > 0);
		return false;
	}
	
	

}
