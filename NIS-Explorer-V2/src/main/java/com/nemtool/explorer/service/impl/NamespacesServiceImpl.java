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

import com.nemtool.explorer.mapper.NamespacesMapper;
import com.nemtool.explorer.pojo.Namespaces;
import com.nemtool.explorer.pojo.NamespacesExample;
import com.nemtool.explorer.pojo.NamespacesExample.Criteria;
import com.nemtool.explorer.service.NamespacesService;
import com.nemtool.explorer.util.ExceptionsUtil;

/**
*
* @author Masker
* @date 2020.07.21
*/
@Transactional
@Service
public class NamespacesServiceImpl implements NamespacesService {
	
	private final static Logger logger = LoggerFactory.getLogger(NamespacesServiceImpl.class);
	
	@Autowired
	private NamespacesMapper namespacesMapper;
	
	@Autowired
    private SqlSessionFactory sqlSessionFactory;

	@Override
	public void add(Namespaces namespaces) {
		namespacesMapper.insert(namespaces);
	}

	@Override
	public List<Namespaces> findByNamespace(String namespace) {
		NamespacesExample namespacesExample = new NamespacesExample();
		namespacesExample.createCriteria().andNamespaceEqualTo(namespace);
		return namespacesMapper.selectByExample(namespacesExample);
	}

	@Override
	public void updateNamespaceExpiredTime(Namespaces namespaces, long expiredTime) {
		NamespacesExample namespacesExample = new NamespacesExample();
		Criteria createCriteria = namespacesExample.createCriteria();
		createCriteria.andNamespaceEqualTo(namespaces.getNamespace());
		createCriteria.andExpiredtimeEqualTo(namespaces.getExpiredtime());
		Namespaces namespaces2 = new Namespaces();
		namespaces2.setExpiredtime(expiredTime);
		namespacesMapper.updateByExampleSelective(namespaces2, namespacesExample);
	}

	@Override
	public List<Namespaces> findOneNamespaceByName(String namespace) {
		NamespacesExample namespacesExample = new NamespacesExample();
		namespacesExample.createCriteria().andNamespaceEqualTo(namespace);
		return namespacesMapper.selectByExample(namespacesExample);
	}

	@Override
	public void updateRootNamespace(Namespaces namespaces) {
		List<Namespaces> namespaceListbyRoot = namespaceListbyRoot(namespaces.getRootnamespace(), 0);
		if(namespaceListbyRoot.size() == 0) {
			return;
		}
		for (int i = 0; i < namespaceListbyRoot.size(); i++) {
			Namespaces namespaces2 = namespaceListbyRoot.get(i);
			if (namespaces2.getRootNamespaceId() == null) {
				String subNamespaces = namespaces2.getSubnamespaces();
				if(subNamespaces == null) {
					subNamespaces = "";
				} else {
					subNamespaces += " ";
				}
				subNamespaces += namespaces.getNamespace();
				
				Namespaces namespacesSave = new Namespaces();
				namespacesSave.setSubnamespaces(subNamespaces);
				
				NamespacesExample namespacesExample = new NamespacesExample();
				Criteria createCriteria = namespacesExample.createCriteria();
				createCriteria.andNamespaceEqualTo(namespaces2.getNamespace());
				createCriteria.andExpiredtimeGreaterThan(namespaces.getTimestamp());
				
				namespacesMapper.updateByExampleSelective(namespacesSave, namespacesExample);
			}
		}
	}

	@Override
	public void insertList(List<Namespaces> namespacesList) {
		SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
		NamespacesMapper mapper = session.getMapper(NamespacesMapper.class);
		int count=0;
		for (int i = 0; i < namespacesList.size(); i++) {
			mapper.insert(namespacesList.get(i));
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
	public List<Namespaces> find(long no, int limit) {
		return namespacesMapper.find(no, limit);
	}

	@Override
	public List<Namespaces> subNamespaceList(String rootNamespace) {
		NamespacesExample namespacesExample = new NamespacesExample();
		namespacesExample.createCriteria().andRootnamespaceEqualTo(rootNamespace);
		namespacesExample.setOrderByClause("timeStamp desc");
		return namespacesMapper.selectByExample(namespacesExample);
	}

	@Override
	public List<Namespaces> namespaceListbyRoot(String rootNamespace, int rootNamespaceId) {
		NamespacesExample namespacesExample = new NamespacesExample();
		Criteria createCriteria = namespacesExample.createCriteria();
		createCriteria.andRootnamespaceEqualTo(rootNamespace);
		if (rootNamespaceId > 0) {
			createCriteria.andRootnamespaceidEqualTo(rootNamespaceId);
		}
		namespacesExample.setOrderByClause("namespace asc");
		return namespacesMapper.selectByExample(namespacesExample);
	}

	@Override
	public void updateByNamespace(Namespaces namespaces) {
		NamespacesExample namespacesExample = new NamespacesExample();
		Criteria createCriteria = namespacesExample.createCriteria();
		createCriteria.andNamespaceEqualTo(namespaces.getNamespace());
		createCriteria.andExpiredtimeGreaterThan(namespaces.getTimestamp());
		namespacesMapper.updateByExampleSelective(namespaces, namespacesExample);
	}

	@Override
	public List<Namespaces> findAll() {
		NamespacesExample namespacesExample = new NamespacesExample();
		return namespacesMapper.selectByExample(namespacesExample);
	}

	@Override
	public List<String> findAllNamespace() {
		List<Namespaces> findAll = findAll();
		List<String> namespaceList = new ArrayList<String>();
		for (int i = 0; i < findAll.size(); i++) {
			namespaceList.add(findAll.get(i).getNamespace());
		}
		return namespaceList;
	}

	@Override
	public List<Namespaces> findById(int id) {
		NamespacesExample namespacesExample = new NamespacesExample();
		Criteria createCriteria = namespacesExample.createCriteria();
		createCriteria.andIdEqualTo(id);
		return namespacesMapper.selectByExample(namespacesExample);
	}

}
