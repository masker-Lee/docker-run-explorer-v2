package com.nemtool.explorer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nemtool.explorer.service.PollindexesService;

/**
*
* @author Masker
* @date 2020.10.21
*/
@Controller
@RequestMapping("/poll")
public class PollServerController {
	
	@Autowired
	private PollindexesService pollindexesService;
	
	final int LISTSIZE = 100;
	
	/**
     * get poll list
     */
	@RequestMapping(value="/list", method=RequestMethod.POST, produces="application/json;charset=UTF-8")
	@ResponseBody
	public String pollList(@RequestBody String bodyStr) {
		JSONObject body = JSON.parseObject(bodyStr);
		int page = 1;
		if (body.containsKey("page")) {
			try {
				page = body.getIntValue("page");
			} catch (Exception e) {
			}
		}
		return JSON.toJSONString(pollindexesService.findPages(page, LISTSIZE));
	}
	
	/**
     * get poll detail from poll id
     */
	@RequestMapping(value="/poll", method=RequestMethod.POST, produces="application/json;charset=UTF-8")
	@ResponseBody
	public String poll(@RequestBody String bodyStr) {
		JSONObject body = JSON.parseObject(bodyStr);
		if (!body.containsKey("address")) {
			return "";
		}
		String address = body.getString("address");
		
		return pollindexesService.pollDetail(address);
	}
	
	/**
     * query result
     */
	@RequestMapping(value="/pollResult", method=RequestMethod.POST, produces="application/json;charset=UTF-8")
	@ResponseBody
	public String pollResult(@RequestBody String bodyStr) {
		JSONObject body = JSON.parseObject(bodyStr);
		if (!body.containsKey("address")) {
			return "";
		}
		String address = body.getString("address");
		
		return pollindexesService.pollResult(address);
	}
	
	/**
     * return the poi poll voters 
     */
	@RequestMapping(value="/pollResultVoters", method=RequestMethod.POST, produces="application/json;charset=UTF-8")
	@ResponseBody
	public String pollResultVoters(@RequestBody String bodyStr) {
		JSONObject body = JSON.parseObject(bodyStr);
		if (!body.containsKey("address") || !body.containsKey("strings")) {
			return "";
		}
		String address = body.getString("address");
		address = address.replace("-", "").replace(" ", "");
		String strings = body.getString("strings");
		
		return pollindexesService.pollResultVoters(address, strings);
	}

}
