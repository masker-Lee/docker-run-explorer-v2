package com.nemtool.explorer.h2;
/**
* query or save data in h2 database table
* @author Masker
* @date 2020.08.28
*/

import java.util.List;

import org.springframework.stereotype.Repository;

import com.nemtool.explorer.pojo.BlockH2;

@Repository
public interface BlocksH2Mapper {
	
	/**
	 * query all blocks
	 */
	List<BlockH2> getAllBlocks();
	
	/**
	 * query the max height in h2 db
	 */
	public int queryMaxHeight();
	
}
