package com.nemtool.explorer.scheduled;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nemtool.explorer.pojo.Pollindexes;
import com.nemtool.explorer.service.PollindexesService;

/**
* poll schedule, get all poll and save into DB
* @author Masker
* @date 2020.10.20
*/
@Component
public class PollindexSchedule {
	
	@Autowired
	private PollindexesService pollindexesService;
	
	//loaded poll address set 
	private HashSet<String> loadedPollAddressSet = new HashSet<String>();
	
	//fetch all poll
	public void fetchPoll() {
		try {
			//get all poll data
			String pollList = pollindexesService.pollList();
			JSONArray pollArray = JSON.parseArray(pollList);
			Pollindexes pollindexes = new Pollindexes();
			for (int i = 0; i < pollArray.size(); i++) {
				pollindexes = new Pollindexes();
				JSONObject pollObject = pollArray.getJSONObject(i);
				pollindexes.setAddress(pollObject.getString("address"));
				pollindexes.setCreator(pollObject.getString("creator"));
				pollindexes.setDoe(pollObject.getLongValue("doe"));
				pollindexes.setTitle(pollObject.getString("title"));
				pollindexes.setType(pollObject.getIntValue("type"));
				//check if loaded
				if (!loadedPollAddressSet.contains(pollindexes.getAddress())) {
					//add address to set
					loadedPollAddressSet.add(pollindexes.getAddress());
					//save poll into DB
					if (!pollindexesService.checkIfExist(pollindexes.getAddress())) {
						pollindexesService.savePollindex(pollindexes);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
