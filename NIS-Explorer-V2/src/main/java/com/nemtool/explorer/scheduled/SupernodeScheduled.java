package com.nemtool.explorer.scheduled;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nemtool.explorer.config.Config;
import com.nemtool.explorer.pojo.Supernodes;
import com.nemtool.explorer.service.SupernodeService;
import com.nemtool.explorer.util.HttpUtils;

/**
* supernode update scheduled
* @author Masker
* @date 2020.07.30
*/
@Component
public class SupernodeScheduled {
	
	// date format
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	@Autowired
	private Config config;
	
	@Autowired
	private SupernodeService supernodeService;
	
	/**
	 * fetch all supernode and save into DB
	 */
	public void fetchSupernode() {
		//get supernode data from web server
		String supernodeData = HttpUtils.doGet(config.getSupernodeDataUrl(), null);
		if(!supernodeData.isEmpty()) {
			JSONObject supernodeJsonObject = JSON.parseObject(supernodeData);
			if(supernodeJsonObject != null && supernodeJsonObject.containsKey("nodes")) {
				//date now
				String nowDate = sdf.format(new Date());
				//saving list
				List<Supernodes> supernodeList = new ArrayList<Supernodes>();
				JSONArray nodesArray = supernodeJsonObject.getJSONArray("nodes");
				for(int i = 0; i < nodesArray.size(); i++) {
					Supernodes supernode = new Supernodes();
					JSONObject nodeObject = nodesArray.getJSONObject(i);
					if(nodeObject != null && nodeObject.containsKey("id") && nodeObject.containsKey("alias") 
							&& nodeObject.containsKey("ip") && nodeObject.getString("payoutAddress")!= null) {
						supernode.setId(nodeObject.getIntValue("id"));
						supernode.setName(nodeObject.getString("alias"));
						supernode.setHost(nodeObject.getString("ip"));
						supernode.setTime(nowDate);
						supernode.setPayoutaddress(nodeObject.getString("payoutAddress"));
						supernodeList.add(supernode);
					}
				}
				//save into DB
				if(supernodeList.size()>0) {
					supernodeService.deleteAll();
					supernodeService.insertList(supernodeList);
				}
			}
		}
	}

}
