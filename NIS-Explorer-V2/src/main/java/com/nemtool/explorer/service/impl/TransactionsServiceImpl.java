package com.nemtool.explorer.service.impl;

import java.util.List;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.nemtool.explorer.mapper.TransactionsMapper;
import com.nemtool.explorer.pojo.Transactions;
import com.nemtool.explorer.pojo.TransactionsExample;
import com.nemtool.explorer.pojo.TransactionsExample.Criteria;
import com.nemtool.explorer.service.TransactionsService;
import com.nemtool.explorer.util.ExceptionsUtil;

/**
*
* @author Masker
* @date 2020.07.16
*/
@Transactional
@Service
public class TransactionsServiceImpl implements TransactionsService{
	
	private final static Logger logger = LoggerFactory.getLogger(TransactionsServiceImpl.class);
	
	@Autowired
	private TransactionsMapper transactionsMapper;
	
	@Autowired
    private SqlSessionFactory sqlSessionFactory;

	@Override
	public void createTransaction(Transactions transactions) {
		transactionsMapper.insert(transactions);
	}

	@Override
	public int queryMaxTransactionHeight() {
		return transactionsMapper.queryMaxTransactionHeight();
	}

	@Override
	public void insertList(List<Transactions> transactionsList) {
		SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
		TransactionsMapper mapper = session.getMapper(TransactionsMapper.class);
		for (int i = 0; i < transactionsList.size(); i++) {
			mapper.insert(transactionsList.get(i));
			if(i%1000==999) {
				session.commit();
				session.clearCache();
			}
		}
		try {
			session.commit();
		} catch (Exception e) {
			logger.info(ExceptionsUtil.getExceptionAllinformation(e));
		}
		session.clearCache();
		session.close();
	}

	@Override
	public List<String> selectAllHash() {
		return transactionsMapper.selectAllHash();
	}

	@Override
	public List<Transactions> findByHash(String hash) {
		TransactionsExample transactionsExample = new TransactionsExample();
		Criteria createCriteria = transactionsExample.createCriteria();
		createCriteria.andHashEqualTo(hash);
		return transactionsMapper.selectByExample(transactionsExample);
	}

	@Override
	public List<Transactions> selectByAddressAndPage(String address, int pageNum, int pageSize, String orderBy) {
		TransactionsExample transactionsExample = new TransactionsExample();
		Criteria criteria = transactionsExample.createCriteria();
		criteria.andSenderEqualTo(address);
		Criteria criteria2 = transactionsExample.createCriteria();
		criteria2.andRecipientEqualTo(address);
		transactionsExample.or(criteria2);
		transactionsExample.setOrderByClause(orderBy);
		PageHelper.startPage(pageNum, pageSize, null);
		List<Transactions> transactionsList = transactionsMapper.selectByExample(transactionsExample);
		
		return transactionsList;
	}

	@Override
	public List<Transactions> transactionsByHeight(int height) {
		TransactionsExample transactionsExample = new TransactionsExample();
		Criteria criteria = transactionsExample.createCriteria();
		criteria.andHeightEqualTo(height);
		transactionsExample.setOrderByClause("timeStamp desc");
		return transactionsMapper.selectByExample(transactionsExample);
	}

	@Override
	public List<Transactions> find(Transactions transactions, int pageNum, int pageSize) {
		TransactionsExample transactionsExample = new TransactionsExample();
		Criteria criteria = transactionsExample.createCriteria();
		if (transactions.getType() != null) {
			criteria.andTypeEqualTo(transactions.getType());
		}
		if (transactions.getApostilleflag() != null) {
			criteria.andApostilleflagEqualTo(transactions.getApostilleflag());
		}
		if (transactions.getAggregateflag() != null) {
			criteria.andAggregateflagEqualTo(transactions.getAggregateflag());
		}
		transactionsExample.setOrderByClause("height desc,timeStamp desc");
		PageHelper.startPage(pageNum, pageSize, null);
		return transactionsMapper.selectByExample(transactionsExample);
	}

	@Override
	public List<Transactions> findByTypeOrMos(int type1, int type2, int mos, int pageNum, int pageSize) {
//		PageHelper.startPage(pageNum, pageSize, null);
		
		return transactionsMapper.findByTypeOrMos(type1, type2, mos, (pageNum-1)*pageSize, pageSize);
	}




}
