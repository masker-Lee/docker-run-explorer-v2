package com.nemtool.explorer.service;

import java.util.List;

import com.nemtool.explorer.pojo.Namespaces;

/**
*
* @author Masker
* @date 2020.07.21
*/
public interface NamespacesService {
	
	public void add(Namespaces namespaces);
	
	public List<Namespaces> findByNamespace(String namespace);

	public void updateNamespaceExpiredTime(Namespaces namespaces, long expiredTime);
	
	public List<Namespaces> findOneNamespaceByName(String namespace);
	
	public void updateRootNamespace(Namespaces namespaces);
	
	public void insertList(List<Namespaces> namespacesList);
	
	public List<Namespaces> find(long no, int limit);
	
	public List<Namespaces> subNamespaceList(String rootNamespace);
	
	public List<Namespaces> namespaceListbyRoot(String rootNamespace, int rootNamespaceId);

	public void updateByNamespace(Namespaces namespaces);
	
	public List<Namespaces> findAll();
	
	public List<String> findAllNamespace();
	
	public List<Namespaces> findById(int id);
}
