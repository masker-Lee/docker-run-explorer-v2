package com.nemtool.explorer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nemtool.explorer.mapper.UnconfirmedtransactionsMapper;
import com.nemtool.explorer.pojo.Unconfirmedtransactions;
import com.nemtool.explorer.pojo.UnconfirmedtransactionsExample;
import com.nemtool.explorer.service.UnconfirmedTransactionService;

/**
*
* @author Masker
* @date 2020.10.15
*/
@Service
public class UnconfirmedTransactionServiceImpl implements UnconfirmedTransactionService{

	@Autowired
	private UnconfirmedtransactionsMapper unconfirMapper;
	
	@Override
	public List<Unconfirmedtransactions> findAll() {
		UnconfirmedtransactionsExample unconfirmedtransactionsExample = new UnconfirmedtransactionsExample();
		unconfirmedtransactionsExample.setOrderByClause("timeStamp desc");
		return unconfirMapper.selectByExample(unconfirmedtransactionsExample);
	}

	@Override
	public int deleBySignature(String signature) {
		UnconfirmedtransactionsExample unconfirmedtransactionsExample = new UnconfirmedtransactionsExample();
		unconfirmedtransactionsExample.createCriteria().andSignatureEqualTo(signature);
		int deleteByExample = unconfirMapper.deleteByExample(unconfirmedtransactionsExample);
		return deleteByExample;
	}

	@Override
	public int deleteLtTime(long nowTime) {
		UnconfirmedtransactionsExample unconfirmedtransactionsExample = new UnconfirmedtransactionsExample();
		unconfirmedtransactionsExample.createCriteria().andTimestampLessThan(nowTime);
		int deleteByExample = unconfirMapper.deleteByExample(unconfirmedtransactionsExample);
		return deleteByExample;
	}

	@Override
	public void save(Unconfirmedtransactions unconfirmedtransactions) {
		unconfirMapper.insert(unconfirmedtransactions);
	}

	@Override
	public List<Unconfirmedtransactions> findByOtherHash(String otherHash) {
		UnconfirmedtransactionsExample unconfirmedtransactionsExample = new UnconfirmedtransactionsExample();
		unconfirmedtransactionsExample.createCriteria().andOtherhashEqualTo(otherHash);
		return unconfirMapper.selectByExample(unconfirmedtransactionsExample);
	}

	@Override
	public void updateBySignature(String signature, Unconfirmedtransactions unconfirmedtransactions) {
		UnconfirmedtransactionsExample example = new UnconfirmedtransactionsExample();
		example.createCriteria().andSignatureEqualTo(signature);
		unconfirMapper.updateByExampleSelective(unconfirmedtransactions, example);
	}

	@Override
	public int deleteAll() {
		UnconfirmedtransactionsExample unconfirmedtransactionsExample = new UnconfirmedtransactionsExample();
		int deleteByExample = unconfirMapper.deleteByExample(unconfirmedtransactionsExample);
		return deleteByExample;
	}

}
