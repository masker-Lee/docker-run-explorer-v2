package com.nemtool.explorer.serviceH2;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nemtool.explorer.h2.BlocksH2Mapper;
import com.nemtool.explorer.pojo.BlockH2;

/**
*
* @author Masker
* @date 2020.08.28
*/
@Transactional
@Service
public class BlocksH2Service {
	
	@Autowired
	private BlocksH2Mapper blocksH2Mapper;
	
	public List<BlockH2> getAllBlocks(){
		return blocksH2Mapper.getAllBlocks();
	}
	
	public int getMaxBlock() {
		return blocksH2Mapper.queryMaxHeight();
	}
}
