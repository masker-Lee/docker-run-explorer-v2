package com.nemtool.explorer.serviceH2;

import java.util.List;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nemtool.explorer.h2.TransactionH2Mapper;
import com.nemtool.explorer.mapper.TransactionMysqlMapper;
import com.nemtool.explorer.pojo.TransactionH2;

/**
*
* @author Masker
* @date 2020.08.28
*/
@Transactional
@Service
public class TransactionH2Service {
	
	@Autowired
	private TransactionH2Mapper transactionH2Mapper;
//	
	@Autowired
	private TransactionMysqlMapper transactionMysqlMapper;
	
	@Autowired
    private SqlSessionFactory sqlSessionFactory;
	
	public List<TransactionH2> getAllTransaction(){
		return transactionH2Mapper.getAllTransaction();
	}
//	
//	public long getMaxTimestampById(int id) {
//		return transactionH2Mapper.getMaxTimestampById(id);
//	}
//	
//	public List<TransactionH2> getTransactionById(int id){
//		return transactionH2Mapper.getTransactionById(id);
//	}
	
	public void insertList(List<TransactionH2> transactionsList) {
		SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
		TransactionMysqlMapper mapper = session.getMapper(TransactionMysqlMapper.class);
		for (int i = 0; i < transactionsList.size(); i++) {
			mapper.insert(transactionsList.get(i));
			if(i%3000==2999) {
				session.commit();
				session.clearCache();
			}
		}
		session.commit();
		session.clearCache();
		session.close();
	}
	
	public List<TransactionH2> getTransactionById(int id){
		return transactionMysqlMapper.getTransactionById(id);
	}
	
	public void createTable() {
		transactionMysqlMapper.createTable();
	}
	
	public void createIndex() {
		transactionMysqlMapper.createIndexRecipientid();
		transactionMysqlMapper.createIndexSenderid();
	}
	
	public void dropTable() {
		transactionMysqlMapper.dropTable();
	}
	
}
