package com.nemtool.explorer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nemtool.explorer.mapper.TableMapper;

/**
*
* @author Masker
* @date 2020.09.03
*/
@Transactional
@Service
public class TableService {

	@Autowired
	private TableMapper tableMapper;
	
	public void createTable() {
		tableMapper.createAccountmosaics();
		tableMapper.createAccountremarks();
		tableMapper.createAccounts();
		tableMapper.createBlocks();
		tableMapper.createErrormessages();
		tableMapper.createMosaics();
		tableMapper.createMosaictransactions();
		tableMapper.createNamespaces();
		tableMapper.createPoll();
		tableMapper.createPollindexes();
		tableMapper.createSupernodepayouts();
		tableMapper.createSupernodes();
		tableMapper.createTransactions();
		tableMapper.createUnconfirmedtransactions();
	}
	
	public void dropTable() {
		tableMapper.dropAccountmosaics();
		tableMapper.dropAccountremarks();
		tableMapper.dropAccounts();
		tableMapper.dropBlocks();
		tableMapper.dropErrormessages();
		tableMapper.dropMosaics();
		tableMapper.dropMosaictransactions();
		tableMapper.dropNamespaces();
		tableMapper.dropPoll();
		tableMapper.dropPollindexes();
		tableMapper.dropSupernodepayouts();
		tableMapper.dropSupernodes();
		tableMapper.dropTransactions();
		tableMapper.dropUnconfirmedtransactions();
	}
	
	public void createIndex() {
		try {
			tableMapper.createIndexBlocksHeight();
		} catch (Exception e) {
			System.out.println("IndexBlocksHeight is exist!");
		}
		try {
			tableMapper.createIndexAccmosAddr();
		} catch (Exception e) {
			System.out.println("IndexAccmosAddr is exist!");
		}
		try {
			tableMapper.createIndexAccmosId();
		} catch (Exception e) {
			System.out.println("IndexAccmosId is exist!");
		}
		try {
			tableMapper.createIndexAccreAddr();
		} catch (Exception e) {
			System.out.println("IndexAccreAddr is exist!");
		}
		try {
			tableMapper.createIndexAccountsBalance();
		} catch (Exception e) {
			System.out.println("IndexAccountsBalance is exist!");
		}
		try {
			tableMapper.createIndexAccountsBlocks();
		} catch (Exception e) {
			System.out.println("IndexAccountsBlocks is exist!");
		}
		try {
			tableMapper.createIndexMosaicsId();
		} catch (Exception e) {
			System.out.println("IndexMosaicsId is exist!");
		}
		try {
			tableMapper.createIndexMostraNs();
		} catch (Exception e) {
			System.out.println("IndexMostraNs is exist!");
		}
		try {
			tableMapper.createIndexMostraMos();
		} catch (Exception e) {
			System.out.println("IndexMostraMos is exist!");
		}
		try {
			tableMapper.createIndexMostraTs();
		} catch (Exception e) {
			System.out.println("IndexMostraTs is exist!");
		}
		try {
			tableMapper.createIndexMostraSender();
		} catch (Exception e) {
			System.out.println("IndexMostraSender is exist!");
		}
		try {
			tableMapper.createIndexMostraRici();
		} catch (Exception e) {
			System.out.println("IndexMostraRici is exist!");
		}
		try {
			tableMapper.createIndexMostraNo();
		} catch (Exception e) {
			System.out.println("IndexMostraNo is exist!");
		}
		try {
			tableMapper.createIndexSupernodesPa();
		} catch (Exception e) {
			System.out.println("IndexSupernodesPa is exist!");
		}
		try {
			tableMapper.createIndexSuppRound();
		} catch (Exception e) {
			System.out.println("IndexSuppRound is exist!");
		}
		try {
			tableMapper.createIndexSuppTs();
		} catch (Exception e) {
			System.out.println("IndexSuppTs is exist!");
		}
		try {
			tableMapper.createIndexTransactionsHeight();
		} catch (Exception e) {
			System.out.println("IndexTransactionsHeight is exist!");
		}
		try {
			tableMapper.createIndexTransactionsHash();
		} catch (Exception e) {
			System.out.println("IndexTransactionsHash is exist!");
		}
		try {
			tableMapper.createIndexTransactionsRecipient();
		} catch (Exception e) {
			System.out.println("IndexTransactionsRecipient is exist!");
		}
		try {
			tableMapper.createIndexTransactionsSender();
		} catch (Exception e8) {
			System.out.println("IndexTransactionsSender is exist!");
		}
		try {
			tableMapper.createIndexTransactionsType();
		} catch (Exception e7) {
			System.out.println("IndexTransactionsType is exist!");
		}
		try {
			tableMapper.createIndexTransactionsMosa();
		} catch (Exception e6) {
			System.out.println("IndexTransactionsMosa is exist!");
		}
		try {
			tableMapper.createIndexTransactionsApos();
		} catch (Exception e5) {
			System.out.println("IndexTransactionsApos is exist!");
		}
		try {
			tableMapper.createIndexTransactionsAggr();
		} catch (Exception e4) {
			System.out.println("IndexTransactionsAggr is exist!");
		}
		try {
			tableMapper.createIndexTransactionsTs();
		} catch (Exception e3) {
			System.out.println("IndexTransactionsTs is exist!");
		}
		try {
			tableMapper.createIndexTransactionsUnion();
		} catch (Exception e2) {
			System.out.println("IndexTransactionsUnion is exist!");
		}
		try {
			tableMapper.createIndexUncontranSig();
		} catch (Exception e1) {
			System.out.println("IndexUncontranSig is exist!");
		}
		try {
			tableMapper.createIndexUncontranTs();
		} catch (Exception e) {
			System.out.println("IndexUncontranTs is exist!");
		}
	}
	
}
