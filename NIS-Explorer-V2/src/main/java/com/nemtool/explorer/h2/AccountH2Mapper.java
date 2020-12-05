package com.nemtool.explorer.h2;
/**
* query or save data in h2 database table account
* @author Masker
* @date 2020.08.28
*/

import java.util.List;

import org.springframework.stereotype.Repository;

import com.nemtool.explorer.pojo.AccountH2;

@Repository
public interface AccountH2Mapper {
	
	/**
	 * query all address and publickey
	 */
	List<AccountH2> getAllAddressAndPublickey();
	
	/**
	 * get address by publickey 
	 * @param publickey
	 * @return
	 */
	String getAddressByPublickey(String publickey);
	
	/**
	 * get all account
	 * @return
	 */
	List<AccountH2> getAllAccount();
}
