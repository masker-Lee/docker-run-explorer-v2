package com.nemtool.explorer.serviceH2mem;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nemtool.explorer.h2mem.AccountH2MemMapper;
import com.nemtool.explorer.pojo.AccountH2;
import com.nemtool.explorer.util.Account;

/**
*
* @author Masker
* @date 2020.09.02
*/
@Transactional
@Service
public class AccountH2MemService {
	
	@Autowired
	private AccountH2MemMapper accountH2MemMapper;
	
	public void createTable() {
		accountH2MemMapper.createTable();
	}
	
	public void createIndex() {
		accountH2MemMapper.createIndexId();
		accountH2MemMapper.createIndexAddress();
		accountH2MemMapper.createIndexPub();
	}
	
	public void insert(AccountH2 accountH2) {
		accountH2MemMapper.insert(accountH2);
	}
	
	public List<AccountH2> accountAllList(){
		return accountH2MemMapper.accountH2AllList();
	}
	
	public void insertList(List<AccountH2> accountH2List) {
		List<AccountH2> insertList = new ArrayList<AccountH2>();
		int listSize = accountH2List.size();
		for (int i = 0; i < listSize; i++) {
			insertList.add(accountH2List.get(i));
			if(i%500==499 || i==(listSize-1)) {
				accountH2MemMapper.insertList(insertList);
				insertList.clear();
			}
		}
		
	}
	
	public AccountH2 getByExample(AccountH2 accountH2) {
		AccountH2 byExample = null;
		try {
			byExample = accountH2MemMapper.getByExample(accountH2);
		} catch (Exception e) {
		}
		return byExample;
	}
	
	public String publicKeyToAddress(String publicKey) {
		AccountH2 AccountH2Example = new AccountH2();
		AccountH2Example.setPublickey(publicKey);
		AccountH2 accounth2 = getByExample(AccountH2Example);
		if(accounth2==null || accounth2.getAddress() == null || accounth2.getAddress().isEmpty()) {
			return Account.getAccountAddressFromPublicKey(publicKey);
		} else {
			return accounth2.getAddress();
		}
	}
	
	public void dropTable() {
		accountH2MemMapper.dropTable();
	}

}
