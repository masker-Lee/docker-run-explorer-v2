package com.nemtool.explorer.service;

import java.util.List;

import com.nemtool.explorer.pojo.Transactions;

/**
*
* @author Masker
* @date 2020.07.16
*/
public interface TransactionsService {
	
	/**
	 * create transaction
	 * @param transactions
	 */
	public void createTransaction(Transactions transactions);
	
	
	/**
	 * query the max transaction height
	 * @return
	 */
	public int queryMaxTransactionHeight();
	
	/**
	 * create transaction list
	 * @param transactionsList
	 * @return
	 */
	public void insertList(List<Transactions> transactionsList);
	
	public List<String> selectAllHash();
	
	public List<Transactions> findByHash(String hash);
	
	public List<Transactions> selectByAddressAndPage(String address, int page, int pagesize, String orderBy);
	
	public List<Transactions> transactionsByHeight(int height);
	
	public List<Transactions> find(Transactions transactions, int pageNum, int pageSize);
	
	public List<Transactions> findByTypeOrMos(int type1, int type2, int mos, int pageNum, int pageSize);
	
}
