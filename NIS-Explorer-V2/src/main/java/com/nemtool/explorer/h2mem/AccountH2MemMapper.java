package com.nemtool.explorer.h2mem;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.nemtool.explorer.pojo.AccountH2;

/**
*
* @author Masker
* @date 2020.09.02
*/
@Repository
public interface AccountH2MemMapper {
	
	void createTable();
	
	void createIndexId();
    void createIndexAddress();
    void createIndexPub();
	
	int insert(AccountH2 accountH2);
	
	List<AccountH2> accountH2AllList();
	
	void insertList(List<AccountH2> accountH2List);
	
	AccountH2 getByExample(AccountH2 accountH2);
	
	void dropTable();
	
}
