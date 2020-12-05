package com.nemtool.explorer.scheduled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nemtool.explorer.init.BlockDataFetch;
import com.nemtool.explorer.pojo.Blocks;
import com.nemtool.explorer.service.BlocksService;
import com.nemtool.explorer.util.ExceptionsUtil;
import com.nemtool.explorer.websocket.MyStompSessionHandler;

/**
* block scheduled, update block if webSocket isn't work
* @author Masker
* @date 2020.07.28
*/
@Component
public class BlockScheduled {
	
	private final static Logger logger = LoggerFactory.getLogger(BlockScheduled.class);
	
	@Autowired
	private BlocksService blocksService;
	
	@Autowired
	private BlockDataFetch blockDataFetch;
	
	@Autowired
	private MyStompSessionHandler myStompSessionHandler;
	
	// block scheduled running status
	private boolean isBlockScheduledRunning = false;
	
	// get block scheduled running status
	public boolean getStatus() {
		return isBlockScheduledRunning;
	}
	
	public void updateLatestBlock() {
		// check blockDataFetch is finish init all data
		if (blockDataFetch.getIsFinishInitData()) {
			// check webSocket is running
			while (myStompSessionHandler.getStatus()) {
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					logger.info("BlockScheduled sleep error:" + ExceptionsUtil.getExceptionAllinformation(e));
				}
			}
			// set block scheduled is running
			isBlockScheduledRunning = true;
			// the latest height in the nis
			int heightNis = blocksService.maxBlockHeightFromNis();
			// the latest height in DB
			Blocks latestBlock = blocksService.maxBlocksFromDB();
			if(latestBlock != null) {
				Integer heightDB = latestBlock.getHeight();
				// if heightNis not exit in DB, save block
				if(heightNis > heightDB) {
					try {
						logger.info("BlockScheduled update block!");
						blockDataFetch.loadAllBlocks(heightDB);
					} catch (Exception e) {
						// set block scheduled running finished
						isBlockScheduledRunning = false;
						logger.info("updateLatestBlock error!" + ExceptionsUtil.getExceptionAllinformation(e));
					}
				}
			}
			// set block scheduled running finished
			isBlockScheduledRunning = false;
		}
	}

}
