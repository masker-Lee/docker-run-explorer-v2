package com.nemtool.explorer.service.impl;

import java.util.ArrayList;
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
import com.nemtool.explorer.mapper.AccountsMapper;
import com.nemtool.explorer.pojo.Accounts;
import com.nemtool.explorer.pojo.AccountsExample;
import com.nemtool.explorer.pojo.AccountsExample.Criteria;
import com.nemtool.explorer.service.AccountsService;
import com.nemtool.explorer.util.ExceptionsUtil;

/**
*
* @author Masker
* @date 2020.07.17
*/
@Transactional
@Service
public class AccountsServiceImpl implements AccountsService {
	
	private final static Logger logger = LoggerFactory.getLogger(AccountsServiceImpl.class);

	@Autowired
	private AccountsMapper accountsMapper;
	
	@Autowired
    private SqlSessionFactory sqlSessionFactory;

	
	@Override
	public void addAccounts(Accounts account) {
		accountsMapper.insert(account);
	}

	@Override
	public void updateAccountsByAddress(Accounts account) {
		accountsMapper.updateAccountsByAddress(account);
	}

	@Override
	public List<Accounts> checkIfAccountExistByAddress(String address) {
		AccountsExample accountsExample = new AccountsExample();
		Criteria createCriteria = accountsExample.createCriteria();
		createCriteria.andAddressEqualTo(address);
		return accountsMapper.selectByExample(accountsExample);
	}

	@Override
	public List<Accounts> checkIfAccountExistByPublicKey(String PublicKey) {
		AccountsExample accountsExample = new AccountsExample();
		Criteria createCriteria = accountsExample.createCriteria();
		createCriteria.andPublickeyEqualTo(PublicKey);
		return accountsMapper.selectByExample(accountsExample);
	}

	@Override
	public void insertList(List<Accounts> accountsList) {
		SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
		AccountsMapper mapper = session.getMapper(AccountsMapper.class);
		int count=0;
		for (int i = 0; i < accountsList.size(); i++) {
			mapper.insert(accountsList.get(i));
			count++;
			if(count%1000==999) {
				session.commit();
				session.clearCache();
			}
		}
		try {
			session.commit();
			session.clearCache();
			session.close();
		} catch (Exception e) {
			logger.info(ExceptionsUtil.getExceptionAllinformation(e));
		}
	}

	@Override
	public List<String> selectAllAddressAndPublickey() {
		List<String> selectAllPublickey = accountsMapper.selectAllPublickey();
		System.out.println("selectAllPublickey:"+selectAllPublickey.size());
		List<String> selectAllAddress = accountsMapper.selectAllAddress();
		System.out.println("selectAllAddress:"+selectAllAddress.size());
		selectAllAddress.addAll(selectAllPublickey);
		System.out.println("all:" + selectAllAddress.size());
		return selectAllAddress;
	}

	@Override
	public List<Accounts> selectPage(int page, int pagesize, String orderBy) {
		List<Accounts> accountList = new ArrayList<Accounts>();
		try {
			PageHelper.startPage(page, pagesize, orderBy);
			AccountsExample example = new AccountsExample();
			example.setOrderByClause(orderBy); 
			accountList = accountsMapper.selectByExample(example);
		} catch (Exception e) {
			logger.info("selectPage error :" + ExceptionsUtil.getExceptionAllinformation(e));
		}
		return accountList;
	}

}
