package com.nemtool.explorer.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.nemtool.explorer.mapper.SupernodepayoutsMapper;
import com.nemtool.explorer.pojo.Supernodepayouts;
import com.nemtool.explorer.pojo.SupernodepayoutsExample;
import com.nemtool.explorer.pojo.SupernodepayoutsExample.Criteria;
import com.nemtool.explorer.service.SupernodepayoutsService;

/**
*
* @author Masker
* @date 2020.07.22
*/
@Transactional
@Service
public class SupernodepayoutsServiceImpl implements SupernodepayoutsService {

	@Autowired
	private SupernodepayoutsMapper supernodepayoutsMapper;
	
	@Autowired
    private SqlSessionFactory sqlSessionFactory;

	@Override
	public void add(Supernodepayouts supernodepayout) {
		supernodepayoutsMapper.insert(supernodepayout);
	}

	@Override
	public void insertList(List<Supernodepayouts> supernodepayoutsList) {
		SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
		SupernodepayoutsMapper mapper = session.getMapper(SupernodepayoutsMapper.class);
		int count=0;
		for (int i = 0; i < supernodepayoutsList.size(); i++) {
			mapper.insert(supernodepayoutsList.get(i));
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
	public List<Supernodepayouts> findByTimestamp(long timestamp) {
		SupernodepayoutsExample supernodepayoutsExample = new SupernodepayoutsExample();
		Criteria createCriteria = supernodepayoutsExample.createCriteria();
		createCriteria.andTimestampEqualTo(timestamp);
		return supernodepayoutsMapper.selectByExample(supernodepayoutsExample);
	}

	@Override
	public List<Supernodepayouts> findByRound(int round) {
		SupernodepayoutsExample supernodepayoutsExample = new SupernodepayoutsExample();
		Criteria createCriteria = supernodepayoutsExample.createCriteria();
		createCriteria.andRoundEqualTo(round);
		supernodepayoutsExample.setOrderByClause("timeStamp desc");
		return supernodepayoutsMapper.selectByExample(supernodepayoutsExample);
	}

	@Override
	public int findMaxRound() {
		SupernodepayoutsExample supernodepayoutsExample = new SupernodepayoutsExample();
		supernodepayoutsExample.setOrderByClause("round desc");
		PageHelper.startPage(1, 1);
		List<Supernodepayouts> selectByExample = supernodepayoutsMapper.selectByExample(supernodepayoutsExample);
		
		return selectByExample.get(0).getRound();
	}

	@Override
	public List<Supernodepayouts> findAll() {
		SupernodepayoutsExample supernodepayoutsExample = new SupernodepayoutsExample();
		return supernodepayoutsMapper.selectByExample(supernodepayoutsExample);
	}

	@Override
	public List<Long> findAllTimestamp() {
		List<Supernodepayouts> supernodepayoutsList = findAll();
		List<Long> timestampList = new ArrayList<Long>();
		for (int i = 0; i < supernodepayoutsList.size(); i++) {
			timestampList.add(supernodepayoutsList.get(i).getTimestamp());
		}
		return timestampList;
	}
	
	

}
