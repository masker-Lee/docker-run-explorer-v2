package com.nemtool.explorer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nemtool.explorer.config.Config;

/**
*
* @author Masker
* @date 2020.10.14
*/
@Controller
@RequestMapping("/sys")
public class SysServerController {
	
	@Autowired
	private Config config;
	
	/**
     * get version info
     */
	@RequestMapping(value="/version", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String version() {
		return config.getVersion();
	}
	
	/**
     * get heartbeat
     */
	@RequestMapping(value="/heartbeat", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String heartbeat() {
		return "{\"code\":1}";
	}
	

}
