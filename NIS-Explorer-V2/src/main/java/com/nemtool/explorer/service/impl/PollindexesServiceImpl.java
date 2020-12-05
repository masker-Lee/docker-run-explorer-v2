package com.nemtool.explorer.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.nemtool.explorer.config.Config;
import com.nemtool.explorer.mapper.PollindexesMapper;
import com.nemtool.explorer.pojo.Pollindexes;
import com.nemtool.explorer.pojo.PollindexesExample;
import com.nemtool.explorer.service.PollindexesService;
import com.nemtool.explorer.util.HttpUtils;

/**
*
* @author Masker
* @date 2020.10.20
*/
@Service
public class PollindexesServiceImpl implements PollindexesService {
	
	@Autowired
	private Config config;
	
	@Autowired
	private PollindexesMapper pollindexesMapper;

	@Override
	public String pollList() {
		String url = config.getPollUrl() + "/poll/list";
		return HttpUtils.doGet(url, null);
	}

	@Override
	public String pollDetail(String address) {
		String url = config.getPollUrl() + "/poll/poll";
		Map<String, String> param = new HashMap<String, String>();
		param.put("address", address);
		return HttpUtils.doPost(url, param);
	}

	@Override
	public String pollResult(String address) {
		String url = config.getPollUrl() + "/poll/pollResult";
		Map<String, String> param = new HashMap<String, String>();
		param.put("address", address);
		return HttpUtils.doPost(url, param);
	}

	@Override
	public String pollResultVoters(String address, String strings) {
		String url = config.getPollUrl() + "/poll/pollResultVoters";
		Map<String, String> param = new HashMap<String, String>();
		param.put("address", address);
		param.put("strings", strings);
		String resultStr = HttpUtils.doPost(url, param);
		JSONArray parseArray = JSON.parseArray(resultStr);
		
		return JSON.toJSONString(parseArray.getJSONArray(0));
	}

	@Override
	public void savePollindex(Pollindexes pollindexes) {
		pollindexesMapper.insert(pollindexes);
	}

	@Override
	public boolean checkIfExist(String address) {
		Pollindexes findByAddress = findByAddress(address);
		if (findByAddress == null) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public List<Pollindexes> findAllPollindexes() {
		PollindexesExample pollindexesExample = new PollindexesExample();
		return pollindexesMapper.selectByExample(pollindexesExample);
	}

	@Override
	public Pollindexes findByAddress(String address) {
		PollindexesExample pollindexesExample = new PollindexesExample();
		pollindexesExample.createCriteria().andAddressEqualTo(address);
		List<Pollindexes> selectByExample = pollindexesMapper.selectByExample(pollindexesExample);
		if (selectByExample.size() > 0) {
			return selectByExample.get(0);
		} else {
			return null;
		}
	}

	@Override
	public Pollindexes findById(int id) {
		Pollindexes selectByPrimaryKey = pollindexesMapper.selectByPrimaryKey(id);
		return selectByPrimaryKey;
	}

	@Override
	public List<Pollindexes> findPages(int page, int pageSize) {
		PollindexesExample pollindexesExample = new PollindexesExample();
		PageHelper.startPage(page, pageSize, null);
		return pollindexesMapper.selectByExampleWithBLOBs(pollindexesExample);
	}

}
