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
import com.nemtool.explorer.mapper.MosaictransactionsMapper;
import com.nemtool.explorer.pojo.Mosaictransactions;
import com.nemtool.explorer.pojo.MosaictransactionsExample;
import com.nemtool.explorer.pojo.MosaictransactionsExample.Criteria;
import com.nemtool.explorer.service.MosaicTransactionService;
import com.nemtool.explorer.util.ExceptionsUtil;

/**
*
* @author Masker
* @date 2020.07.21
*/
@Transactional
@Service
public class MosaicTransactionServiceImpl implements MosaicTransactionService {
	
	private final static Logger logger = LoggerFactory.getLogger(MosaicTransactionServiceImpl.class);

	@Autowired
	private MosaictransactionsMapper mosaictransactionsMapper;
	
	@Autowired
    private SqlSessionFactory sqlSessionFactory;
	
	@Override
	public void insertList(List<Mosaictransactions> mosaictransactionsList) {
		SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
		MosaictransactionsMapper mapper = session.getMapper(MosaictransactionsMapper.class);
		int count=0;
		for (int i = 0; i < mosaictransactionsList.size(); i++) {
			mapper.insert(mosaictransactionsList.get(i));
			count++;
			if(count%1000==999) {
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
	public List<Long> findAllMosaicTransactionNo() {
		return mosaictransactionsMapper.findAllMosaicTransactionNo();
	}

	@Override
	public Mosaictransactions findByTransactionNo(long no) {
		return mosaictransactionsMapper.findByTransactionNo(no);
	}

	@Override
	public List<Mosaictransactions> selectByAddressAndNo(String address, long no, int pageNum, int pageSize,
			String orderBy) {
		PageHelper.startPage(pageNum, pageSize, orderBy);
		List<Mosaictransactions> MosaictransactionsList = mosaictransactionsMapper.selectByAddressAndNoPage(address, no);
		
		return MosaictransactionsList;
	}
	
	@Override
	public List<Mosaictransactions> mosaicTransferList(String mosaicName, String namespace, long no, int pageNo, int limit) {
		MosaictransactionsExample mosaictransactionsExample = new MosaictransactionsExample();
		Criteria createCriteria = mosaictransactionsExample.createCriteria();
		if (mosaicName != null && !mosaicName.isEmpty() && namespace != null && !namespace.isEmpty()) {
			createCriteria.andMosaicEqualTo(mosaicName);
			createCriteria.andNamespaceEqualTo(namespace);
		}
		if (no > 0) {
			createCriteria.andNoLessThan(no);
		}
		PageHelper.startPage(pageNo, limit, "timeStamp desc");
		List<Mosaictransactions> MosaictransactionsList = mosaictransactionsMapper.selectByExample(mosaictransactionsExample);
		
		return MosaictransactionsList;
	}

}
