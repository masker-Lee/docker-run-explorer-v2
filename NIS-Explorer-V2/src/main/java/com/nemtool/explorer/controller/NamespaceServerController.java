package com.nemtool.explorer.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nemtool.explorer.dto.MosaicDto;
import com.nemtool.explorer.dto.MosaictransactionsDto;
import com.nemtool.explorer.dto.NamespaceDto;
import com.nemtool.explorer.pojo.Namespaces;
import com.nemtool.explorer.service.NamespacesService;
import com.nemtool.explorer.service.NisService;

/**
* api for namespace
* @author Masker
* @date 2020.10.13
*/
@Controller
@RequestMapping("/namespace")
public class NamespaceServerController {
	
	final int LISTSIZE = 100;
	final int NAMESPACELISTLIMIT = 50;
	
	@Autowired
	private NamespacesService namespacesService;
	
	@Autowired
	private NisService nisService;
	
	@Autowired
	private MosaicServerController mosaicServiceController;
	
	/**
     * get root namespace list
     */
	@RequestMapping(value="/rootNamespaceList", method=RequestMethod.POST, produces="application/json;charset=UTF-8")
	@ResponseBody
	public String rootNamespaceList(@RequestBody String bodyStr) {
		JSONObject body = JSON.parseObject(bodyStr);
		long no = 0;
		if (body.containsKey("no")) {
			try {
				no = body.getLongValue("no");
			} catch (Exception e) {
			}
		}
		List<Namespaces> NamespacesList = namespacesService.find(no, NAMESPACELISTLIMIT);
		for (int i = 0; i < NamespacesList.size(); i++) {
			Namespaces namespaces = NamespacesList.get(i);
			String subNamespaces = namespaces.getSubnamespaces() == null?"":namespaces.getSubnamespaces();
			namespaces.setSubnamespaces(subNamespaces);
		}
		return JSON.toJSONString(NamespacesList);
	}
	
	/**
     * get root namespace by namespace
     */
	@RequestMapping(value="/rootNamespaceByNamespace", method=RequestMethod.POST, produces="application/json;charset=UTF-8")
	@ResponseBody
	public String rootNamespaceByNamespace(@RequestBody String bodyStr) {
		JSONObject body = JSON.parseObject(bodyStr);
		if (!body.containsKey("ns")) {
			return "[]";
		}
		String namespace = body.getString("ns");
		// validate namespace
		String regex = "^[a-zA-Z0-9_-]+((\\.)[a-zA-Z0-9_-]+)*$";
		boolean flag = Pattern.matches(regex, namespace);
		if (!flag) {
			return "[]" ;
		}
		if(namespace.indexOf(".")!=-1) {
			namespace = namespace.substring(0, namespace.indexOf("."));
		}
		// check namspace exists
		List<Namespaces> findOneNamespaceByName = namespacesService.findOneNamespaceByName(namespace);
		if (findOneNamespaceByName.size() > 0) {
			return JSON.toJSONString(findOneNamespaceByName.get(0));
		} else {
			return "[]";
		}
	}
	
	/**
     * get sub namespace list by root namespace
     */
	@RequestMapping(value="/subNamespaceList", method=RequestMethod.POST, produces="application/json;charset=UTF-8")
	@ResponseBody
	public String subNamespaceList(@RequestBody String bodyStr) {
		JSONObject body = JSON.parseObject(bodyStr);
		if (!body.containsKey("root")) {
			return "[]";
		}
		String rootNamespace = body.getString("root");
		// validate rootNamespace
		String regex = "^([a-zA-Z0-9_-])+$";
		boolean flag = Pattern.matches(regex, rootNamespace);
		if (!flag) {
			return "[]" ;
		}
		List<Namespaces> namespaceList = namespacesService.subNamespaceList(rootNamespace);
		return JSON.toJSONString(namespaceList);
	}
	
	/**
     * get namespace and sub namespace list
     */
	@RequestMapping(value="/namespaceListbyNamespace", method=RequestMethod.POST, produces="application/json;charset=UTF-8")
	@ResponseBody
	public String namespaceListbyNamespace(@RequestBody String bodyStr) {
		JSONObject body = JSON.parseObject(bodyStr);
		if (!body.containsKey("ns") || !body.containsKey("rootNamespaceId")) {
			return "[]";
		}
		int rootNamespaceId = 0;
		try {
			rootNamespaceId = body.getIntValue("rootNamespaceId");
		} catch (Exception e) {
			return "[]";
		}
		if (rootNamespaceId == 0) {
			return "[]";
		}
		String namespace = body.getString("ns");
		// validate namespace
		String regex = "^[a-zA-Z0-9_-]+((\\.)[a-zA-Z0-9_-]+)*$";
		boolean flag = Pattern.matches(regex, namespace);
		if (!flag) {
			return "[]" ;
		}
		// get root namespace
		String rootNamespace = namespace.substring(namespace.indexOf(".")+1);
		List<Namespaces> namespaceList = namespacesService.findById(rootNamespaceId);
		List<Namespaces> namespaceList2 = namespacesService.namespaceListbyRoot(rootNamespace, rootNamespaceId);
		namespaceList.addAll(namespaceList2);
		return JSON.toJSONString(namespaceList);
	}
	
	
	/**
     * get namespace list which the address own
     */
	@RequestMapping(value="/namespaceListByAddress", method=RequestMethod.POST, produces="application/json;charset=UTF-8")
	@ResponseBody
	public String namespaceListByAddress(@RequestBody String bodyStr) {
		JSONObject body = JSON.parseObject(bodyStr);
		if (!body.containsKey("address")) {
			return "";
		}
		String address = body.getString("address");
		address = address.replace("-", "").replace(" ", "");
		if (address == null || address.isEmpty()) {
			return "[]" ;
		}
		String result = nisService.namespaceListByAddress(address);
		if ("[]".equals(result) || result.isEmpty() || JSON.parseObject(result).getJSONArray("data").size() == 0) {
			return "[]" ;
		}
		JSONArray data = JSON.parseObject(result).getJSONArray("data");
		ArrayList<NamespaceDto> namespaceDtoList = new ArrayList<NamespaceDto>();
		NamespaceDto namespaceDto = new NamespaceDto();
		for (int i = 0; i < data.size(); i++) {
			namespaceDto = new NamespaceDto();
			namespaceDto.setNamespace(data.getJSONObject(i).getString("fqn"));
			namespaceDto.setHeight(data.getJSONObject(i).getIntValue("height"));
			
			namespaceDtoList.add(namespaceDto);
		}
		
		return JSON.toJSONString(namespaceDtoList);
	}
	
	/**
     * get mosaic list which the address own
     */
	@RequestMapping(value="/mosaicListByAddress", method=RequestMethod.POST, produces="application/json;charset=UTF-8")
	@ResponseBody
	public String mosaicListByAddress(@RequestBody String bodyStr) {
		JSONObject body = JSON.parseObject(bodyStr);
		if (!body.containsKey("address")) {
			return "";
		}
		String address = body.getString("address");
		address = address.replace("-", "").replace(" ", "");
		if (address == null || address.isEmpty()) {
			return "[]" ;
		}
		String result = nisService.mosaicListByAddress(address);
		if ("[]".equals(result) || result.isEmpty() || JSON.parseObject(result).getJSONArray("data").size() == 0) {
			return "[]" ;
		}
		ArrayList<MosaictransactionsDto> MosaictransactionsDtoList = new ArrayList<MosaictransactionsDto>();
		MosaictransactionsDto mosaictransactionsDto = new MosaictransactionsDto();
		JSONArray data = JSON.parseObject(result).getJSONArray("data");
		for (int i = 0; i < data.size(); i++) {
			mosaictransactionsDto = new MosaictransactionsDto();
			mosaictransactionsDto.setMosaic(data.getJSONObject(i).getJSONObject("mosaicId").getString("name"));
			mosaictransactionsDto.setQuantity(data.getJSONObject(i).getLongValue("quantity"));
			mosaictransactionsDto.setNamespace(data.getJSONObject(i).getJSONObject("mosaicId").getString("namespaceId"));
			mosaictransactionsDto.setId(data.getJSONObject(i).getJSONObject("mosaicId").getString("name"));
			
			MosaictransactionsDtoList.add(mosaictransactionsDto);
		}
		return mosaicServiceController.setMosaicTXDivisibility(MosaictransactionsDtoList);
	}
	
	/**
     * get mosaic list which the namespace own
     */
	@RequestMapping(value="/mosaicListByNamespace", method=RequestMethod.POST, produces="application/json;charset=UTF-8")
	@ResponseBody
	public String mosaicListByNamespace(@RequestBody String bodyStr) {
		JSONObject body = JSON.parseObject(bodyStr);
		if (!body.containsKey("ns")) {
			return "[]";
		}
		String namespace = body.getString("ns");
		int id = 0;
		if (body.containsKey("id")) {
			try {
				id = body.getIntValue("id");
			} catch (Exception e) {
			}
		}
		// validate namespace
		String regex = "^[a-zA-Z0-9_-]+((\\.)[a-zA-Z0-9_-]+)*$";
		boolean flag = Pattern.matches(regex, namespace);
		if (!flag) {
			return "[]" ;
		}
		String result = nisService.allMosaicDefinitionListByNamespace(namespace, id);
		if (result.isEmpty() || "[]".equals(result)) {
			return "[]" ;
		}
		JSONArray resultList = JSON.parseArray(result);
		List<MosaicDto> MosaicDtoList = new ArrayList<MosaicDto>();
		MosaicDto mosaicDto = new MosaicDto();
		for (int i = 0; i < resultList.size(); i++) {
			JSONObject mosaicObj = resultList.getJSONObject(i);
			mosaicDto = new MosaicDto();
			mosaicDto.setNo(i + 1L);
			if (mosaicObj.containsKey("mosaic")) {
				mosaicDto.setMosaicname(mosaicObj.getJSONObject("mosaic").getJSONObject("id").getString("name"));
				if (mosaicObj.getJSONObject("mosaic").containsKey("properties")) {
					JSONArray propertiesList = mosaicObj.getJSONObject("mosaic").getJSONArray("properties");
					for (int j = 0; j < propertiesList.size(); j++) {
						JSONObject property = propertiesList.getJSONObject(j);
						if (property.containsKey("name")) {
							String propertyName = property.getString("name");
							if ("initialSupply".equals(propertyName)) {
								mosaicDto.setInitialsupply(property.getLongValue("value"));
							}
							if ("transferable".equals(propertyName)) {
								mosaicDto.setTransferable(property.getString("value"));
							}
							if ("divisibility".equals(propertyName)) {
								mosaicDto.setDivisibility(property.getIntValue("value"));
							}
						}
						
					}
				}
			}
			
			MosaicDtoList.add(mosaicDto);
		}
		
		return JSON.toJSONString(MosaicDtoList);
	}

}
