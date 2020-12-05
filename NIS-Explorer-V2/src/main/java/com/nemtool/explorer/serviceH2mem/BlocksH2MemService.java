package com.nemtool.explorer.serviceH2mem;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nemtool.explorer.h2mem.BlocksH2MemMapper;
import com.nemtool.explorer.pojo.BlockH2;

/**
*
* @author Masker
* @date 2020.09.02
*/
@Transactional
@Service
public class BlocksH2MemService {
	
	@Autowired
	private BlocksH2MemMapper blocksH2MemMapper;
	
	public void createTable() {
		blocksH2MemMapper.createTable();
	}
	
	public void createIndex() {
		blocksH2MemMapper.createIndexHarvestedinname();
		blocksH2MemMapper.createIndexHarvesterid();
		blocksH2MemMapper.createIndexTotalfee();
		blocksH2MemMapper.createIndexHeight();
	}
	
	public void insert(BlockH2 blocksH2) {
		blocksH2MemMapper.insert(blocksH2);
	}
	
	public List<BlockH2> blocksAllList(){
		return blocksH2MemMapper.blocksH2AllList();
	}
	
	public void insertList(List<BlockH2> blocksH2List) {
		List<BlockH2> insertList = new ArrayList<BlockH2>();
		int listSize = blocksH2List.size();
		for (int i = 0; i < listSize; i++) {
			insertList.add(blocksH2List.get(i));
			if(i%500==499 || i==(listSize-1)) {
				blocksH2MemMapper.insertList(insertList);
				insertList.clear();
			}
		}
		
	}
	
	public List<BlockH2> getByHarvesterId(int id) {
		return blocksH2MemMapper.getByHarvesterId(id);
	}
	
	public int getMaxHeight() {
		return blocksH2MemMapper.getMaxHeight();
	}
	
	public void dropTable() {
		blocksH2MemMapper.dropTable();
	}

}
