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
import com.nemtool.explorer.mapper.MosaicsMapper;
import com.nemtool.explorer.pojo.Mosaics;
import com.nemtool.explorer.pojo.MosaicsExample;
import com.nemtool.explorer.pojo.MosaicsExample.Criteria;
import com.nemtool.explorer.service.MosaicsService;
import com.nemtool.explorer.util.ExceptionsUtil;

/**
*
* @author Masker
* @date 2020.07.21
*/
@Transactional
@Service
public class MosaicsServiceImpl implements MosaicsService {
	
	private final static Logger logger = LoggerFactory.getLogger(MosaicsServiceImpl.class);
	
	@Autowired
	private MosaicsMapper mosaicsMapper;
	
	@Autowired
    private SqlSessionFactory sqlSessionFactory;
	
	@Override
	public void add(Mosaics mosaics) {
		mosaicsMapper.insert(mosaics);
	}
	
	@Override
	public void updateMosaic(Mosaics mosaics) {
		MosaicsExample mosaicsExample = new MosaicsExample();
		Criteria createCriteria = mosaicsExample.createCriteria();
		createCriteria.andMosaicidEqualTo(mosaics.getMosaicid());
		createCriteria.andTimestampLessThan(mosaics.getTimestamp());
		mosaicsMapper.updateByExampleSelective(mosaics, mosaicsExample);
	}

	@Override
	public void saveOrUpdateMosaic(Mosaics mosaics) {
		Mosaics mosaicDB = findByMosaicId(mosaics.getMosaicid());
		if(mosaicDB != null) {
			updateMosaic(mosaics);
		} else {
			add(mosaics);
		}
	}

	@Override
	public void updateMosaicSupply(String mosaicName, String namespace, long timeStamp, int change, int height) {
		MosaicsExample mosaicsExample = new MosaicsExample();
		Criteria createCriteria = mosaicsExample.createCriteria();
		createCriteria.andMosaicnameEqualTo(mosaicName);
		createCriteria.andNamespaceEqualTo(namespace);
		createCriteria.andTimestampLessThan(timeStamp);
		List<Mosaics> selectByExampleList = mosaicsMapper.selectByExample(mosaicsExample);
		if(selectByExampleList.size() == 0) {
			return;
		}
		for(int i = 0; i<selectByExampleList.size(); i++) {
			Mosaics mosaic = selectByExampleList.get(i);
			mosaic.setInitialsupply(mosaic.getInitialsupply() + change);
			this.updateMosaic(mosaic);
		}
	}

	@Override
	public void insertList(List<Mosaics> mosaicsList) {
		SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
		MosaicsMapper mapper = session.getMapper(MosaicsMapper.class);
		int count=0;
		for (int i = 0; i < mosaicsList.size(); i++) {
			try {
				mapper.insert(mosaicsList.get(i));
			} catch (Exception e) {
				logger.info(ExceptionsUtil.getExceptionAllinformation(e));
			}
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
	public Mosaics findByMosaicId(String mosaicId) {
		MosaicsExample mosaicsExample = new MosaicsExample();
		mosaicsExample.createCriteria().andMosaicidEqualTo(mosaicId);
		List<Mosaics> selectByExampleList = mosaicsMapper.selectByExample(mosaicsExample);
		if(selectByExampleList.size() > 0) {
			return selectByExampleList.get(0);
		} else {
			return null;
		}
		
	}

	@Override
	public List<String> findAllMosaicId() {
		return mosaicsMapper.findAllMosaicId();
	}

	@Override
	public List<Mosaics> findMosaicID(String mosaicID) {
		MosaicsExample mosaicsExample = new MosaicsExample();
		Criteria createCriteria = mosaicsExample.createCriteria();
		createCriteria.andMosaicidEqualTo(mosaicID);
		List<Mosaics> selectByExample = mosaicsMapper.selectByExample(mosaicsExample);
		return selectByExample;
	}

	@Override
	public List<Mosaics> mosaicListByNamespace(String namespace) {
		MosaicsExample mosaicsExample = new MosaicsExample();
		Criteria createCriteria = mosaicsExample.createCriteria();
		createCriteria.andNamespaceEqualTo(namespace);
		List<Mosaics> selectByExample = mosaicsMapper.selectByExample(mosaicsExample);
		return selectByExample;
	}

	@Override
	public List<Mosaics> mosaicList(long no, int limit) {
		MosaicsExample mosaicsExample = new MosaicsExample();
		if (no > 0) {
			Criteria createCriteria = mosaicsExample.createCriteria();
			createCriteria.andNoLessThan(no);
		}
		PageHelper.startPage(1, limit,"timeStamp desc");
		List<Mosaics> selectByExample = mosaicsMapper.selectByExample(mosaicsExample);
		return selectByExample;
	}

	@Override
	public Mosaics findOneMosaic(String mosaicName, String namespace) {
		MosaicsExample mosaicsExample = new MosaicsExample();
		Criteria createCriteria = mosaicsExample.createCriteria();
		createCriteria.andNamespaceEqualTo(namespace);
		createCriteria.andMosaicnameEqualTo(mosaicName);
		List<Mosaics> MosaicsList = mosaicsMapper.selectByExample(mosaicsExample);
		if (MosaicsList.size() > 0) {
			return MosaicsList.get(0);
		} else {
			return null;
		}
		
	}

}
