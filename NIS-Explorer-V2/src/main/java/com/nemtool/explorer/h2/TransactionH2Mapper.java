package com.nemtool.explorer.h2;
/**
*
* @author Masker
* @date 2020.08.28
*/

import java.util.List;

import org.springframework.stereotype.Repository;

import com.nemtool.explorer.pojo.TransactionH2;

@Repository
public interface TransactionH2Mapper {
	
	List<TransactionH2> getAllTransaction();
	
	long getMaxTimestampById(int id);
	
	List<TransactionH2> getTransactionById(int id);
}
