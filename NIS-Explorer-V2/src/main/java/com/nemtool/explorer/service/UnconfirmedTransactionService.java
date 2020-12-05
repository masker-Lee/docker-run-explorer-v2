package com.nemtool.explorer.service;
/**
*
* @author Masker
* @date 2020.10.15
*/

import java.util.List;

import com.nemtool.explorer.pojo.Unconfirmedtransactions;

public interface UnconfirmedTransactionService {
	
	public void save(Unconfirmedtransactions unconfirmedtransactions);

	public List<Unconfirmedtransactions> findAll();
	
	public int deleBySignature(String signature);
	
	public int deleteLtTime(long nowTime);
	
	public List<Unconfirmedtransactions> findByOtherHash(String otherHash);
	
	public void updateBySignature(String signature, Unconfirmedtransactions unconfirmedtransactions);
	
	public int deleteAll();
	
}
