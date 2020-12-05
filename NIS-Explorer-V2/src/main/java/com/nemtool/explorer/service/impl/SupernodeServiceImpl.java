package com.nemtool.explorer.service.impl;

import java.util.List;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.nemtool.explorer.mapper.SupernodesMapper;
import com.nemtool.explorer.pojo.Supernodes;
import com.nemtool.explorer.pojo.SupernodesExample;
import com.nemtool.explorer.pojo.SupernodesExample.Criteria;
import com.nemtool.explorer.service.SupernodeService;

/**
*
* @author Masker
* @date 2020.07.30
*/
@Transactional
@Component
public class SupernodeServiceImpl implements SupernodeService {

	@Autowired
	private SupernodesMapper supernodesMapper;
	
	@Autowired
    private SqlSessionFactory sqlSessionFactory;

	
	@Override
	public List<Supernodes> findAllSupernodes() {
		return supernodesMapper.findAllSupernodes();
	}

	@Override
	public void deleteAll() {
		supernodesMapper.deleteAll();
	}

	@Override
	public void insertList(List<Supernodes> supernodeList) {
		SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
		SupernodesMapper mapper = session.getMapper(SupernodesMapper.class);
		int count=0;
		for (int i = 0; i < supernodeList.size(); i++) {
			mapper.insert(supernodeList.get(i));
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
	public List<Supernodes> findByName(String name) {
		SupernodesExample supernodesExample = new SupernodesExample();
		Criteria criteria = supernodesExample.createCriteria();
		criteria.andNameEqualTo(name);
		return supernodesMapper.selectByExample(supernodesExample);
	}

}
