package com.nemtool.explorer.service;

import java.util.List;

import com.nemtool.explorer.pojo.Supernodepayouts;

/**
*
* @author Masker
* @date 2020.07.22
*/
public interface SupernodepayoutsService {
	
	public void add(Supernodepayouts supernodepayout);
	
	public void insertList(List<Supernodepayouts> supernodepayoutsList);
	
	public List<Supernodepayouts> findByTimestamp(long timestamp);
	
	public List<Supernodepayouts> findByRound(int round);
	
	public int findMaxRound();
	
	public List<Supernodepayouts> findAll();
	
	public List<Long> findAllTimestamp();
	
}
