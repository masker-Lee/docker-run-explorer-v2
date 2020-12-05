package com.nemtool.explorer.service;

import java.util.List;

import com.nemtool.explorer.pojo.Mosaics;

/**
*
* @author Masker
* @date 2020.07.21
*/
public interface MosaicsService {
	
	public void add(Mosaics mosaics);
	
	public void updateMosaic(Mosaics mosaics);
	
	public void saveOrUpdateMosaic(Mosaics mosaics);
	
	public void updateMosaicSupply(String mosaicName, String namespace, long timeStamp, int change, int height);
	
	public void insertList(List<Mosaics> mosaicsList);
	
	public Mosaics findByMosaicId(String mosaicId);
	
	public List<String> findAllMosaicId();
	
	public List<Mosaics> findMosaicID(String mosaicID);
	
	public List<Mosaics> mosaicListByNamespace(String namespace);
	
	public List<Mosaics> mosaicList(long no, int limit);
	
	public Mosaics findOneMosaic(String mosaicName, String namespace);
	
}
