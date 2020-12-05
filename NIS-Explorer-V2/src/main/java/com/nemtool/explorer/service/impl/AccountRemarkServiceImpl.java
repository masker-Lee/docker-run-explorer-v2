package com.nemtool.explorer.service.impl;

import java.util.List;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nemtool.explorer.mapper.AccountremarksMapper;
import com.nemtool.explorer.pojo.Accountremarks;
import com.nemtool.explorer.pojo.AccountremarksExample;
import com.nemtool.explorer.service.AccountRemarkService;

/**
*
* @author Masker
* @date 2020.07.17
*/
@Transactional
@Service
public class AccountRemarkServiceImpl implements AccountRemarkService {

	@Autowired
	private AccountremarksMapper accountremarksMapper;
	
	@Autowired
    private SqlSessionFactory sqlSessionFactory;

	
	@Override
	public void add(Accountremarks accountremarks) {
		accountremarksMapper.insert(accountremarks);
	}

	@Override
	public void deleteByAddress(String address) {
		accountremarksMapper.deleteByaddress(address);
	}
	
	@Override
	public Accountremarks findByAddress(String address) {
		return accountremarksMapper.findByAddress(address);
	}

	@Override
	public void insertList(List<Accountremarks> accountremarksList) {
		SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
		AccountremarksMapper mapper = session.getMapper(AccountremarksMapper.class);
		int count=0;
		for (int i = 0; i < accountremarksList.size(); i++) {
			mapper.insert(accountremarksList.get(i));
			count++;
			if(count%1000==999) {
				session.commit();
				session.clearCache();
			}
		}
		session.commit();
		session.clearCache();
		session.close();
	}

	@Override
	public int countAll() {
		AccountremarksExample accountremarksExample = new AccountremarksExample();
		return accountremarksMapper.countByExample(accountremarksExample);
	}

	@Override
	public void updateByAddress(Accountremarks accountremarks) {
		AccountremarksExample accountremarksExample = new AccountremarksExample();
		accountremarksExample.createCriteria().andAddressEqualTo(accountremarks.getAddress());
		accountremarksMapper.updateByExampleSelective(accountremarks, accountremarksExample);
	}

	@Override
	public void deleteAll() {
		AccountremarksExample accountremarksExample = new AccountremarksExample();
		accountremarksMapper.deleteByExample(accountremarksExample);
	}

	

}
