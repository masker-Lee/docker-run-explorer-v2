package com.nemtool.explorer.init;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nemtool.explorer.config.Config;
import com.nemtool.explorer.pojo.AccountH2;
import com.nemtool.explorer.pojo.Accountmosaics;
import com.nemtool.explorer.pojo.Accounts;
import com.nemtool.explorer.pojo.BlockH2;
import com.nemtool.explorer.pojo.Blocks;
import com.nemtool.explorer.pojo.Mosaics;
import com.nemtool.explorer.pojo.Mosaictransactions;
import com.nemtool.explorer.pojo.Namespaces;
import com.nemtool.explorer.pojo.Supernodepayouts;
import com.nemtool.explorer.pojo.TransactionH2;
import com.nemtool.explorer.pojo.Transactions;
import com.nemtool.explorer.service.AccountmosaicsService;
import com.nemtool.explorer.service.AccountsService;
import com.nemtool.explorer.service.BlocksService;
import com.nemtool.explorer.service.MosaicTransactionService;
import com.nemtool.explorer.service.MosaicsService;
import com.nemtool.explorer.service.NamespacesService;
import com.nemtool.explorer.service.NisService;
import com.nemtool.explorer.service.SupernodepayoutsService;
import com.nemtool.explorer.service.TransactionsService;
import com.nemtool.explorer.serviceH2.AccountH2Service;
import com.nemtool.explorer.serviceH2.BlocksH2Service;
import com.nemtool.explorer.serviceH2.TransactionH2Service;
import com.nemtool.explorer.serviceH2mem.AccountH2MemService;
import com.nemtool.explorer.serviceH2mem.BlocksH2MemService;
import com.nemtool.explorer.util.Account;
import com.nemtool.explorer.util.CommonUtil;
import com.nemtool.explorer.util.ExceptionsUtil;
import com.nemtool.explorer.util.HexEncoder;
import com.nemtool.explorer.util.TimeUtil;

/**
* update all blocks data
* @author Masker
* @date 2020.07.28
*/
@Component
public class BlockDataFetch {
	
	private final static Logger logger = LoggerFactory.getLogger(BlockDataFetch.class);
	
	@Autowired
	private Config config;
	
	@Autowired
	private NisService nisService;
	
	@Autowired
	private BlocksService blocksService;
	
	@Autowired
	private TransactionsService transactionsService;
	
	@Autowired
	private AccountsService accountsService;
	
	@Autowired
	private AccountmosaicsService accountmosaicsService;
	
	@Autowired
	private MosaicTransactionService mosaicTransactionService;
	
	@Autowired
	private NamespacesService namespacesService;
	
	@Autowired
	private MosaicsService mosaicsService;
	
	@Autowired
	private SupernodepayoutsService supernodepayoutsService;
	
	@Autowired
	private AccountH2Service accountH2Service;
	
	@Autowired
	private AccountH2MemService accountH2MemService;
	
	@Autowired
	private BlocksH2Service blocksH2Service;
	
	@Autowired
	private BlocksH2MemService blocksH2MemService;
	
	@Autowired
	private TransactionH2Service transactionH2Service;
	
	//insertList
	private List<Accounts> accountsInsertList = new ArrayList<Accounts>();
	private List<Accountmosaics> accountmosaicsInsertList = new ArrayList<Accountmosaics>();
	private List<Blocks> blocksInserList = new ArrayList<Blocks>();
	private List<Mosaics> mosaicsInsertList = new ArrayList<Mosaics>();
	private List<Mosaictransactions> mosaictransactionsInsertList = new ArrayList<Mosaictransactions>();
	private List<Supernodepayouts> supernodepayoutsInsertList = new ArrayList<Supernodepayouts>();
	private List<Transactions> transactionsInsertList = new ArrayList<Transactions>();
	
	//loaded set
	private Set<String> loadedAccountSet = new HashSet<String>();
	private Set<String> loadTransactionHashSet = new HashSet<String>();
	private Set<String> mosaicsIDSet;
	private Set<Long> loadedSupnodepayoutTimestamp;
	
	//max block in h2 DB
	private int maxBlockInH2 = 0;
	//last block now updating
	private int lastBlockNow = 0;
	//last block list data
	private JSONArray blockListjsonArray;
	//if is update from first block
	private boolean isUpdateFromFirstBlock;
	//if is finish init data
	private boolean isFinishInitData = false;
	//if is finish prepare data
	private boolean isFinishPrepareData = false;
	
	//get init data status
	public boolean getIsFinishInitData() {
		return isFinishInitData;
	}
	
	//get prepare data status
	public boolean getIsFinishPrepareData() {
		return isFinishPrepareData;
	}
	
	/**
	 * load all blocks data and save
	 * @param height
	 * @throws Exception
	 */
	public void loadAllBlocks(int height) throws Exception{
		//set if is update from first block, if height > 1 , yes
		isUpdateFromFirstBlock = height>1?false:true;
		//if update from first block
		if (isUpdateFromFirstBlock) {
			loadNemesisBlock();
		}
		loadBlocks(height);
		while(blockListjsonArray.size() > 0) {
			loadBlocks(lastBlockNow);
		}
		//save data at last
		insertDataToDB();
		//set finished init data
		isFinishInitData = true;
		//clear cache data
		clearCacheData();
	}
	
	/**
	 * prepare some data
	 * @param boo
	 */
	public void prepareData(boolean boo) {
		//copyDatabase if needed
		if (boo) {
			copyDatabase();
			logger.info("copy database successed!");
		}
		//read account data from h2 database
		List<AccountH2> allAccountList = accountH2Service.getAllAccount();
		logger.info("read account successed!");
		//create table account in h2mem database
		accountH2MemService.createTable();
		//insert account data into h2mem database
		accountH2MemService.insertList(allAccountList);
		//create index in account
		accountH2MemService.createIndex();
		logger.info("insert account successed!");
		
		//read blocks data from h2 database
		List<BlockH2> allBlocks = blocksH2Service.getAllBlocks();
		logger.info("read blocks successed!");
		//create table blocks in h2mem database
		blocksH2MemService.createTable();
		//insert blocks data into h2mem database
		blocksH2MemService.insertList(allBlocks);
		allBlocks.clear();
		//create index in blocks
		blocksH2MemService.createIndex();
		logger.info("insert blocks successed!");
		
		//select max block in blocks
		maxBlockInH2 = blocksH2MemService.getMaxHeight();
		logger.info("max block in h2mem:"+ maxBlockInH2);
		//prepare transaction h2 data if needed
		if (boo) {
			//drop table transactionH2
			transactionH2Service.dropTable();
			//read transaction from h2 database
			List<TransactionH2> alltransaction = transactionH2Service.getAllTransaction();
			logger.info("read transaction successed!");
			//create table transaction in mysql database
			transactionH2Service.createTable();
			//insert transaction into mysql database
			logger.info("create table transaction successed!");
			transactionH2Service.insertList(alltransaction);
			alltransaction.clear();
			logger.info("insert transaction successed!");
			//create index in transaction
			transactionH2Service.createIndex();
			logger.info("transaction createIndex successed!");
			//update account
			for (int i = 0; i < allAccountList.size(); i++) {
				createOrUpdateAccount(allAccountList.get(i).getAddress(), allAccountList.get(i).getPublickey(), 0);
			}
			allAccountList.clear();
			insertAccountDataToDB();
			logger.info("update account successed!");
		}
		
		//update loaded mosaicsIDSet
		mosaicsIDSet = new HashSet<String>(mosaicsService.findAllMosaicId());
		//upadte loaded supernodepayout
		loadedSupnodepayoutTimestamp = new HashSet<Long>(supernodepayoutsService.findAllTimestamp());
		//set finished prepare data
		isFinishPrepareData = true;
		logger.info("read mosaicsId successed! And finished prepare data");
	}
	
	/**
	 * load block
	 * @param height
	 */
	public void loadBlocks(int height) {
		String blockHeight = "{\"height\":" + height + "}";
		//get 10 blocks data from NIS
		String blockList = nisService.localChainBlocksAfter(blockHeight);
		if(blockList != null && !blockList.isEmpty() && !"".equals(blockList)) {
			logger.info("update block:"+blockHeight);
			//get the block 
			blockListjsonArray = JSON.parseObject(blockList).getJSONArray("data");
			JSONObject blockListObject = null;
			JSONObject blockObject = null;
			Blocks block;
			for (int i = 0; i < blockListjsonArray.size(); i++) {
				blockListObject = blockListjsonArray.getJSONObject(i);
				//save block
				blockObject = blockListObject.getJSONObject("block");
				block = new Blocks();
				lastBlockNow = blockObject.getIntValue("height");
				block.setHeight(lastBlockNow);
				block.setTimestamp(blockObject.getLongValue("timeStamp"));
				// add block to blockInsertList
				blocksInserList.add(block);
				//create or update account (block creator)
				if(blockObject.containsKey("signer")){
					if(lastBlockNow > maxBlockInH2) {
						this.createOrUpdateAccount(null, blockObject.getString("signer"), lastBlockNow);
					}
				}
				//save txes
				JSONArray txesArray = new JSONArray();
				try {
					txesArray = blockListObject.getJSONArray("txes");
					for(int j = 0; j<txesArray.size(); j++) {
						//handle transaction
						handleTX(txesArray.getJSONObject(j), j, lastBlockNow);
					}
				} catch (Exception exception) {
					logger.info("save txes error！" + ExceptionsUtil.getExceptionAllinformation(exception));
				}
			}
		}
		//save data every 1000 blocks
		if(blocksInserList.size() >= 1000) {
			insertDataToDB();
		}
	}
	
	/**
	 * Init Nemesis Block
	 */
	public void loadNemesisBlock(){
		logger.info("start update nemesis block！");
		JSONObject block = blocksService.blockAtPublic(1);
		if(block==null){
			return;
		}
		//save Nemesis block
		Blocks blockObject = new Blocks();
		blockObject.setHeight(1);
		blockObject.setTimestamp(block.getLongValue("timeStamp"));
		blocksInserList.add(blockObject);
		//create transactions
		JSONArray transactions = block.getJSONArray("transactions");
		JSONObject transaction = null;
		Transactions transactionObject;
		for(int i=0;i<transactions.size();i++){
			transaction = transactions.getJSONObject(i);
			if(transaction==null){
				continue;
			}
			transactionObject = new Transactions();
			transactionObject.setHash("#NemesisBlock#"+(i+1));
			transactionObject.setHeight(1);
			if(transaction.containsKey("signer")){
				transactionObject.setSender(accountH2MemService.publicKeyToAddress(transaction.getString("signer")));
			} else {
				transactionObject.setSender("");
			}
			if(transaction.containsKey("recipient")){
				transactionObject.setRecipient(transaction.getString("recipient"));
			} else if(transaction.containsKey("remoteAccount")){
				transactionObject.setRecipient(accountH2MemService.publicKeyToAddress(transaction.getString("remoteAccount")));
			} else {
				transactionObject.setRecipient("");
			}
			if(transaction.containsKey("amount")){
				transactionObject.setAmount(transaction.getLongValue("amount"));
			} else {
				transactionObject.setAmount(0L);
			}
			if(transaction.containsKey("fee")){
				transactionObject.setFee(transaction.getLongValue("fee"));
			} else {
				transactionObject.setFee(0L);
			}
			if(transaction.containsKey("timeStamp")){
				transactionObject.setTimestamp(transaction.getLongValue("timeStamp"));
			} else {
				transactionObject.setTimestamp(0L);
			}
			if(transaction.containsKey("deadline")){
				transactionObject.setDeadline(transaction.getLongValue("deadline"));
			} else {
				transactionObject.setDeadline(0L);
			}
			if(transaction.containsKey("signature")){
				transactionObject.setSignature(transaction.getString("signature"));
			} else {
				transactionObject.setSignature("");
			}
			if(transaction.containsKey("type")){
				transactionObject.setType(transaction.getIntValue("type"));
			} else {
				transactionObject.setType(0);
			}
			//check if loaded
			if(!loadTransactionHashSet.contains(transactionObject.getHash())) {
				transactionsInsertList.add(transactionObject);
				loadTransactionHashSet.add(transactionObject.getHash());
			}
			//save or update mosaic
			if(i == 0) {
				saveOrUpdateMosaic(transactionObject, null, 0);
			}
		}
		//save into DB
		insertDataToDB();
		logger.info("Init Nemesis Block end");
	}
	
	/**
	 * handle transaction
	 * @param txes
	 * @param index
	 * @param height
	 */
	public void handleTX(JSONObject txes, int index, int height) {
		if(txes==null || txes.size()==0){
			return ;
		}
		JSONObject tx = txes.getJSONObject("tx");
		if(tx==null){
			return;
		}
		Transactions transactions = new Transactions();
		transactions.setHash(txes.getString("hash"));
		transactions.setHeight(height);
		long amountForMosaic = 0;
		if (tx.containsKey("amount")) {
			amountForMosaic = tx.getLongValue("amount");
		}
		if(tx.containsKey("signer")){
			transactions.setSender(accountH2MemService.publicKeyToAddress(tx.getString("signer")));
		} else {
			transactions.setSender("");
		}
		if(tx.containsKey("recipient")){
			transactions.setRecipient(tx.getString("recipient"));
		} else if(tx.containsKey("remoteAccount")){
			transactions.setRecipient(accountH2MemService.publicKeyToAddress(tx.getString("remoteAccount")));
		} else {
			transactions.setRecipient("");
		}
		if(tx.containsKey("amount")){
			transactions.setAmount(tx.getLongValue("amount"));
		} else {
			transactions.setAmount(0L);
		}
		if(tx.containsKey("fee")){
			transactions.setFee(tx.getLongValue("fee"));
		} else {
			transactions.setFee(0L);
		}
		if(tx.containsKey("timeStamp")){
			transactions.setTimestamp(tx.getLongValue("timeStamp"));
		} else {
			transactions.setTimestamp(0L);
		}
		if(tx.containsKey("deadline")){
			transactions.setDeadline(tx.getLongValue("deadline"));
		} else {
			transactions.setDeadline(0L);
		}
		if(tx.containsKey("signature")){
			transactions.setSignature(tx.getString("signature"));
		} else {
			transactions.setSignature("");
		}
		if(tx.containsKey("type")){
			transactions.setType(tx.getIntValue("type"));
		} else {
			transactions.setType(0);
		}
		// check if apostille
		if(transactions.getType() == 257 && transactions.getRecipient().equals(config.getApostilleAccount()) && 
				tx.containsKey("message") && tx.getJSONObject("message").containsKey("type") && tx.getJSONObject("message").getIntValue("type") == 1) {
			String message = HexEncoder.hexToUtf8(tx.getJSONObject("message").getString("payload"));
			if(message.indexOf("HEX:") == 0) {
				transactions.setApostilleflag(1);
			}
		}
		// check if mosaic transafer
		if(transactions.getType() == 257 && tx.containsKey("mosaics") && tx.getJSONArray("mosaics").size()>0) {
			transactions.setMosaictransferflag(1);
		}
		// check if multisig transaction
		if(transactions.getType() == 4100 && tx.containsKey("signatures") && tx.containsKey("otherTrans")){
			if(tx.getJSONObject("otherTrans").containsKey("amount")){
				transactions.setAmount(tx.getJSONObject("otherTrans").getLongValue("amount"));
			} else {
				transactions.setAmount(0L);
			}
			if (tx.getJSONObject("otherTrans").containsKey("amount")) {
				amountForMosaic = tx.getJSONObject("otherTrans").getLongValue("amount");
			}
			if (tx.getJSONObject("otherTrans").containsKey("fee")) {
				transactions.setFee(tx.getJSONObject("otherTrans").getLongValue("fee"));
			} else {
				transactions.setFee(0L);
			}
			if (tx.getJSONObject("otherTrans").containsKey("signer")) {
				transactions.setSender(accountH2MemService.publicKeyToAddress(tx.getJSONObject("otherTrans").getString("signer")));
			} else {
				transactions.setSender("");
			}
			if (tx.getJSONObject("otherTrans").getString("recipient") != null) {
				transactions.setRecipient(tx.getJSONObject("otherTrans").getString("recipient"));
			}
			// check if mosaic transafer
			if(tx.getJSONObject("otherTrans").getIntValue("type") == 257 && tx.getJSONObject("otherTrans").containsKey("mosaics") && tx.getJSONObject("otherTrans").getJSONArray("mosaics").size() > 0) {
				transactions.setMosaictransferflag(1);
			}
		}
		// check if aggregate  modification transaction
		if((transactions.getType() == 4100 && tx.containsKey("otherTrans") && tx.getJSONObject("otherTrans").containsKey("type") && tx.getJSONObject("otherTrans").getIntValue("type") == 4097) || transactions.getType() == 4097) {
			transactions.setAggregateflag(1);
		}
		// correct amount if mosaic
		transactions.setAmount(correctAmountIfMosaic(transactions, tx));
		//insert the transaction into DB
		if(height > maxBlockInH2) {
			transactionsService.createTransaction(transactions);
		} else if(!loadTransactionHashSet.contains(transactions.getHash())) {
			//record hash
			loadTransactionHashSet.add(transactions.getHash());
			if (isUpdateFromFirstBlock) {
				transactionsInsertList.add(transactions);
			} else {
				List<Transactions> findByHashList = transactionsService.findByHash(transactions.getHash());
				if (findByHashList.size() == 0) {
					transactionsInsertList.add(transactions);
				}
			}
		} else {
			return;
		}
		
		// check if mosaic transafer
		if(tx.containsKey("type") && tx.getIntValue("type") == 257 && tx.containsKey("mosaics") && tx.getJSONArray("mosaics").size() > 0) {
			saveMosaicTX(transactions, tx.getJSONArray("mosaics"), index, amountForMosaic);
		}
		// check if multisig transaction
		if(tx.containsKey("type") && tx.getIntValue("type") == 4100 && tx.containsKey("signatures") && tx.containsKey("otherTrans")) {
			// check if mosaic transafer
			if(tx.getJSONObject("otherTrans").containsKey("type") && tx.getJSONObject("otherTrans").getIntValue("type") == 257 && tx.getJSONObject("otherTrans").containsKey("mosaics") && tx.getJSONObject("otherTrans").getJSONArray("mosaics").size() > 0) {
				saveMosaicTX(transactions, tx.getJSONObject("otherTrans").getJSONArray("mosaics"), index, amountForMosaic);
			}
			// save namespace
			saveNamespace(transactions, tx.getJSONObject("otherTrans"), index);
			// save or update mosaic
			saveOrUpdateMosaic(transactions, tx.getJSONObject("otherTrans"), index);
		}
		// save namespace
		saveNamespace(transactions, tx, index);
		// save or update mosaic
		saveOrUpdateMosaic(transactions, tx, index);
		// save supernode payout
		saveSupernodePayout(transactions, tx, index);
		// update the account info which is in DB
		if(height > maxBlockInH2) {
			if(tx.containsKey("signer")){ //as a signer
				this.createOrUpdateAccount(null, tx.getString("signer"), height);
			}
			if(tx.containsKey("recipient")){ //as a recipient
				this.createOrUpdateAccount(tx.getString("recipient"), null, height);
			}
			if(tx.containsKey("cosignatoryAccount")){ //as a cosignatoryAccount
				this.createOrUpdateAccount(tx.getString("cosignatoryAccount"), null, height);
			}
			if(tx.containsKey("otherAccount")){ //as a otherAccount
				this.createOrUpdateAccount(tx.getString("otherAccount"), null, height);
			}
			// update the account which is multisig transaction
			if(tx.containsKey("signatures")) {
				JSONObject signatureItem = null;
				for(int i = 0; i<tx.getJSONArray("signatures").size(); i++) {
					signatureItem = tx.getJSONArray("signatures").getJSONObject(i);
					if(signatureItem.containsKey("otherAccount")) {
						this.createOrUpdateAccount(signatureItem.getString("otherAccount"), null, height);
					}
					if(signatureItem.containsKey("signatureItem")) {
						this.createOrUpdateAccount(null, signatureItem.getString("signatureItem"), height);
					}
				}
			}
			// update the account which is multisig transaction
			if(tx.containsKey("otherTrans")) {
				if(tx.getJSONObject("otherTrans").containsKey("signer")) {
					this.createOrUpdateAccount(null, tx.getJSONObject("otherTrans").getString("signer"), height);
				}
				if(tx.getJSONObject("otherTrans").containsKey("recipient")) {
					this.createOrUpdateAccount(tx.getJSONObject("otherTrans").getString("recipient"), null, height);
				}
			}
		}
	}
	
	/**
	 * correct transactions' amount if mosaic
	 * @param transactions
	 * @param tx
	 * @return
	 */
	public long correctAmountIfMosaic(Transactions transactions, JSONObject tx) {
		long amount = transactions.getAmount();
		JSONArray mosaics = new JSONArray();
		if(tx.containsKey("mosaics") && tx.getJSONArray("mosaics").size() > 0) {
			mosaics = tx.getJSONArray("mosaics");
		}
		if(tx.containsKey("otherTrans") && tx.getJSONObject("otherTrans").containsKey("mosaics") && tx.getJSONObject("otherTrans").getJSONArray("mosaics").size() > 0) {
			mosaics = tx.getJSONObject("otherTrans").getJSONArray("mosaics");
		}
		if(mosaics.size() == 0) {
			return amount;
		}
		amount = 0L;
		JSONObject m = null;
		for(int i = 0; i < mosaics.size(); i++) {
			m = mosaics.getJSONObject(i);
			if(m.containsKey("mosaicId") && m.getJSONObject("mosaicId").containsKey("namespaceId") && "nem".equals(m.getJSONObject("mosaicId").getString("namespaceId")) 
					&& m.getJSONObject("mosaicId").containsKey("name") && "xem".equals(m.getJSONObject("mosaicId").getString("name"))) {
				amount = m.getLongValue("quantity") * (transactions.getAmount() / 1000000);
			}
		}
		if(amount < 1) {
			amount = 0L;
		}
		return amount;
	}
	
	/**
	 * save mosaic transaction
	 */
	public void saveMosaicTX(Transactions transactions, JSONArray mosaics, int index, long amountForMosaic) {
		JSONObject m = null;
		for(int i = 0; i < mosaics.size(); i++) {
			m = mosaics.getJSONObject(i);
			if(m == null || !m.containsKey("mosaicId") || !m.getJSONObject("mosaicId").containsKey("namespaceId") || 
					!m.getJSONObject("mosaicId").containsKey("name") || !m.containsKey("quantity") ) {
				return ;
			}
			Mosaictransactions mosaictransactions = new Mosaictransactions();
			mosaictransactions.setHash(transactions.getHash());
			mosaictransactions.setSender(transactions.getSender());
			mosaictransactions.setRecipient(transactions.getRecipient());
			mosaictransactions.setTimestamp(transactions.getTimestamp());
			mosaictransactions.setNamespace(m.getJSONObject("mosaicId").getString("namespaceId"));
			mosaictransactions.setMosaic(m.getJSONObject("mosaicId").getString("name"));
			mosaictransactions.setQuantity(transactions.getAmount());
			if (amountForMosaic == 0) {
				mosaictransactions.setQuantity(0L);
			} else {
				mosaictransactions.setQuantity(m.getLongValue("quantity") * (amountForMosaic / 1000000));
			}
			// calculate the number, no = block height + tx index + mosaic index
			mosaictransactions.setNo(((long)transactions.getHeight() * 10000 + (index + 1)) * 1000 + (i + 1));
			//check if exit in db
			if(isUpdateFromFirstBlock) {
				mosaictransactionsInsertList.add(mosaictransactions);
			} else {
				Mosaictransactions MosaictransactionsInDB = mosaicTransactionService.findByTransactionNo(mosaictransactions.getNo());
				if (MosaictransactionsInDB == null) {
					mosaictransactionsInsertList.add(mosaictransactions);
				}
			}
		}
	}
		
	/**
	 * save namespace
	 */
	public void saveNamespace(Transactions transactions, JSONObject tx, int index) {
		if(!tx.containsKey("type") || tx.getIntValue("type") != 8193 ) {
			return ;
		}
		Namespaces namespaces = new Namespaces();
		namespaces.setCreator(transactions.getSender());
		namespaces.setHeight(transactions.getHeight());
		namespaces.setTimestamp(tx.getLongValue("timeStamp"));
		//timeStamp add 1 year
		namespaces.setExpiredtime(TimeUtil.getYearAddOneInNem(transactions.getTimestamp()));
		// calculate the number, no = block height + tx index
		namespaces.setNo((long)transactions.getHeight() * 1000 + (index + 1));
		if(!tx.containsKey("parent") || tx.getString("parent") == null) {
			namespaces.setNamespace(tx.getString("newPart"));
			namespaces.setRootnamespace(tx.getString("newPart"));
			// check if renew
			List<Namespaces> namespacesList = namespacesService.findByNamespace(namespaces.getNamespace());
			if(namespacesList.size() == 0) {
				namespacesService.add(namespaces);
			} else {
				for (int i = 0; i < namespacesList.size(); i++) {
					Namespaces namespaces2 = namespacesList.get(i);
					if (namespaces2.getExpiredtime() > namespaces.getTimestamp()) {
						long expiredTime = TimeUtil.getYearAddOneInNem(namespaces2.getExpiredtime());
						namespacesService.updateNamespaceExpiredTime(namespaces2, expiredTime);
						return;
					}
				}
				namespacesService.add(namespaces);
			}
		} else {
			namespaces.setNamespace(tx.getString("parent") + "." + tx.getString("newPart"));
			namespaces.setRootnamespace(tx.getString("parent"));
			if(tx.getString("parent").indexOf(".") != -1) {
				namespaces.setRootnamespace(tx.getString("parent").substring(0, tx.getString("parent").indexOf(".")));
			}
			// find by rootNamespace
			List<Namespaces> namespaceListbyRoot = namespacesService.namespaceListbyRoot(namespaces.getRootnamespace(), 0);
			for (int i = 0; i < namespaceListbyRoot.size(); i++) {
				Namespaces namespaces2 = namespaceListbyRoot.get(i);
				if (namespaces2.getExpiredtime() > namespaces.getTimestamp()) {
					namespaces.setRootNamespaceId(namespaces2.getId());
					// save namespace
					namespacesService.add(namespaces);
					// update root namespace
					namespacesService.updateRootNamespace(namespaces);
					break;
				}
			}
		}
	}
	
	/**
	 * save or update mosaic
	 */
	public void saveOrUpdateMosaic(Transactions transactions, JSONObject tx, int index) {
		if(transactions.getHeight() == 1) {
			Mosaics mosaic = new Mosaics();
			mosaic.setMosaicname("xem");
			mosaic.setNamespace("nem");
			mosaic.setMosaicid("nem:xem");
			mosaic.setDescription("");
			mosaic.setTimestamp(transactions.getTimestamp());
			mosaic.setCreator(transactions.getSender());
			mosaic.setHeight(1);
			mosaic.setDivisibility(6);
			mosaic.setInitialsupply(8999999999l);
			mosaic.setSupplymutable(0);
			mosaic.setTransferable(1);
			mosaic.setNo(1001L);
			if(mosaicsIDSet.contains(mosaic.getMosaicid())) {
				mosaicsService.updateMosaic(mosaic);
			} else {
				mosaicsIDSet.add(mosaic.getMosaicid());
				mosaicsInsertList.add(mosaic);
			}
		} else if(tx.containsKey("type") && tx.getIntValue("type") == 16385 && tx.containsKey("mosaicDefinition") && tx.getJSONObject("mosaicDefinition").containsKey("id")){
			// save or update mosaic
			Mosaics mosaic = new Mosaics();
			mosaic.setMosaicname(tx.getJSONObject("mosaicDefinition").getJSONObject("id").getString("name"));
			mosaic.setNamespace(tx.getJSONObject("mosaicDefinition").getJSONObject("id").getString("namespaceId"));
			mosaic.setMosaicid(mosaic.getNamespace() + ":" + mosaic.getMosaicname());
			mosaic.setDescription(tx.getJSONObject("mosaicDefinition").getString("description"));
			mosaic.setTimestamp(tx.getLongValue("timeStamp"));
			//get address from h2mem
			mosaic.setCreator(accountH2MemService.publicKeyToAddress(tx.getJSONObject("mosaicDefinition").getString("creator")));
			mosaic.setHeight(transactions.getHeight());
			if(!tx.getJSONObject("mosaicDefinition").containsKey("properties") || tx.getJSONObject("mosaicDefinition").getJSONArray("properties").size() == 0) {
				return;
			}
			JSONObject property = null;
			for(int i = 0; i < tx.getJSONObject("mosaicDefinition").getJSONArray("properties").size(); i++) {
				property = tx.getJSONObject("mosaicDefinition").getJSONArray("properties").getJSONObject(i);
				if(property.isEmpty() || !property.containsKey("name") || !property.containsKey("value")) {
					continue;
				}
				// mosaic properties
				if("divisibility".equals(property.getString("name"))) {
					mosaic.setDivisibility(property.getIntValue("value"));
				}
				if("initialSupply".equals(property.getString("name"))) {
					mosaic.setInitialsupply(property.getLongValue("value"));
				}
				if("supplyMutable".equals(property.getString("name"))) {
					if("false".equals(property.getString("value"))) {
						mosaic.setSupplymutable(0);
					} else if("true".equals(property.getString("value"))) {
						mosaic.setSupplymutable(1);
					}
				}
				if("transferable".equals(property.getString("name"))) {
					if("false".equals(property.getString("value"))) {
						mosaic.setTransferable(0);
					} else if("true".equals(property.getString("value"))) {
						mosaic.setTransferable(1);
					}
				}
			}
			// mosaic levy
			if(tx.getJSONObject("mosaicDefinition").containsKey("levy") && tx.getJSONObject("mosaicDefinition").getJSONObject("levy").containsKey("type")) {
				JSONObject levy = tx.getJSONObject("mosaicDefinition").getJSONObject("levy");
				mosaic.setLevytype(levy.getIntValue("type"));
				if(levy.containsKey("mosaicId") && levy.getJSONObject("mosaicId").containsKey("namespaceId") && levy.getJSONObject("mosaicId").containsKey("name")) {
					mosaic.setLevynamespace(levy.getJSONObject("mosaicId").getString("namespaceId"));
					mosaic.setLevymosaic(levy.getJSONObject("mosaicId").getString("name"));
					mosaic.setLevyrecipient(levy.getString("recipient"));
					mosaic.setLevyfee(levy.getLongValue("fee"));
				}
			}
			// calculate the number, no = block height + tx index
			mosaic.setNo((long)transactions.getHeight() * 1000 + (index + 1));
			// save mosaic
			if (transactions.getHeight() > maxBlockInH2) {
				List<Mosaics> findMosaicID = mosaicsService.findMosaicID(mosaic.getMosaicid());
				if (findMosaicID.size() > 0) {
					mosaicsService.updateMosaic(mosaic);
				} else {
					mosaicsService.add(mosaic);
				}
			} else {
				if(!mosaicsIDSet.contains(mosaic.getMosaicid())) {
					mosaicsIDSet.add(mosaic.getMosaicid());
					mosaicsInsertList.add(mosaic);
				} else {
					mosaicsService.updateMosaic(mosaic);
				}
			}
		} else if(tx.containsKey("type") && tx.getIntValue("type") == 16386 && tx.containsKey("mosaicId") && tx.containsKey("supplyType") && tx.containsKey("delta")) {
			// update mosaic supply
			String mosaicName = tx.getJSONObject("mosaicId").getString("name");
			String namesapce = tx.getJSONObject("mosaicId").getString("namespaceId");
			int change = 0;
			// increase
			if(tx.getIntValue("supplyType") == 1) {
				change += tx.getIntValue("delta");
			} else if(tx.getIntValue("supplyType") == 2) {
			// decrease
				change -= tx.getIntValue("delta");
			}
			// update mosaic
			mosaicsService.updateMosaicSupply(mosaicName, namesapce, tx.getLongValue("timeStamp"), change, transactions.getHeight());
		}
	}
	
	/**
	 * save supernode payout
	 */
	public void saveSupernodePayout(Transactions transactions, JSONObject tx, int index){
		if((transactions.getSender() != null && !transactions.getSender().equals(config.getSupernodePayoutAccount())) || 
				!tx.containsKey("message") || !tx.getJSONObject("message").containsKey("type") || tx.getJSONObject("message").getIntValue("type") != 1) {
			return;
		}
		String message = CommonUtil.decodeMessage(CommonUtil.jsonString(tx.getJSONObject("message"), "payload"));
		Pattern p = Pattern.compile("Node rewards payout: round (\\d+)-(\\d+)");
		Matcher m = p.matcher(message);
		if(m.find()){
			//check if exist in DB
			List<Supernodepayouts> findByTimestamp = new ArrayList<Supernodepayouts>();
			if (transactions.getHeight() <= maxBlockInH2) {
				if (loadedSupnodepayoutTimestamp.contains(transactions.getTimestamp())) {
					return;
				} else {
					loadedSupnodepayoutTimestamp.add(transactions.getTimestamp());
				}
			} else {
				findByTimestamp = supernodepayoutsService.findByTimestamp(transactions.getTimestamp());
			}
			if (findByTimestamp.size() == 0) {
				Supernodepayouts supernodepayout = new Supernodepayouts();
				supernodepayout.setRound(Integer.valueOf(m.group(2)).intValue());
				supernodepayout.setSender(config.getSupernodePayoutAccount());
				supernodepayout.setRecipient(transactions.getRecipient());
				supernodepayout.setAmount(transactions.getAmount());
				supernodepayout.setFee(transactions.getFee());
				supernodepayout.setTimestamp(transactions.getTimestamp());
				supernodepayoutsInsertList.add(supernodepayout);
			}
		}
	}
	
	
	/**
	 * create or update account
	 * @param address
	 * @param publicKey
	 */
	public void createOrUpdateAccount(String address, String publicKey, int block){
		if(address==null && publicKey==null){
			return;
		}
		//get address info by NIS
		JSONObject accountJSON = null;
		Accounts accounts = new Accounts();
		if(address!=null){
			accountJSON = Account.accountGet(address);
			accounts.setAddress(address);
		} else {
			accountJSON = Account.getAccountJSONFromPublicKey(publicKey);
		}
		if(accountJSON==null || !accountJSON.containsKey("account")){
			return;
		}
		accountJSON = accountJSON.getJSONObject("account");
		if(address == null) {
			address = accountJSON.getString("address");
			accounts.setAddress(address);
		}
		
		accounts.setPublickey(CommonUtil.jsonString(accountJSON, "publicKey"));
		accounts.setBalance(CommonUtil.jsonLong(accountJSON, "balance"));
		accounts.setBlocks(CommonUtil.jsonLong(accountJSON, "harvestedBlocks"));
		accounts.setLabel(CommonUtil.jsonString(accountJSON, "label"));
		//get account harvest info by NIS
		long fees = 0;
		long lastBlock = 0;
		long timeStamp = 0;
		
		if(block>maxBlockInH2) {
			JSONObject totalFeeAndlastBlock = Account.getTotalFeeAndlastBlock(address, new JSONObject());
			if(totalFeeAndlastBlock.containsKey("totalFee")) {
				fees = totalFeeAndlastBlock.getLongValue("totalFee");
			}
			if(totalFeeAndlastBlock.containsKey("lastBlock")) {
				lastBlock = totalFeeAndlastBlock.getIntValue("lastBlock");
			}
			JSONObject accountTransfersJSON = Account.accountTransfersAll(address);
			if(accountTransfersJSON==null || !accountTransfersJSON.containsKey("data")){
				return;
			}
			JSONArray accountTransfersArray = accountTransfersJSON.getJSONArray("data");
			JSONObject accountTransfersItem = null;
			for(int i=0;i<accountTransfersArray.size();i++){
				accountTransfersItem = accountTransfersArray.getJSONObject(i);
				if(!accountTransfersItem.containsKey("transaction")){
					continue;
				}
				timeStamp = CommonUtil.jsonLong(accountTransfersItem.getJSONObject("transaction"), "timeStamp");
				break;
			}
		} else {
			//get account id
			AccountH2 example = new AccountH2();
			example.setAddress(address);
			AccountH2 accountInH2 = accountH2MemService.getByExample(example);
			//get all harvest
			List<BlockH2> blockH2List = blocksH2MemService.getByHarvesterId(accountInH2.getId());
			BlockH2 blockH2 = null;
			for (int i = 0; i < blockH2List.size(); i++) {
				blockH2 = blockH2List.get(i);
				fees += blockH2.getTotalfee();
				if(blockH2.getHeight() > lastBlock) {
					lastBlock = blockH2.getHeight();
				}
			}
			//query lastest transaction timeStamp
			List<TransactionH2> transactionById = transactionH2Service.getTransactionById(accountInH2.getId());
			TransactionH2 transactionH2 = null;
			for (int i = 0; i < transactionById.size(); i++) {
				transactionH2 = transactionById.get(i);
				if(transactionH2.getTimestamp() > timeStamp) {
					timeStamp = transactionH2.getTimestamp();
				}
			}
		}
		accounts.setLastblock(lastBlock);
		accounts.setFees(fees);
		accounts.setTimestamp(timeStamp);
		if(block > maxBlockInH2 ) {
				if((accountsService.checkIfAccountExistByAddress(address).size()>0) 
					|| (accounts.getPublickey()!=null && accountsService.checkIfAccountExistByPublicKey(accounts.getPublickey()).size()>0)){ 
					//update
					accountsService.updateAccountsByAddress(accounts);
				} else { //create
					accountsService.addAccounts(accounts);
				}
		} else {
			accountsInsertList.add(accounts);
		}
		//save or update acount mosaic
		if(block > maxBlockInH2 || !loadedAccountSet.contains(address)) {
			if (block <= maxBlockInH2) {
				loadedAccountSet.add(address);
			}
			String mosaicListByAddress = nisService.mosaicListByAddress(address);
			if(!mosaicListByAddress.isEmpty()) {
				JSONArray mosaicArray = JSON.parseObject(mosaicListByAddress).getJSONArray("data");
				JSONObject mosaicObject = null;
				long quantity = 0L;
				Accountmosaics accountmosaics;
				Accountmosaics accountmosaicsDB;
				for (int i = 0 ; i < mosaicArray.size() ; i++) {
					mosaicObject = mosaicArray.getJSONObject(i);
					quantity = 0L;
					JSONObject mosaicId = new JSONObject();
					try {
						quantity = mosaicObject.getLongValue("quantity");
						mosaicId = mosaicObject.getJSONObject("mosaicId");
					} catch (Exception e) {
						logger.info("quantity or mosaicId is empty!");
						return;
					}
					accountmosaics = new Accountmosaics();
					accountmosaics.setAddress(address);
					accountmosaics.setQuantity(quantity);
					accountmosaics.setMosaicid(mosaicId.getString("namespaceId") + ":" + mosaicId.getString("name"));
					if(block > maxBlockInH2) {
						//insert into mysql db
						accountmosaicsDB = accountmosaicsService.findByAddress(address);
						if(accountmosaicsDB != null) {
							if(!accountmosaicsDB.getMosaicid().equals(accountmosaics.getMosaicid()) || !accountmosaicsDB.getQuantity().equals(accountmosaics.getQuantity())) {
								accountmosaicsService.update(accountmosaics);
							}
						} else {
							accountmosaicsService.addAccountMosaic(accountmosaics);
						}
					} else {
						if(i == (mosaicArray.size()-1)) {
							accountmosaicsInsertList.add(accountmosaics);
						}
					}
				}
			}
		}
		//save account
		if(accountsInsertList.size() > 499) {
			insertAccountDataToDB();
		}
	}
	
	
	/**
	 * copy h2 database file
	 */
	@SuppressWarnings("resource")
	public void copyDatabase() {
		String targetFileDir = "/usr/local/NIS-Explorer-V2/data/nis5_mainnet.h2.db";
		String targetFileDir1 = "/usr/local/NIS-Explorer-V2/data/nis5_mainnet.trace.db";
		String targetFileDir2 = "/usr/local/NIS-Explorer-V2/data/nis5_mainnet.lock.db";
		//String databaseDir = System.getProperty("user.home")+"\\nem\\nis\\data\\nis5_mainnet.h2.db";
		String databaseDir = "/root/nem/nis/data/nis5_mainnet.h2.db";
		//delete exited target file
		File file = new File(targetFileDir);
		if (file.exists() && file.isFile()) {  
	        file.delete();  
		}
		File file1 = new File(targetFileDir1);
		if (file1.exists() && file1.isFile()) {  
	        file1.delete();  
		}
		File file2 = new File(targetFileDir2);
		if (file2.exists() && file2.isFile()) {  
	        file2.delete();  
		}
		
		//copy database
		FileChannel input = null;
	    FileChannel output = null;

	    try {
	        input = new FileInputStream(new File(databaseDir)).getChannel();
	        output = new FileOutputStream(new File(targetFileDir)).getChannel();
	        output.transferFrom(input, 0, input.size());
	    } catch (Exception e) {
			logger.info("copy database error");
		}finally {
	        try {
				input.close();
				output.close();
			} catch (IOException e) {
				logger.info("in and out put close error:" + ExceptionsUtil.getExceptionAllinformation(e));
			}
	        
	    }
	}
	
	/**
	 * save account data
	 */
	public void insertAccountDataToDB() {
		logger.info("insert accountsInsertList, size:"+ accountsInsertList.size());
		accountsService.insertList(accountsInsertList);
		accountsInsertList.clear();
		accountmosaicsService.insertList(accountmosaicsInsertList);
		accountmosaicsInsertList.clear();
	}
	
	/**
	 * save block data
	 */
	public void insertDataToDB() {
		logger.info("start insert data to DB");
			if(mosaicsInsertList.size() > 0) {
				logger.info("mosaicsInsertList.size():" + mosaicsInsertList.size());
				//save mosaicsInsertList
				try {
					mosaicsService.insertList(mosaicsInsertList);
				} catch (Exception e) {
					logger.info(ExceptionsUtil.getExceptionAllinformation(e));
				}
				//clear mosaicsInsertList
				mosaicsInsertList.clear();
			}
			if(mosaictransactionsInsertList.size() > 0) {
				logger.info("mosaictransactionsInsertList.size():" + mosaictransactionsInsertList.size());
				//save mosaictransactionsInsertList
				try {
					mosaicTransactionService.insertList(mosaictransactionsInsertList);
				} catch (Exception e) {
					logger.info(ExceptionsUtil.getExceptionAllinformation(e));
				}
				//clear mosaictransactionsInsertList
				mosaictransactionsInsertList.clear();
			}
			if (supernodepayoutsInsertList.size() > 0) {
				logger.info("supernodepayoutsInsertList.size():" + supernodepayoutsInsertList.size());
				//save mosaictransactionsInsertList
				try {
					supernodepayoutsService.insertList(supernodepayoutsInsertList);
				} catch (Exception e) {
					logger.info(ExceptionsUtil.getExceptionAllinformation(e));
				}
				//clear mosaictransactionsInsertList
				supernodepayoutsInsertList.clear();
			}
			if(transactionsInsertList.size() > 0) {
				logger.info("transactionsInsertList.size():" + transactionsInsertList.size());
				//save transactionsInsertList
				try {
					transactionsService.insertList(transactionsInsertList);
				} catch (Exception e) {
					logger.info(ExceptionsUtil.getExceptionAllinformation(e));
				}
				//clear transactionsInsertList
				transactionsInsertList.clear();
			}
			if(blocksInserList.size() > 0) {
				logger.info("blocksInserList.size():" + blocksInserList.size());
				//save blocksInserList
				try {
					blocksService.insertList(blocksInserList);
				} catch (Exception e) {
					logger.info(ExceptionsUtil.getExceptionAllinformation(e));
				}
				blocksInserList.clear();
			}
		logger.info("insert data to DB successed!");
	}
	
	/**
	 * clear cache data
	 */
	public void clearCacheData() {
		loadedAccountSet = new HashSet<String>();
		loadTransactionHashSet = new HashSet<String>();
		mosaicsIDSet = new HashSet<String>();
		loadedSupnodepayoutTimestamp = new HashSet<Long>();
		
		try {
			accountH2MemService.dropTable();
			blocksH2MemService.dropTable();
		} catch (Exception e) {
		}
	}
	
	

}
