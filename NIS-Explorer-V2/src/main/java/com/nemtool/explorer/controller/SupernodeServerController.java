package com.nemtool.explorer.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nemtool.explorer.dto.SupernodeDto;
import com.nemtool.explorer.dto.SupernodepayoutsDto;
import com.nemtool.explorer.pojo.Supernodepayouts;
import com.nemtool.explorer.pojo.Supernodes;
import com.nemtool.explorer.service.SupernodeService;
import com.nemtool.explorer.service.SupernodepayoutsService;

/**
*
* @author Masker
* @date 2020.10.16
*/
@Controller
@RequestMapping("/supernode")
public class SupernodeServerController {
	
	@Autowired
	private SupernodeService supernodeService;
	
	@Autowired
	private SupernodepayoutsService supernodepayoutsService;
	
	/**
     * get supernodes payout list
     */
	@RequestMapping(value="/payoutlist", method=RequestMethod.POST, produces="application/json;charset=UTF-8")
	@ResponseBody
	public String payoutList(@RequestBody String bodyStr) {
		JSONObject body = JSON.parseObject(bodyStr);
		if (!body.containsKey("round")) {
			return "[]";
		}
		try {
			body.getIntValue("round");
		} catch (Exception e) {
			return "[]";
		}
		int round = body.getIntValue("round");
		Map<String, String> supernodeNameMap = new HashMap<String, String>();
		Map<String, Integer> supernodeIDMap = new HashMap<String, Integer>();
		//select all supernode from DB
		List<Supernodes> supernodesList = supernodeService.findAllSupernodes();
		if (supernodesList.size() == 0) {
			return "[]";
		}
		//put into map
		for (int i = 0; i < supernodesList.size(); i++) {
			Supernodes supernode = supernodesList.get(i);
			if (supernode.getId() == null || supernode.getName() == null || supernode.getPayoutaddress() == null) {
				continue;
			}
			supernodeNameMap.put(supernode.getPayoutaddress(), supernode.getName());
			supernodeIDMap.put(supernode.getPayoutaddress(), supernode.getId());
		}
		//select from supernodPayout by round
		List<Supernodepayouts> supernodepayoutsList = supernodepayoutsService.findByRound(round);
		if (supernodepayoutsList.size() == 0) {
			return "[]";
		}
		List<SupernodepayoutsDto> arrayList = new ArrayList<SupernodepayoutsDto>();
		SupernodepayoutsDto supernodepayoutsDto = new SupernodepayoutsDto();
		for (int i = 0; i < supernodepayoutsList.size(); i++) {
			Supernodepayouts supernodepayouts = supernodepayoutsList.get(i);
			supernodepayoutsDto = new SupernodepayoutsDto();
			BeanCopier beanCopier = BeanCopier.create(Supernodepayouts.class, SupernodepayoutsDto.class, false);
			beanCopier.copy(supernodepayouts, supernodepayoutsDto, null);
			if (supernodeNameMap.containsKey(supernodepayouts.getRecipient())) {
				supernodepayoutsDto.setSupernodeName(supernodeNameMap.get(supernodepayouts.getRecipient()));
			}
			if (supernodeIDMap.containsKey(supernodepayouts.getRecipient())) {
				supernodepayoutsDto.setSupernodeID(supernodeIDMap.get(supernodepayouts.getRecipient()));
			}
			//add to list
			arrayList.add(supernodepayoutsDto);
		}
		//reverse list
		Collections.reverse(arrayList);
		return JSON.toJSONString(arrayList);
	}
	
	/**
     * get supernodes payout list last 10 rounds
     */
	@RequestMapping(value="/payoutListLast10Rounds", method=RequestMethod.POST, produces="application/json;charset=UTF-8")
	@ResponseBody
	public String payoutListLast10Rounds() {
		Map<String, String> supernodeNameMap = new HashMap<String, String>();
		Map<String, Integer> supernodeIDMap = new HashMap<String, Integer>();
		//select all supernode from DB
		List<Supernodes> supernodesList = supernodeService.findAllSupernodes();
		if (supernodesList.size() == 0) {
			return "[]";
		}
		//put into map
		for (int i = 0; i < supernodesList.size(); i++) {
			Supernodes supernode = supernodesList.get(i);
			if (supernode.getId() == null || supernode.getName() == null || supernode.getPayoutaddress() == null) {
				continue;
			}
			supernodeNameMap.put(supernode.getPayoutaddress(), supernode.getName());
			supernodeIDMap.put(supernode.getPayoutaddress(), supernode.getId());
		}
		//find max round from DB
		int maxRound = supernodepayoutsService.findMaxRound();
		List<SupernodepayoutsDto> arrayList = new ArrayList<SupernodepayoutsDto>();
		SupernodepayoutsDto supernodepayoutsDto = new SupernodepayoutsDto();
		//find last 10 round supernodepayouts from DB
		for (int i = 0; i < 10; i++) {
			List<Supernodepayouts> supernodepayoutsList = supernodepayoutsService.findByRound(maxRound-i*4);
			if (supernodepayoutsList.size() > 0) {
				Supernodepayouts supernodepayouts = supernodepayoutsList.get(0);
				supernodepayoutsDto = new SupernodepayoutsDto();
				BeanCopier beanCopier = BeanCopier.create(Supernodepayouts.class, SupernodepayoutsDto.class, false);
				beanCopier.copy(supernodepayouts, supernodepayoutsDto, null);
				if (supernodeNameMap.containsKey(supernodepayouts.getRecipient())) {
					supernodepayoutsDto.setSupernodeName(supernodeNameMap.get(supernodepayouts.getRecipient()));
				}
				if (supernodeIDMap.containsKey(supernodepayouts.getRecipient())) {
					supernodepayoutsDto.setSupernodeID(supernodeIDMap.get(supernodepayouts.getRecipient()));
				}
				//add to list
				arrayList.add(supernodepayoutsDto);
			}
		}
		return JSON.toJSONString(arrayList);
	}
	
	/**
     * get supernodes payout rounds list
     */
	@RequestMapping(value="/payoutRoundlist", method=RequestMethod.POST, produces="application/json;charset=UTF-8")
	@ResponseBody
	public String payoutRoundList() {
		//find max round from DB
		int maxRound = supernodepayoutsService.findMaxRound();
		List<JSONObject> payoutRoundList = new ArrayList<JSONObject>();
		for (int i = 0; i < 10; i++) {
			int round = maxRound - i*4;
			JSONObject r_payoutRound = new JSONObject();
			r_payoutRound.put("key", (round-3) + "-" + round);
			r_payoutRound.put("value", round);
			payoutRoundList.add(r_payoutRound);
		}
		return JSON.toJSONString(payoutRoundList);
	}
	
	/**
     * get supernodes list
     */
	@RequestMapping(value="/supernodeList", method=RequestMethod.POST, produces="application/json;charset=UTF-8")
	@ResponseBody
	public String supernodeList() {
		ArrayList<SupernodeDto> arrayList = new ArrayList<SupernodeDto>();
		SupernodeDto supernodeDto = new SupernodeDto();
		List<Supernodes> supernodesList = supernodeService.findAllSupernodes();
		for (int i = 0; i < supernodesList.size(); i++) {
			Supernodes supernode = supernodesList.get(i);
			supernodeDto = new SupernodeDto();
			BeanCopier beanCopier = BeanCopier.create(Supernodes.class, SupernodeDto.class, false);
			beanCopier.copy(supernode, supernodeDto, null);
			arrayList.add(supernodeDto);
		}
		
		return JSON.toJSONString(arrayList);
	}
	
	/**
	 * get supernodes selected payout list last 10 rounds
	 */
	@RequestMapping(value="/selectedPayoutList10Rounds", method=RequestMethod.POST, produces="application/json;charset=UTF-8")
	@ResponseBody
	public String selectedPayoutList10Rounds(@RequestBody String bodyStr) {
		JSONObject body = JSON.parseObject(bodyStr);
		if (!body.containsKey("supernodeName")) {
			return "";
		}
		String supernodeName = body.getString("supernodeName");
		int page = 1;
		if (body.containsKey("page")) {
			page = body.getIntValue("page");
		}
		String[] supernodeNameArr = supernodeName.split(",");
		List<Supernodes> supernodesList = new ArrayList<Supernodes>();
		for (int i = 0; i < supernodeNameArr.length; i++) {
			String nameStr = supernodeNameArr[i];
			List<Supernodes> findByNameList = supernodeService.findByName(nameStr);
			supernodesList.addAll(findByNameList);
		}
		Map<String, String> supernodeNameMap = new HashMap<String, String>();
		Map<String, Integer> supernodeIDMap = new HashMap<String, Integer>();
		//put into map
		for (int i = 0; i < supernodesList.size(); i++) {
			Supernodes supernode = supernodesList.get(i);
			if (supernode.getId() == null || supernode.getName() == null || supernode.getPayoutaddress() == null) {
				continue;
			}
			supernodeNameMap.put(supernode.getPayoutaddress(), supernode.getName());
			supernodeIDMap.put(supernode.getPayoutaddress(), supernode.getId());
		}
		//find max round from DB
		int maxRound = supernodepayoutsService.findMaxRound();
		List<SupernodepayoutsDto> arrayList = new ArrayList<SupernodepayoutsDto>();
		SupernodepayoutsDto supernodepayoutsDto = new SupernodepayoutsDto();
		//find last 10 round supernodepayouts from DB
		for (int i = 0 + 10*(page-1); i < (10 + 10*(page-1)); i++) {
			List<Supernodepayouts> supernodepayoutsList = supernodepayoutsService.findByRound(maxRound-i*4);
			for (int j = 0; j < supernodepayoutsList.size(); j++) {
				Supernodepayouts supernodepayouts = supernodepayoutsList.get(j);
				supernodepayoutsDto = new SupernodepayoutsDto();
				BeanCopier beanCopier = BeanCopier.create(Supernodepayouts.class, SupernodepayoutsDto.class, false);
				beanCopier.copy(supernodepayouts, supernodepayoutsDto, null);
				if (supernodeNameMap.containsKey(supernodepayouts.getRecipient())) {
					supernodepayoutsDto.setSupernodeName(supernodeNameMap.get(supernodepayouts.getRecipient()));
				}
				if (supernodeIDMap.containsKey(supernodepayouts.getRecipient())) {
					supernodepayoutsDto.setSupernodeID(supernodeIDMap.get(supernodepayouts.getRecipient()));
					//add to list
					arrayList.add(supernodepayoutsDto);
				}
			}
		}
		return JSON.toJSONString(arrayList);
	}

}
