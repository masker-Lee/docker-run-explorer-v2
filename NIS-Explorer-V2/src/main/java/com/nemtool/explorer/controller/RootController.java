package com.nemtool.explorer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
*
* @author Masker
* @date 2020.10.22
*/
@Controller
public class RootController {

	@RequestMapping("/")
	public ModelAndView index() {
		return new ModelAndView("index.html");
	}
	
	@RequestMapping("/accountlist")
	public String accountlist() {
		return "accountlist.html";
	}
	
	@RequestMapping("/harvesterlist")
	public String harvesterlist() {
		return "harvesterlist.html";
	}
	
	@RequestMapping("/harvestingCalculator")
	public String harvestingCalculator() {
		return "harvestingCalculator.html";
	}
	
	@RequestMapping("/blocklist")
	public String blocklist() {
		return "blocklist.html";
	}
	
	@RequestMapping("/mosaictransfer")
	public String mosaictransfer() {
		return "mosaictransfer.html";
	}
	
	@RequestMapping("/mosaiclist")
	public String mosaiclist() {
		return "mosaiclist.html";
	}
	
	@RequestMapping("/mosaic")
	public String mosaic() {
		return "mosaic.html";
	}
	
	@RequestMapping("/namespacelist")
	public String namespacelist() {
		return "namespacelist.html";
	}
	
	@RequestMapping("/namespace")
	public String namespace() {
		return "namespace.html";
	}
	
	@RequestMapping("/nodelist")
	public String nodelist() {
		return "nodelist.html";
	}
	
	@RequestMapping("/txlist")
	public String txlist() {
		return "txlist.html";
	}
	
	@RequestMapping("/unconfirmedtxlist")
	public String unconfirmedtxlist() {
		return "unconfirmedtxlist.html";
	}
	
	@RequestMapping("/supernodepayout")
	public String supernodepayout() {
		return "supernodepayout.html";
	}
	
	@RequestMapping("/supernodepayout_custom")
	public String supernodepayout_custom() {
		return "supernodepayout_custom.html";
	}
	
	@RequestMapping("/polllist")
	public String polllist() {
		return "polllist.html";
	}
	
	@RequestMapping("/poll")
	public String poll() {
		return "poll.html";
	}
	
	@RequestMapping("/s_account")
	public String s_account() {
		return "s_account.html";
	}
	
	@RequestMapping("/s_block")
	public String s_block() {
		return "s_block.html";
	}
	
	@RequestMapping("/s_tx")
	public String s_tx() {
		return "s_tx.html";
	}
	
	@RequestMapping("/logs")
	public String logs() {
		return "logs.html";
	}
	
	
}
