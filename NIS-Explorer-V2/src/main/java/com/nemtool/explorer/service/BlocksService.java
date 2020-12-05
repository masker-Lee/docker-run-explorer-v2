package com.nemtool.explorer.service;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nemtool.explorer.pojo.Blocks;

/**
 * @Description: blocks service interface
 * @author Masker
 * @date 2020.06.28
 */
public interface BlocksService {

	/**
	 * add one block
	 * @param block
	 */
	public void add(Blocks block);
	
	/**
	 * delete block
	 * @param height
	 */
	public void delete(int [] height);
	
	/**
	 * find all blocks
	 * @return
	 */
	public List<Blocks> findAll();
	
	/**
	 * update one block
	 * @param block
	 */
	public void update(Blocks block);
	
	/**
	 * find one block
	 * @param height
	 * @return
	 */
	public Blocks findByHeight(int height);
	
	/**
	 * get the latest 10 Blocks
	 * @return
	 */
	public List<Blocks> latest10Blcoks();
	
	/**
	 * insert some blocks
	 * @param blocksList
	 * @return 
	 */
	public void insertList(List<Blocks> blocksList);
	
	/**
	 * get blockList from the nis
	 * @param height
	 */
	public JSONArray blockListFromNis(int height);
	
	/**
	 * query max block height from NIS 
	 * @return max block
	 */
	public int maxBlockHeightFromNis();
	
	/**
	 * query max block from DB
	 * @return max block
	 */
	public Blocks maxBlocksFromDB();
	
	/**
	 * query minimum block from DB
	 * @return minimum block
	 */
	public Blocks minBlocksFromDB();
	
	/**
	 * query the specific block data
	 * @param height
	 * @return block
	 */
	public JSONObject blockAtPublic(int height);
	
}
