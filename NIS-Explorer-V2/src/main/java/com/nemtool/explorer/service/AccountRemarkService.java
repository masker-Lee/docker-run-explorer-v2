package com.nemtool.explorer.service;
/**
*
* @author Masker
* @date 2020.07.17
*/

import java.util.List;

import com.nemtool.explorer.pojo.Accountremarks;

public interface AccountRemarkService {
	
	public void add(Accountremarks accountremarks);
	
	public void deleteByAddress(String address);
	
	public Accountremarks findByAddress(String address);

	public void insertList(List<Accountremarks> accountremarksList);
	
	public int countAll();
	
	public void updateByAddress(Accountremarks accountremarks);
	
	public void deleteAll();
}
