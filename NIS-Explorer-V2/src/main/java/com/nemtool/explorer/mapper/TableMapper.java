package com.nemtool.explorer.mapper;
/**
*
* @author Masker
* @date 2020.09.03
*/
public interface TableMapper {

	void createBlocks();
	void createAccountmosaics();
	void createAccountremarks();
	void createAccounts();
	void createErrormessages();
	void createMosaics();
	void createMosaictransactions();
	void createNamespaces();
	void createPoll();
	void createPollindexes();
	void createSupernodes();
	void createSupernodepayouts();
	void createTransactions();
	void createUnconfirmedtransactions();
	
	void dropBlocks();
	void dropAccountmosaics();
	void dropAccountremarks();
	void dropAccounts();
	void dropErrormessages();
	void dropMosaics();
	void dropMosaictransactions();
	void dropNamespaces();
	void dropPoll();
	void dropPollindexes();
	void dropSupernodes();
	void dropSupernodepayouts();
	void dropTransactions();
	void dropUnconfirmedtransactions();
	
	void createIndexBlocksHeight();
	void createIndexAccmosAddr();
	void createIndexAccmosId();
	void createIndexAccreAddr();
	void createIndexAccountsBalance();
	void createIndexAccountsBlocks();
	void createIndexMosaicsId();
	void createIndexMostraNs();
	void createIndexMostraMos();
	void createIndexMostraTs();
	void createIndexMostraSender();
	void createIndexMostraRici();
	void createIndexMostraNo();
	void createIndexSupernodesPa();
	void createIndexSuppRound();
	void createIndexSuppTs();
	void createIndexTransactionsHeight();
	void createIndexTransactionsHash();
	void createIndexTransactionsSender();
	void createIndexTransactionsRecipient();
	void createIndexTransactionsType();
	void createIndexTransactionsMosa();
	void createIndexTransactionsApos();
	void createIndexTransactionsAggr();
	void createIndexTransactionsTs();
	void createIndexTransactionsUnion();
	void createIndexUncontranSig();
	void createIndexUncontranTs();
	
}
