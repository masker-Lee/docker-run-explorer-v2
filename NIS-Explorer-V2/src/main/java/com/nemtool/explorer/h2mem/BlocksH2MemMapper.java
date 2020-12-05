package com.nemtool.explorer.h2mem;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.nemtool.explorer.pojo.BlockH2;

/**
*
* @author Masker
* @date 2020.09.02
*/
@Repository
public interface BlocksH2MemMapper {
	
	void createTable();
	
	void createIndexId();
	void createIndexHarvesterid();
    void createIndexHarvestedinname();
    void createIndexTotalfee();
    void createIndexHeight();
	
	int insert(BlockH2 blockH2);
	
	List<BlockH2> blocksH2AllList();
	
	void insertList(List<BlockH2> blocksH2List);
	
	List<BlockH2> getByHarvesterId(int id);
	
	int getMaxHeight();
	
	void dropTable();
	
}
