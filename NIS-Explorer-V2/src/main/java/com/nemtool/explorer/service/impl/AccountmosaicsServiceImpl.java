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
import com.nemtool.explorer.mapper.AccountmosaicsMapper;
import com.nemtool.explorer.pojo.Accountmosaics;
import com.nemtool.explorer.pojo.AccountmosaicsExample;
import com.nemtool.explorer.pojo.AccountmosaicsExample.Criteria;
import com.nemtool.explorer.service.AccountmosaicsService;
import com.nemtool.explorer.util.ExceptionsUtil;

/**
*
* @author Masker
* @date 2020.07.18
*/
@Transactional
@Service
public class AccountmosaicsServiceImpl implements AccountmosaicsService {
	
	private final static Logger logger = LoggerFactory.getLogger(AccountmosaicsServiceImpl.class);
	
	@Autowired
	private AccountmosaicsMapper accountmosaicsMapper;
	
	@Autowired
    private SqlSessionFactory sqlSessionFactory;


	
	@Override
	public void addAccountMosaic(Accountmosaics accountmosaics) {
		accountmosaicsMapper.insert(accountmosaics);
	}

	@Override
	public void deleteByExample(AccountmosaicsExample accountmosaicsExample) {
		accountmosaicsMapper.deleteByExample(accountmosaicsExample);
	}
	
	@Override
	public void resetAccountMosaic(String address) {
		Accountmosaics accountmosaics = new Accountmosaics();
		accountmosaics.setAddress(address);
		accountmosaics.setQuantity(0l);
		accountmosaicsMapper.updateByAddressSelective(accountmosaics);
	}

	@Override
	public void saveOrUpdateAccountMosaic(Accountmosaics accountMosaic) {
		Accountmosaics accountmosaicsDB = findByAddress(accountMosaic.getAddress());
		if(accountmosaicsDB != null) {
			if(!accountmosaicsDB.getMosaicid().equals(accountMosaic.getMosaicid()) || !accountmosaicsDB.getQuantity().equals(accountMosaic.getQuantity())) {
				accountmosaicsMapper.updateByAddressSelective(accountMosaic);
			}
		} else {
			accountmosaicsMapper.insert(accountMosaic);
		}

	}

	@Override
	public void insertList(List<Accountmosaics> accountmosaicsList) {
		SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
		AccountmosaicsMapper mapper = session.getMapper(AccountmosaicsMapper.class);
		int count=0;
		for (int i = 0; i < accountmosaicsList.size(); i++) {
			mapper.insert(accountmosaicsList.get(i));
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
	public Accountmosaics findByAddress(String address) {
		AccountmosaicsExample accountmosaicsExample = new AccountmosaicsExample();
		Criteria createCriteria = accountmosaicsExample.createCriteria();
		createCriteria.andAddressEqualTo(address);
		List<Accountmosaics> AccountmosaicsList = accountmosaicsMapper.selectByExample(accountmosaicsExample);
		if(AccountmosaicsList.size() > 0) {
			return AccountmosaicsList.get(0);
		} else {
			return null;
		}
	}

	@Override
	public void update(Accountmosaics accountMosaic) {
		accountmosaicsMapper.updateByAddressSelective(accountMosaic);
	}

	@Override
	public List<Accountmosaics> getMosaicRichList(String mosaicID, int limit, int pageNo) {
		AccountmosaicsExample accountmosaicsExample = new AccountmosaicsExample();
		Criteria createCriteria = accountmosaicsExample.createCriteria();
		createCriteria.andMosaicidEqualTo(mosaicID);
		PageHelper.startPage(pageNo, limit, "quantity desc");
		
		List<Accountmosaics> AccountmosaicsList = accountmosaicsMapper.selectByExample(accountmosaicsExample);
		return AccountmosaicsList;
	}

	

}
