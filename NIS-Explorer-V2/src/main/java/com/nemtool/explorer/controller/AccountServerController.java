package com.nemtool.explorer.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nemtool.explorer.dto.AccountDto;
import com.nemtool.explorer.dto.HarvestDto;
import com.nemtool.explorer.dto.MosaictransactionsDto;
import com.nemtool.explorer.pojo.Accountremarks;
import com.nemtool.explorer.pojo.Accounts;
import com.nemtool.explorer.pojo.Mosaictransactions;
import com.nemtool.explorer.pojo.Transactions;
import com.nemtool.explorer.service.AccountRemarkService;
import com.nemtool.explorer.service.AccountsService;
import com.nemtool.explorer.service.MosaicTransactionService;
import com.nemtool.explorer.service.NisService;
import com.nemtool.explorer.service.TransactionsService;
import com.nemtool.explorer.util.TimeUtil;


/**
* account controller, api for account
* @author Masker
* @date 2020.09.28
*/
@Controller
@RequestMapping("/account")
public class AccountServerController {
	
	final int LISTSIZE = 100;
	final int MOSIALISTSIZE = 50;
	
	@Autowired
	private AccountsService accountsService;
	
	@Autowired
	private NisService nisService;
	
	@Autowired
	private TransactionsService transactionsService;
	
	@Autowired
	private MosaicTransactionService mosaicTransactionservice;
	
	@Autowired
	private MosaicServerController mosaicServiceController;
	
	@Autowired
	private AccountRemarkService accountremarkService;
	

	/**
     * get account list
     */
	@RequestMapping(value="/accountList", method=RequestMethod.POST, produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getAccountList(@RequestBody String bodyStr) {
		JSONObject body = JSON.parseObject(bodyStr);
		int page = 1;
		if (body.containsKey("page")) {
			try {
				page = body.getIntValue("page");
			} catch (Exception e) {
				return "[]";
			}
		}
		// sql orderBy
		String orderBy = "balance desc";
		// select all account pages
		List<Accounts> accountList = accountsService.selectPage(page, LISTSIZE, orderBy);
		// create return list
		List<AccountDto> accountDtoList = new ArrayList<AccountDto>();
		AccountDto accountDto = new AccountDto();
		for (int i = 0; i < accountList.size(); i++) {
			Accounts accounts = accountList.get(i);
			
			accountDto = new AccountDto();
			accountDto.setAddress(accounts.getAddress());
			accountDto.setBalance(accounts.getBalance());
			accountDto.setTimeStamp(accounts.getTimestamp());
			Accountremarks accountremark = accountremarkService.findByAddress(accounts.getAddress());
			if (accountremark != null) {
				accountDto.setRemark(accountremark.getRemark());
			}
			
			double importance = 0.0D;
			//get account data from nis
			String resultAcc = nisService.accountByAddress(accounts.getAddress());
			if (resultAcc != null && !resultAcc.isEmpty()) {
				JSONObject resultAccObject = JSON.parseObject(resultAcc);
				if (resultAccObject.containsKey("account") && resultAccObject.getJSONObject("account").containsKey("importance")) {
					importance = resultAccObject.getJSONObject("account").getDoubleValue("importance");
				}
			}
			//set importance
			accountDto.setImportance(importance);
			//add to accountDtoList
			accountDtoList.add(accountDto);
		}
		return JSON.toJSONString(accountDtoList);
	}
	
	/**
     * get havrester list
     */
	@RequestMapping(value="/harvesterList", method=RequestMethod.POST, produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getHarvesterList(@RequestBody String bodyStr) {
		JSONObject body = JSON.parseObject(bodyStr);
		int page = 1;
		if (body.containsKey("page")) {
			try {
				page = body.getIntValue("page");
			} catch (Exception e) {
				return "[]";
			}
		}
		String orderBy = "blocks desc";
		List<Accounts> accountList = accountsService.selectPage(page, LISTSIZE, orderBy);
		List<AccountDto> accountDtoList = new ArrayList<AccountDto>();
		AccountDto accountDto = new AccountDto();
		for (int i = 0; i < accountList.size(); i++) {
			Accounts accounts = accountList.get(i);
			
			accountDto = new AccountDto();
			accountDto.setAddress(accounts.getAddress());
			accountDto.setBlocks(accounts.getBlocks());
			accountDto.setFees(accounts.getFees());
			accountDto.setLastblock(accounts.getLastblock());
			Accountremarks accountremark = accountremarkService.findByAddress(accounts.getAddress());
			if (accountremark != null) {
				accountDto.setRemark(accountremark.getRemark());
			}
			
			double importance = 0.0D;
			String resultAcc = nisService.accountByAddress(accounts.getAddress());
			if (resultAcc != null && !resultAcc.isEmpty()) {
				JSONObject resultAccObject = JSON.parseObject(resultAcc);
				if (resultAccObject.containsKey("account") && resultAccObject.getJSONObject("account").containsKey("importance")) {
					importance = resultAccObject.getJSONObject("account").getDoubleValue("importance");
				}
			}
			//set importance
			accountDto.setImportance(importance);
			//add to accountDtoList
			accountDtoList.add(accountDto);
		}
		return JSON.toJSONString(accountDtoList);
	}
	
	/**
     * get account detail info
     */
	@RequestMapping(value="/detail", method=RequestMethod.POST, produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getDetail(@RequestBody String bodyStr) {
		JSONObject body = JSON.parseObject(bodyStr);
		if (!body.containsKey("address")) {
			return "";
		}
		String address = body.getString("address");
		address = address.replace("-", "").replace(" ", "");
		if (address.isEmpty()) {
			return "address error";
		}
		
		AccountDto accountDto = new AccountDto();
		String resultAcc = nisService.accountByAddress(address);
		if (resultAcc != null && !resultAcc.isEmpty()) {
			JSONObject resultAccObject = JSON.parseObject(resultAcc);
			if (resultAccObject.containsKey("account") && resultAccObject.getJSONObject("account").containsKey("importance")) {
				JSONObject accountJson = resultAccObject.getJSONObject("account");
				JSONObject metaJson = resultAccObject.getJSONObject("meta");
				
				accountDto.setAddress(address);
				accountDto.setPublickey(accountJson.getString("publicKey"));
				accountDto.setBalance(accountJson.getLongValue("balance"));
				accountDto.setImportance(accountJson.getDoubleValue("importance"));
				
				String label = "";
				if (accountJson.containsKey("label") && accountJson.getString("label") != null) {
					label = accountJson.getString("label");
				}
				accountDto.setLabel(label);
				
				accountDto.setRemoteStatus(metaJson.getString("remoteStatus"));
				accountDto.setHarvestedBlocks(accountJson.getIntValue("harvestedBlocks"));
				accountDto.setVestedBalance(accountJson.getLongValue("vestedBalance"));
				
				if (accountJson.containsKey("multisigInfo") && accountJson.getJSONObject("multisigInfo").containsKey("minCosignatories")) {
					if (accountJson.getJSONObject("multisigInfo").getIntValue("minCosignatories") == 0 && metaJson.containsKey("cosignatories")) {
						accountDto.setMinCosignatories(metaJson.getJSONArray("cosignatories").size());
					} else {
						accountDto.setMinCosignatories(accountJson.getJSONObject("multisigInfo").getIntValue("minCosignatories"));
					}
				}
				
				if (metaJson.containsKey("cosignatories") && metaJson.getJSONArray("cosignatories").size() > 0) {
					accountDto.setMultisig(1);
					JSONArray cosignatoriesJSON = metaJson.getJSONArray("cosignatories");
					String str = "";
					for (int i = 0; i < cosignatoriesJSON.size(); i++) {
						str += cosignatoriesJSON.getJSONObject(i).getString("address");
						if (i < (cosignatoriesJSON.size() -1)) {
							str += "<br/>";
						}
					}
					accountDto.setCosignatories(str);
				}
				
				Accountremarks accountremark = accountremarkService.findByAddress(address);
				if (accountremark != null) {
					accountDto.setRemark(accountremark.getRemark());
				}
			}
		}
		
		return JSON.toJSONString(accountDto);
	}
	
	/**
     * get transactions belong this account
     */
	@RequestMapping(value="/detailTXList", method=RequestMethod.POST, produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getDetailTXList(@RequestBody String bodyStr) {
		JSONObject body = JSON.parseObject(bodyStr);
		if (!body.containsKey("address")) {
			return "";
		}
		String address = body.getString("address");
		address = address.replace("-", "").replace(" ", "");
		int page = 1;
		if (body.containsKey("page")) {
			try {
				page = body.getIntValue("page");
			} catch (Exception e) {
				return "";
			}
		}
		if (address.isEmpty()) {
			return "address error";
		}
		String orderBy = "timeStamp desc";
		List<Transactions> transactionsList = transactionsService.selectByAddressAndPage(address, page, MOSIALISTSIZE, orderBy);
		
		return JSON.toJSONString(transactionsList);
	}
	
	/**
     * get mosaic transactions belong this account
     */
	@RequestMapping(value="/detailMosaicTXList", method=RequestMethod.POST, produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getDetailMosaicTXList(@RequestBody String bodyStr) {
		JSONObject body = JSON.parseObject(bodyStr);
		if (!body.containsKey("address")) {
			return "";
		}
		String address = body.getString("address");
		address = address.replace("-", "").replace(" ", "");
		long no = 0L;
		if (body.containsKey("no")) {
			no = body.getLongValue("no");
		}
		if (address.isEmpty()) {
			return "address error";
		}
		String orderBy = "timeStamp desc";
		List<Mosaictransactions> MosaictransactionsList = mosaicTransactionservice.selectByAddressAndNo(address, no, 1, MOSIALISTSIZE, orderBy);
		List<MosaictransactionsDto> mosaictransactionsDtoList = new ArrayList<MosaictransactionsDto>();
		MosaictransactionsDto mosaictransactionsDto = new MosaictransactionsDto();
		for (int i = 0; i < MosaictransactionsList.size(); i++) {
			Mosaictransactions mosaictransactions = MosaictransactionsList.get(i);
			mosaictransactionsDto = new MosaictransactionsDto();
			//copy Mosaictransactions to mosaictransactionsDto
			BeanCopier beanCopier = BeanCopier.create(Mosaictransactions.class, MosaictransactionsDto.class, false);
			beanCopier.copy(mosaictransactions, mosaictransactionsDto, null);
			
			mosaictransactionsDtoList.add(mosaictransactionsDto);
		}
		
		String mosaictransactionsDtoListStr = mosaicServiceController.setMosaicTXDivisibility(mosaictransactionsDtoList);
		
		return mosaictransactionsDtoListStr;
	}
	
	/**
     * load harvested blocks
     */
	@RequestMapping(value="/loadHarvestBlocks", method=RequestMethod.POST, produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getLoadHarvestBlocks(@RequestBody String bodyStr) {
		JSONObject body = JSON.parseObject(bodyStr);
		if (!body.containsKey("address")) {
			return "";
		}
		String address = body.getString("address");
		address = address.replace("-", "").replace(" ", "");
		long dayTime = TimeUtil.getTimeBeforeOneDayInNem();
		long monthTime = TimeUtil.getTimeBeforeOneMonthInNem();
		int allBlocks = 0;
		int dayBlocks = 0;
		int monthBlocks = 0;
		long allFee = 0L;
		long dayFee = 0L;
		long monthFee = 0L;
		
		String harvestByAddress = nisService.harvestByAddress(address, 0);
		JSONArray parseArray = JSON.parseArray(harvestByAddress);
		for (int i = 0; i < parseArray.size(); i++) {
			allBlocks++;
			JSONObject jsonObject = parseArray.getJSONObject(i);
			if (jsonObject.containsKey("timeStamp")) {
				if (jsonObject.getLongValue("timeStamp") > dayTime) {
					dayBlocks++;
					dayFee += jsonObject.getLongValue("totalFee");
				}
				if (jsonObject.getLongValue("timeStamp") > monthTime) {
					monthBlocks++;
					monthFee += jsonObject.getLongValue("totalFee");
				}
			}
			allFee += jsonObject.getLongValue("totalFee");
		}
		HarvestDto harvestDto = new HarvestDto();
		harvestDto.setAllBlocks(allBlocks);
		harvestDto.setDayBlocks(dayBlocks);
		harvestDto.setMonthBlocks(monthBlocks);
		harvestDto.setAllFee(Math.round(allFee/Math.pow(10,6)));
		harvestDto.setDayFee(Math.round(dayFee/Math.pow(10,6)));
		harvestDto.setMonthFee(Math.round(monthFee/Math.pow(10,6)));
		
		return JSON.toJSONString(harvestDto);
	}

}
