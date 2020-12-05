package com.nemtool.explorer.util;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nemtool.explorer.config.Config;

/** 
 * @Description: Account Util
 * @author Masker
 * @date 2020.06.22
 */
@Component
public class Account {
	private final static Logger logger = LoggerFactory.getLogger(Account.class);
	
	private static String nisHost;
	
	@Autowired
	public Account(Config config) {
		Account.nisHost = "http://" + config.getNisHost() + ":" + config.getNisPort();
	}
	
    /**
     * query the account data from public key
     * @param publicKey
     * @return
     */
    public static String accountGetFromPublicKey(String publicKey){
    	return HttpUtils.doGet(nisHost + "/account/get/from-public-key?publicKey="+publicKey,null);
    }
    
    /**
	 * Get Address From Public Key
	 * @param publicKey
	 * @return
	 */
	public static String getAccountAddressFromPublicKey(String publicKey){
		String accountString = Account.accountGetFromPublicKey(publicKey);
		if("".equals(accountString)){
			return "";
		}
		JSONObject account = JSON.parseObject(accountString);
		JSONObject accountSub = account.getJSONObject("account");
		if(accountSub==null || accountSub.getString("address")==null){
			return "";
		}
		return accountSub.getString("address");
	}
	
	/**
	 * Query the specific Account data by public key
	 * @param publicKey
	 * @return
	 */
	public static JSONObject getAccountJSONFromPublicKey(String publicKey){
		JSONObject accountJSON = null;
		String accountJSONString = Account.accountGetFromPublicKey(publicKey);
		if(accountJSONString==null || "".equals(accountJSONString.trim())){
			logger.info("fail to get the Account data! getAccountJSONFromPublicKey");
			return null;
		}
		accountJSON = JSON.parseObject(accountJSONString);
		return accountJSON;
	}
	
	/**
     * Query the specific Account data by account
     * @param account
     * @return
     */
    public static JSONObject accountGet(String account){
    	JSONObject accountJSON = null;
		String accountJSONString = HttpUtils.doGet(nisHost + "/account/get?address="+account,null);
		if(accountJSONString==null || "".equals(accountJSONString.trim())){
			logger.info("fail to get the Account data! accountGet");
			return null;
		}
		accountJSON = JSON.parseObject(accountJSONString);
		return accountJSON;
    }
    
    /**
     * Query the specific Account Harvest data by account
     * @param account
     * @param id
     * @return
     */
    public static List<JSONObject> accountHarvests(String account, int id, List<JSONObject> list){
    	List<JSONObject> accountList = list;
    	String accountJSONString = null;
    	if(id == 0){
    		accountJSONString = HttpUtils.doGet(nisHost + "/account/harvests?address="+account,null);
    	} else {
    		accountJSONString = HttpUtils.doGet(nisHost + "/account/harvests?address="+account+"&id="+id,null);
    	}
		if(accountJSONString !=null && !accountJSONString.isEmpty()){
			JSONObject accountJSON = JSON.parseObject(accountJSONString);
			if(accountJSON.containsKey("data") && accountJSON.getJSONArray("data").size()>0) {
				int lastID = 0;
				JSONArray accountJSONArray = accountJSON.getJSONArray("data");
				for (int i = 0; i < accountJSONArray.size(); i++) {
					JSONObject jsonObject = accountJSONArray.getJSONObject(i);
					lastID = jsonObject.getIntValue("id");
					accountList.add(jsonObject);
				}
				accountList = accountHarvests(account, lastID, accountList);
			}
		}
		return accountList;
		
    }
    
    
    public static JSONObject getTotalFeeAndlastBlock(String account, JSONObject jObject){
    	JSONObject resultJson = jObject;
    	long totalFee = 0L;
		try {
			totalFee = resultJson.getLongValue("totalFee");
		} catch (Exception e) {
		}
		int lastID = 0;
		try {
			lastID = resultJson.getIntValue("lastID");
		} catch (Exception e) {
		}
		long lastBlock = 0L;
		try {
			lastBlock = resultJson.getLongValue("lastBlock");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	String accountJSONString = null;
    	if(lastID == 0){
    		accountJSONString = HttpUtils.doGet(nisHost + "/account/harvests?address="+account,null);
    	} else {
    		accountJSONString = HttpUtils.doGet(nisHost + "/account/harvests?address="+account+"&id="+lastID,null);
    	}
		if(accountJSONString !=null && !accountJSONString.isEmpty()){
			JSONObject accountJSON = JSON.parseObject(accountJSONString);
			if(accountJSON.containsKey("data") && accountJSON.getJSONArray("data").size()>0) {
				JSONArray accountJSONArray = accountJSON.getJSONArray("data");
				for (int i = 0; i < accountJSONArray.size(); i++) {
					JSONObject jsonObject = accountJSONArray.getJSONObject(i);
					if(lastID == 0) {
						lastBlock = jsonObject.getIntValue("height");
						resultJson.put("lastBlock",lastBlock);
					}
					lastID = jsonObject.getIntValue("id");
					totalFee +=jsonObject.getLongValue("totalFee");
				}
				resultJson.put("lastID", lastID);
				resultJson.put("totalFee",totalFee);
				resultJson = getTotalFeeAndlastBlock(account, resultJson);
			}
		}
		return resultJson;
    }
//    public static JSONObject accountHarvests(String account, int id){
//    	JSONObject accountJSON = null;
//    	String accountJSONString = null;
//    	if(id == 0){
//    		accountJSONString = HttpUtils.doGet(nisHost + "/account/harvests?address="+account,null);
//    	} else {
//    		accountJSONString = HttpUtils.doGet(nisHost + "/account/harvests?address="+account+"&id="+id,null);
//    	}
//		if(accountJSONString==null || "".equals(accountJSONString.trim())){
//			logger.info("fail to get the Account data! accountHarvests");
//			return null;
//		}
//		accountJSON = JSON.parseObject(accountJSONString);
//		return accountJSON;
//    }
    
    /**
     * Query the specific Account Transfers
     * @param account
     * @return
     */
    public static JSONObject accountTransfersAll(String account){
    	JSONObject accountJSON = null;
    	String accountJSONString = HttpUtils.doGet(nisHost + "/account/transfers/all?address="+account,null);
		if(accountJSONString==null || "".equals(accountJSONString.trim())){
			logger.info("fail to get the Account data! accountTransfersAll");
			return null;
		}
		accountJSON = JSON.parseObject(accountJSONString);
		return accountJSON;
    }
    
    /**
     * Query the specific Account Transfers
     * @param account
     * @param id
     * @return
     */
    public static JSONObject accountTransfersAll(String account, String lastID){
    	JSONObject accountJSON = null;
    	String accountJSONString = HttpUtils.doGet(nisHost + "/account/transfers/all?address="+account+"&id="+lastID,null);
		if(accountJSONString==null || "".equals(accountJSONString.trim())){
			logger.info("fail to get the Account data! accountTransfersAll");
			return null;
		}
		accountJSON = JSON.parseObject(accountJSONString);
		return accountJSON;
    }
    
    /**
     * Query the specific Account outgoing
     * @param account
     * @return
     */
    public static JSONObject accountTransfersOutgoing(String account){
    	JSONObject accountJSON = null;
    	String accountJSONString = HttpUtils.doGet(nisHost + "/account/transfers/outgoing?address="+account,null);
		if(accountJSONString==null || "".equals(accountJSONString.trim())){
			logger.info("fail to get the Account data! accountTransfersOutgoing");
			return null;
		}
		accountJSON = JSON.parseObject(accountJSONString);
		return accountJSON;
    }
    
    /**
     * Query the specific Account outgoing
     * @param account
     * @param id
     * @return
     */
    public static JSONObject accountTransfersOutgoing(String account, String lastID){
    	JSONObject accountJSON = null;
    	String accountJSONString = HttpUtils.doGet(nisHost + "/account/transfers/outgoing?address="+account+"&id="+lastID,null);
		if(accountJSONString==null || "".equals(accountJSONString.trim())){
			logger.info("fail to get the Account data! accountTransfersOutgoing");
			return null;
		}
		accountJSON = JSON.parseObject(accountJSONString);
		return accountJSON;
    }
    
}
