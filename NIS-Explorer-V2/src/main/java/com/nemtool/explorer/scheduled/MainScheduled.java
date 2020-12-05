package com.nemtool.explorer.scheduled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.nemtool.explorer.init.BlockDataFetch;

/**
 * @Description:  main scheduled
 * @author Masker
 * @date 2020.07.28
 */
@Configuration
@EnableScheduling
public class MainScheduled {
	
	@Autowired
	private BlockScheduled blockScheduled;
	
	@Autowired
	private NodeScheduled nodeScheduled;
	
	@Autowired 
	private PollindexSchedule pollindexSchedule;
	
	@Autowired
	private SupernodeScheduled supernodeScheduled;
	
	@Autowired
	private BlockDataFetch blockDataFetch;
	
	/**
	 * check the latest block in every minute and save the block
	 */
	@Scheduled(fixedDelay = 60 *1000)
	public void runBlockScheduled(){
		if (blockDataFetch.getIsFinishPrepareData() || blockDataFetch.getIsFinishInitData()) {
			blockScheduled.updateLatestBlock();
		}
	}
	
	/**
	 * check the latest node in every minute
	 */
	@Scheduled(fixedDelay = 60 *1000)
	public void runNodeScheduled(){
		if (blockDataFetch.getIsFinishPrepareData() || blockDataFetch.getIsFinishInitData()) {
			nodeScheduled.fetchNode();
		}
	}
	
	/**
	 * check the latest poll in every minute, and save
	 */
	@Scheduled(fixedDelay = 60 *1000)
	public void runPollindexSchedule(){
		if (blockDataFetch.getIsFinishPrepareData() || blockDataFetch.getIsFinishInitData()) {
			pollindexSchedule.fetchPoll();
		}
	}
	
	/**
	 * check the latest supernode in every 10 minute, and save
	 */
	@Scheduled(fixedDelay = 10 * 60 *1000)
	public void runSupernodeScheduled(){
		if (blockDataFetch.getIsFinishPrepareData() || blockDataFetch.getIsFinishInitData()) {
			supernodeScheduled.fetchSupernode();
		}
	}
	
}