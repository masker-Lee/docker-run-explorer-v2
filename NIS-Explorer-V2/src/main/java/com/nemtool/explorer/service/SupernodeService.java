package com.nemtool.explorer.service;

import java.util.List;

import com.nemtool.explorer.pojo.Supernodes;


/**
*
* @author Masker
* @date 2020.07.30
*/
public interface SupernodeService {
	
	List<Supernodes> findAllSupernodes();
	
	void deleteAll();
	
	void insertList(List<Supernodes> supernodeList);
	
	List<Supernodes> findByName(String name);

}
