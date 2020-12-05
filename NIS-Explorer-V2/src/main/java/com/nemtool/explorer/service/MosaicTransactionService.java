package com.nemtool.explorer.service;

import java.util.List;

import com.nemtool.explorer.pojo.Mosaictransactions;

/**
*
* @author Masker
* @date 2020.07.21
*/
public interface MosaicTransactionService {
	
	public void insertList(List<Mosaictransactions> mosaictransactionsList);
	
	public List<Long> findAllMosaicTransactionNo();
	
	public Mosaictransactions findByTransactionNo(long no);
	
	public List<Mosaictransactions> selectByAddressAndNo(String address, long no, int pageNum, int pageSize, String orderBy);
	
	public List<Mosaictransactions> mosaicTransferList(String mosaicName, String namespace, long no, int pageNo, int limit);
	
}
