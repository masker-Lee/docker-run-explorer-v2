package com.nemtool.explorer.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nemtool.explorer.dto.AccountmosaicDto;
import com.nemtool.explorer.dto.MosaicDto;
import com.nemtool.explorer.dto.MosaictransactionsDto;
import com.nemtool.explorer.pojo.Accountmosaics;
import com.nemtool.explorer.pojo.Mosaics;
import com.nemtool.explorer.pojo.Mosaictransactions;
import com.nemtool.explorer.service.AccountmosaicsService;
import com.nemtool.explorer.service.MosaicTransactionService;
import com.nemtool.explorer.service.MosaicsService;

/**
*
* @author Masker
* @date 2020.10.10
*/
@Controller
@RequestMapping("/mosaic")
public class MosaicServerController {
	
	@Autowired
	private MosaicsService mosaicsService;
	
	@Autowired
	private MosaicTransactionService mosaicTransactionService;
	
	@Autowired
	private AccountmosaicsService accountmosaicsService;
	
	final int MOSAICLISTLIMIT = 50;
	final int MOSAICTRANSFERLISTLIMIT = 50;
	final int MOSAICRICHLISTLIMIT = 100;
	
	/**
     * get mosaic list by namespace
     */
	@RequestMapping(value="/mosaicListByNamespace", method=RequestMethod.POST, produces="application/json;charset=UTF-8")
	@ResponseBody
	public String mosaicListByNamespace(@RequestBody String bodyStr) {
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
		
		List<Mosaics> mosaicListByNamespace = mosaicsService.mosaicListByNamespace(namespace);
		return JSON.toJSONString(mosaicListByNamespace);
	}
	
	/**
     * get mosaic list
     */
	@RequestMapping(value="/mosaicList", method=RequestMethod.POST, produces="application/json;charset=UTF-8")
	@ResponseBody
	public String mosaicList(@RequestBody String bodyStr) {
		JSONObject body = JSON.parseObject(bodyStr);
		long no = 0;
		if (body.containsKey("no")) {
			try {
				no = body.getLongValue("no");
			} catch (Exception e) {
				return "[]";
			}
		}
		List<Mosaics> mosaicList = mosaicsService.mosaicList(no, MOSAICLISTLIMIT);
		return JSON.toJSONString(mosaicList);
	}
	
	/**
     * get mosaic list by mosaic
     */
	@RequestMapping(value="/mosaicListByMosaic", method=RequestMethod.POST, produces="application/json;charset=UTF-8")
	@ResponseBody
	public String mosaicListByMosaic(@RequestBody String bodyStr) {
		JSONObject body = JSON.parseObject(bodyStr);
		if (!body.containsKey("ns") || !body.containsKey("m")) {
			return "[]" ;
		}
		String namespace = body.getString("ns");
		String mosaicName = body.getString("m");
		// validate namespace
		String regex = "^[a-zA-Z0-9_-]+((\\.)[a-zA-Z0-9_-]+)*$";
		boolean flag = Pattern.matches(regex, namespace);
		if (!flag) {
			return "[]" ;
		}
		
		// validate mosaic
		String regex2 = "^[a-zA-Z0-9'_-]+$";
		boolean flag2 = Pattern.matches(regex2, mosaicName);
		if (!flag2) {
			return "[]" ;
		}
		
		Mosaics mosaic = mosaicsService.findOneMosaic(mosaicName, namespace);
		if (mosaic == null) {
			return "";
		} else {
			return JSON.toJSONString(mosaic);
		}
	}
	
	/**
     * get mosaic detail
     */
	@RequestMapping(value="/mosaic", method=RequestMethod.POST, produces="application/json;charset=UTF-8")
	@ResponseBody
	public String mosaic(@RequestBody String bodyStr) {
		JSONObject body = JSON.parseObject(bodyStr);
		if (!body.containsKey("ns") || !body.containsKey("m")) {
			return "[]" ;
		}
		String namespace = body.getString("ns");
		String mosaicName = body.getString("m");
		// validate namespace
		String regex = "^[a-zA-Z0-9_-]+((\\.)[a-zA-Z0-9_-]+)*$";
		boolean flag = Pattern.matches(regex, namespace);
		if (!flag) {
			return "[]" ;
		}
		
		// validate mosaic
		String regex2 = "^[a-zA-Z0-9'_-]+$";
		boolean flag2 = Pattern.matches(regex2, mosaicName);
		if (!flag2) {
			return "[]" ;
		}
		
		Mosaics mosaic = mosaicsService.findOneMosaic(mosaicName, namespace);
		if (mosaic == null) {
			return "";
		} else {
			MosaicDto mosaicDto = new MosaicDto();
			//copy mosaic to mosaicDto
			BeanCopier beanCopier = BeanCopier.create(Mosaics.class, MosaicDto.class, false);
			beanCopier.copy(mosaic, mosaicDto, null);
			if (mosaic.getLevymosaic() != null && !mosaic.getLevymosaic().isEmpty() && 
					mosaic.getLevynamespace() != null && !mosaic.getLevynamespace().isEmpty()) {
				Mosaics findByMosaicId = mosaicsService.findByMosaicId(mosaic.getLevynamespace() + ":" + mosaic.getLevymosaic());
				if (findByMosaicId != null) {
					mosaicDto.setLevyDiv(findByMosaicId.getDivisibility());
				}
			}
			return JSON.toJSONString(mosaicDto);
		}
	}
	
	/**
     * get mosaic transfer record
     */
	@RequestMapping(value="/mosaicTransferRecord", method=RequestMethod.POST, produces="application/json;charset=UTF-8")
	@ResponseBody
	public String mosaicTransferRecord(@RequestBody String bodyStr) {
		JSONObject body = JSON.parseObject(bodyStr);
		if (!body.containsKey("ns") || !body.containsKey("m")) {
			return "[]" ;
		}
		String namespace = body.getString("ns");
		String mosaicName = body.getString("m");
		int pageNo = 1;
		long no = 0L;
		if (body.containsKey("page")) {
			pageNo = body.getIntValue("page");
		}
		if (body.containsKey("no")) {
			no = body.getLongValue("no");
		}
		// validate namespace
		String regex = "^[a-zA-Z0-9_-]+((\\.)[a-zA-Z0-9_-]+)*$";
		boolean flag = Pattern.matches(regex, namespace);
		if (!flag) {
			return "[]" ;
		}
		
		// validate mosaic
		String regex2 = "^[a-zA-Z0-9'_-]+$";
		boolean flag2 = Pattern.matches(regex2, mosaicName);
		if (!flag2) {
			return "[]" ;
		}
		
		List<Mosaictransactions> mosaicTransferList = mosaicTransactionService.mosaicTransferList(mosaicName, namespace, no, pageNo, MOSAICTRANSFERLISTLIMIT);
		return JSON.toJSONString(mosaicTransferList);
	}
	
	/**
     * get mosaic transfer list
     */
	@RequestMapping(value="/mosaicTransferList", method=RequestMethod.POST, produces="application/json;charset=UTF-8")
	@ResponseBody
	public String mosaicTransferList(@RequestBody String bodyStr) {
		JSONObject body = JSON.parseObject(bodyStr);
		String namespace = null;
		if (body.containsKey("ns")) {
			namespace = body.getString("ns");
			// validate namespace
			String regex = "^[a-zA-Z0-9_-]+((\\.)[a-zA-Z0-9_-]+)*$";
			boolean flag = Pattern.matches(regex, namespace);
			if (!flag) {
				namespace = null ;
			}
		}
		
		String mosaicName = null;
		if (body.containsKey("m")) {
			mosaicName = body.getString("m");
			// validate mosaic
			String regex2 = "^[a-zA-Z0-9'_-]+$";
			boolean flag2 = Pattern.matches(regex2, mosaicName);
			if (!flag2) {
				mosaicName = null;
			}
		}	
				
		int pageNo = 1;
		long no = 0L;
		if (body.containsKey("page")) {
			try {
				pageNo = body.getIntValue("page");
			} catch (Exception e) {
			}
		}
		if (body.containsKey("no")) {
			try {
				no = body.getLongValue("no");
			} catch (Exception e) {
			}
		}
		
		List<Mosaictransactions> mosaicTransferList = mosaicTransactionService.mosaicTransferList(mosaicName, namespace, no, pageNo, MOSAICTRANSFERLISTLIMIT);

		ArrayList<MosaictransactionsDto> mosaictransactionsDtoList = new ArrayList<MosaictransactionsDto>();
		MosaictransactionsDto mosaictransactionsDto = new MosaictransactionsDto();
		for (int i = 0; i < mosaicTransferList.size(); i++) {
			Mosaictransactions mosaictransactions = mosaicTransferList.get(i);
			mosaictransactionsDto = new MosaictransactionsDto();
			//copy Mosaictransactions to mosaictransactionsDto
			BeanCopier beanCopier = BeanCopier.create(Mosaictransactions.class, MosaictransactionsDto.class, false);
			beanCopier.copy(mosaictransactions, mosaictransactionsDto, null);
			mosaictransactionsDtoList.add(mosaictransactionsDto);
		}
		
		return setMosaicTXDivisibility(mosaictransactionsDtoList);
	}
	
	/**
     * get mosaic rich list
     */
	@RequestMapping(value="/mosaicRichList", method=RequestMethod.POST, produces="application/json;charset=UTF-8")
	@ResponseBody
	public String mosaicRichList(@RequestBody String bodyStr) {
		JSONObject body = JSON.parseObject(bodyStr);
		if (!body.containsKey("ns") || !body.containsKey("m")) {
			return "[]" ;
		}
		String namespace = body.getString("ns");
		String mosaicName = body.getString("m");
		int pageNo = 1;
		if (body.containsKey("page")) {
			pageNo = body.getIntValue("page");
		}
		// validate namespace
		String regex = "^[a-zA-Z0-9_-]+((\\.)[a-zA-Z0-9_-]+)*$";
		boolean flag = Pattern.matches(regex, namespace);
		if (!flag) {
			return "";
		}
		// validate mosaic
		String regex2 = "^[a-zA-Z0-9'_-]+$";
		boolean flag2 = Pattern.matches(regex2, mosaicName);
		if (!flag2) {
			return "";
		}
		
		String mosaicID = namespace + ":" + mosaicName;
		List<Accountmosaics> mosaicRichList = accountmosaicsService.getMosaicRichList(mosaicID, MOSAICRICHLISTLIMIT, pageNo);
		List<Mosaics> MosaicsList = mosaicsService.findMosaicID(mosaicID);
		int div = 0;
		if (MosaicsList.size() > 0) {
			div = MosaicsList.get(0).getDivisibility();
		}
		ArrayList<AccountmosaicDto> accountmosaicDtoList = new ArrayList<AccountmosaicDto>();
		AccountmosaicDto accountmosaicDto = new AccountmosaicDto();
		for (int i = 0; i < mosaicRichList.size(); i++) {
			Accountmosaics accountmosaics = mosaicRichList.get(i);
			accountmosaicDto = new AccountmosaicDto();
			//copy accountmosaics to accountmosaicDto
			BeanCopier beanCopier = BeanCopier.create(Accountmosaics.class, AccountmosaicDto.class, false);
			beanCopier.copy(accountmosaics, accountmosaicDto, null);
			accountmosaicDto.setNamespace(namespace);
			accountmosaicDto.setMosaic(mosaicName);
			accountmosaicDto.setDiv(div);
			accountmosaicDtoList.add(accountmosaicDto);
		}
		
		return JSON.toJSONString(accountmosaicDtoList);
	}
	
	
	
	/**
	 * set mosaic transactions divisibility
	 */
	@RequestMapping(value="/setMosaicTXDivisibility", method=RequestMethod.POST, produces="application/json;charset=UTF-8")
	@ResponseBody
	public String setMosaicTXDivisibility(@RequestBody List<MosaictransactionsDto> MosaictransactionsDtoList) {
		for (int i = 0; i < MosaictransactionsDtoList.size(); i++) {
			MosaictransactionsDto mosaictransactionsDto = MosaictransactionsDtoList.get(i);
			List<Mosaics> MosaicsList = mosaicsService.findMosaicID(mosaictransactionsDto.getNamespace() + ":" + mosaictransactionsDto.getMosaic());
			if (MosaicsList.size() > 0) {
				Mosaics mosaics = MosaicsList.get(0);
				mosaictransactionsDto.setDiv(mosaics.getDivisibility());
			}
		}
		
		return JSON.toJSONString(MosaictransactionsDtoList);
	}

}
