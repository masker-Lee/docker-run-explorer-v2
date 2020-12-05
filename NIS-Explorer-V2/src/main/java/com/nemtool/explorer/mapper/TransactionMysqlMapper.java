package com.nemtool.explorer.mapper;

import com.nemtool.explorer.pojo.TransactionH2;

import java.util.List;

public interface TransactionMysqlMapper {

	void insert(TransactionH2 transactionH2);
	
	List<TransactionH2> getTransactionById(int id);
	
	void createTable();
	
	void createIndexId();
	void createIndexSenderid();
	void createIndexRecipientid();
	void createIndexTimestamp();
	
	void dropTable();
	
}