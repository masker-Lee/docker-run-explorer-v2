package com.nemtool.explorer.serviceH2;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nemtool.explorer.h2.AccountH2Mapper;
import com.nemtool.explorer.pojo.AccountH2;

/**
*
* @author Masker
* @date 2020.08.28
*/
@Transactional
@Service
public class AccountH2Service {
	
	@Autowired
	private AccountH2Mapper accountH2Mapper;
	
	public Map<String, String> getAllAddressAndPublickey(){
		List<AccountH2> accountH2ListAll = accountH2Mapper.getAllAddressAndPublickey();
		Map<String, String> mapResult = accountH2ListAll.stream().collect(Collectors.toMap(AccountH2::getAddress, a ->  {
		 	if(a.getPublickey()==null) {
		 		return "";
		 	} else {
		 		return a.getPublickey();
		 	}
	 	}));
	 	
		return mapResult;
	}
	
	public String getAddressByPublickey(String publickey) {
		return accountH2Mapper.getAddressByPublickey(publickey);
	}
	
	public List<AccountH2> getAllAccount(){
		return accountH2Mapper.getAllAccount();
	}

}
