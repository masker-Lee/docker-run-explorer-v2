package com.nemtool.explorer.init;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nemtool.explorer.pojo.Accountremarks;
import com.nemtool.explorer.pojo.Blocks;
import com.nemtool.explorer.service.AccountRemarkService;
import com.nemtool.explorer.service.BlocksService;
import com.nemtool.explorer.service.NisService;
import com.nemtool.explorer.service.impl.TableService;
import com.nemtool.explorer.util.ExceptionsUtil;
import com.nemtool.explorer.websocket.ConnectNISWebSocket;

/**
 * @Description: init DB table and update all blocks data
 * @author Masker
 * @date 2020.06.28
 */
@Component
public class InitBlocksData implements ApplicationRunner{

	private final static Logger logger = LoggerFactory.getLogger(InitBlocksData.class);
	
	long start,end;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	@Autowired
	private TableService tableService;
	
	@Autowired
	private ConnectNISWebSocket connectNISWebSocket;
	
	@Autowired
	private NisService nisService;
	
	@Autowired
	private BlocksService blocksService;
	
	@Autowired
	private BlockDataFetch blockDataFetch;
	
	@Autowired
	private AccountRemarkService accountRemarkService;
	
	private static int heightNIS = 0;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		//check NIS status
		String heartbeat = nisService.heartbeat();
		logger.info("NIS status:" + heartbeat);
		if(heartbeat.isEmpty() || "{}".equals(heartbeat.trim()) || !(JSON.parseObject(heartbeat).containsKey("code")) || JSON.parseObject(heartbeat).getInteger("code") != 1 
				|| JSON.parseObject(heartbeat).getInteger("type") != 2) {
			logger.info("Error: Please make sure NIS has been started and blocks loading has been finished.");
			throw new Exception("NIS error");
		}
		
		//try to create table
        try {
        	logger.info("initing database...  " + sdf.format(new Date()));
        	start = System.currentTimeMillis();
        	tableService.createTable();
            end = System.currentTimeMillis();
            logger.info("database inited! Time-consuming:" + (end - start) + "ms");
        } catch (Exception e) {
        	logger.error(e.toString());
        }
		//update blocks
		logger.info("start update blocks...  " + sdf.format(new Date()));
		start = System.currentTimeMillis();
		//insert Accountremark
		insertAccountremark();
		//query max block height from NIS
		heightNIS = blocksService.maxBlockHeightFromNis();
		logger.info("NIS max block is " + heightNIS);
		if(heightNIS==-1){
			return;
		}
		
		new Thread(){
			public void run() {
				//query max block height from DB
				Blocks maxBlockHeightDB = blocksService.maxBlocksFromDB();
				logger.info("max block height from DB is " + maxBlockHeightDB);
				try {
					if (maxBlockHeightDB != null && heightNIS <= maxBlockHeightDB.getHeight() + 1000) {
						try {
							tableService.createIndex();
						} catch (Exception e) {
							logger.info("mysql index is exited");
						}
						blockDataFetch.loadAllBlocks(maxBlockHeightDB.getHeight());
					} else if(maxBlockHeightDB == null || maxBlockHeightDB.getHeight() <= 1) {
						//prepare data
						blockDataFetch.prepareData(true);
						try {
							blockDataFetch.loadAllBlocks(1);
							try {
								tableService.createIndex();
							} catch (Exception e) {
								logger.info("create mysql index error");
							}
						} catch (Exception e) {
							logger.info(ExceptionsUtil.getExceptionAllinformation(e));
						}
						logger.info("update data finish!");
					} else {
						//update block from max block in DB to max block in NIS
						try {
							blockDataFetch.prepareData(false);
							try {
								tableService.createIndex();
							} catch (Exception e) {
								logger.info("mysql index is exited");
							}
							blockDataFetch.loadAllBlocks(maxBlockHeightDB.getHeight());
						} catch (Exception e) {
							logger.info(ExceptionsUtil.getExceptionAllinformation(e));
						}
					}
				} catch (Exception e) {
					logger.info(ExceptionsUtil.getExceptionAllinformation(e));
				}
				
				end = System.currentTimeMillis();
				logger.info("blocks update success! Time-consuming:" + (end - start) + "ms");
				//start webSocket
				connectNISWebSocket.connect();
			}
		}.start();
		
	}

	/**
	 * update accountremark 
	 */
	private void insertAccountremark() {
		logger.info("start update accountremark");
		BufferedReader reader = null;
        String laststr = "";
        try {
        	InputStream in = this.getClass().getResourceAsStream("/accountRemarkData.json");
	        InputStreamReader inputStreamReader = new InputStreamReader(in, "UTF-8");
	        reader = new BufferedReader(inputStreamReader);
	        String tempString = null;
	        while ((tempString = reader.readLine()) != null) {
	            laststr += tempString;
	        }
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
        String replace = laststr.replace("address", "\"address\"").replace("remark", "\"remark\"");
        JSONArray parseArray = JSON.parseArray(replace);
        int countAll = accountRemarkService.countAll();
        if (countAll == 0) {
        	ArrayList<Accountremarks> accountremarksList = new ArrayList<Accountremarks>();
        	Accountremarks accountremark = new Accountremarks();
        	for (int i = 0; i < parseArray.size(); i++) {
				JSONObject jsonObject = parseArray.getJSONObject(i);
				accountremark = new Accountremarks();
				accountremark.setAddress(jsonObject.getString("address").trim());
				accountremark.setRemark(jsonObject.getString("remark").trim());
				
				accountremarksList.add(accountremark);
			}
        	accountRemarkService.insertList(accountremarksList);
		} else if (parseArray.size() > countAll) {
			accountRemarkService.deleteAll();
			ArrayList<Accountremarks> accountremarksList = new ArrayList<Accountremarks>();
        	Accountremarks accountremark = new Accountremarks();
        	for (int i = 0; i < parseArray.size(); i++) {
				JSONObject jsonObject = parseArray.getJSONObject(i);
				accountremark = new Accountremarks();
				accountremark.setAddress(jsonObject.getString("address"));
				accountremark.setRemark(jsonObject.getString("remark"));
				
				accountremarksList.add(accountremark);
			}
        	accountRemarkService.insertList(accountremarksList);
		}
		
	}
}
