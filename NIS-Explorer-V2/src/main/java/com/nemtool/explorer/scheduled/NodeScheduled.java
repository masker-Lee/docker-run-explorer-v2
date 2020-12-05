package com.nemtool.explorer.scheduled;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nemtool.explorer.dto.NodeDto;
import com.nemtool.explorer.pojo.Supernodes;
import com.nemtool.explorer.service.NisService;
import com.nemtool.explorer.service.SupernodeService;

/**
* node scheduled, get all node data
* @author Masker
* @date 2020.07.28
*/
@Component
public class NodeScheduled {
	
	@Autowired
	private NisService nisService;
	
	@Autowired
	private SupernodeService supernodeService;
	
	// all node
	private List<NodeDto> nodeDtoList;
	
	// get all node
	public List<NodeDto> getNodeDtoList(){
		return nodeDtoList;
	}
	
	// fetch all node
	public void fetchNode() {
		List<NodeDto> nodeDtoArray = new ArrayList<NodeDto>();
		// get all node data from NIS
		try {
			JSONArray nodePeerListReachable = nisService.nodePeerListReachable();
			if(nodePeerListReachable.size() > 0) {
				HashMap<String, Integer> hostMap = new HashMap<>();
				HashMap<String, Integer> nameMap = new HashMap<>();
				// find all supernode from DB
				List<Supernodes> allSupernode = supernodeService.findAllSupernodes();
				// save supernode id,host,name into map
				for(int i = 0; i<allSupernode.size(); i++) {
					Supernodes supernode = allSupernode.get(i);
					hostMap.put(supernode.getHost(), supernode.getId());
					nameMap.put(supernode.getName(), supernode.getId());
				}
				
				NodeDto nodeDto = new NodeDto();
				for(int i = 0; i<nodePeerListReachable.size(); i++) {
					JSONObject nodeJsonObject = nodePeerListReachable.getJSONObject(i);
					if (nodeJsonObject != null && nodeJsonObject.containsKey("metaData") && nodeJsonObject.containsKey("endpoint") && nodeJsonObject.containsKey("identity")) {
						nodeDto = new NodeDto();
						nodeDto.setHost(nodeJsonObject.getJSONObject("endpoint").getString("host"));
						nodeDto.setPort(nodeJsonObject.getJSONObject("endpoint").getString("port"));
						nodeDto.setName(nodeJsonObject.getJSONObject("identity").getString("name"));
						nodeDto.setVersion(nodeJsonObject.getJSONObject("metaData").getString("version"));
						
						//discover the supernodes from all the nodes
						if (hostMap.containsKey(nodeDto.getHost())) {
							nodeDto.setSuperNodeID(hostMap.get(nodeDto.getHost()));
						} else if (nameMap.containsKey(nodeDto.getName())) {
							nodeDto.setSuperNodeID(nameMap.get(nodeDto.getName()));
						}
						//set height
						String heightStr = nisService.blockHeightByHostAndPort(nodeDto.getHost(), nodeDto.getPort());
						if (!heightStr.isEmpty() && heightStr != null) {
							JSONObject heightObject = JSON.parseObject(heightStr);
							if (heightObject.containsKey("height")) {
								nodeDto.setHeight(heightObject.getIntValue("height"));
							}
							// add into array
							nodeDtoArray.add(nodeDto);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// set node list
		if (nodeDtoArray.size() > 0) {
			nodeDtoList = new ArrayList<NodeDto>();
			nodeDtoList.addAll(nodeDtoArray);
		}
	}
	
}
