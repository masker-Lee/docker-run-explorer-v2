package com.nemtool.explorer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.nemtool.explorer.scheduled.NodeScheduled;

/**
*
* @author Masker
* @date 2020.10.17
*/
@Controller
@RequestMapping("/node")
public class NodeServerController {

	@Autowired
	private NodeScheduled nodescheduled;
	
	/**
     * get node list (including supernodes)
     */
	@RequestMapping(value="/list", method=RequestMethod.POST, produces="application/json;charset=UTF-8")
	@ResponseBody
	public String nodeList() {
		return JSON.toJSONString(nodescheduled.getNodeDtoList());
	}
	
}
