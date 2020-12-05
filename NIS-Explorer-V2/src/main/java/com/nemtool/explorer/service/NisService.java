package com.nemtool.explorer.service;

import com.alibaba.fastjson.JSONArray;

/**
 * @Description: nis service interface
 * @author Masker
 * @date 2020.06.28
 */
public interface NisService {
	
	/**
	 * get the latest block height
	 * @return block height String
	 */
	public String blockHeight();
	
	/**
	 * get 10 block height
	 * @param reqData  The start of the block
	 * @return 10 block height
	 */
	public String localChainBlocksAfter(String reqData);

	/**
	 * nis heartbeat
	 * @return
	 */
	public String heartbeat();
	
	/**
	 * Gets a block from the chain that has the given height.
	 * @param reqData
	 * @return
	 */
	public String blockAtPublic(String reqData);
	
	/**
	 * get mosaic list from nis
	 * @param address
	 * @return
	 */
	public String mosaicListByAddress(String address);
	
	public JSONArray nodePeerListReachable();
	
	public JSONArray nodePeerListActive();
	
	public String blockHeightByHostAndPort(String host, String port);
	
	public String accountByAddress(String address);
	
	public String harvestByAddress(String address, int id);
	
	public String namespaceListByAddress(String address);
	
	public String allMosaicDefinitionListByNamespace(String namespace,int id);
	
	public String accountUnconfirmedTransactions(String address);
	
	public boolean checkUncomfirmedTransactionStatus(String address, int id, String signature);
	
}
