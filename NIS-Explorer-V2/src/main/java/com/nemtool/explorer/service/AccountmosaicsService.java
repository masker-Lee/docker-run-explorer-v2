package com.nemtool.explorer.service;

import java.util.List;

import com.nemtool.explorer.pojo.Accountmosaics;
import com.nemtool.explorer.pojo.AccountmosaicsExample;

/**
*
* @author Masker
* @date 2020.07.18
*/
public interface AccountmosaicsService {
	
	public void addAccountMosaic(Accountmosaics accountmosaics);
	
	public void deleteByExample(AccountmosaicsExample accountmosaicsExample);
	
	public void resetAccountMosaic(String address);
	
	public void saveOrUpdateAccountMosaic(Accountmosaics accountMosaic);
	
	public void insertList(List<Accountmosaics> accountmosaicsList);

	public Accountmosaics findByAddress(String address);
	
	public void update(Accountmosaics accountMosaic);
	
	public List<Accountmosaics> getMosaicRichList(String mosaicID, int limit, int pageNo);
}
