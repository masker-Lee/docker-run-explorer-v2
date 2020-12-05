package com.nemtool.explorer.service;

import java.util.List;

import com.nemtool.explorer.pojo.Accounts;

/**
*
* @author Masker
* @date 2020.07.17
*/
public interface AccountsService {
	
	public void addAccounts(Accounts account);
	
	public void updateAccountsByAddress(Accounts account);
	
	public List<Accounts> checkIfAccountExistByAddress(String address);
	
	public List<Accounts> checkIfAccountExistByPublicKey(String PublicKey);
	
	public void insertList(List<Accounts> accountsList);
	
	public List<String> selectAllAddressAndPublickey();
	
	public List<Accounts> selectPage(int page, int pagesize, String orderBy);
	
}
