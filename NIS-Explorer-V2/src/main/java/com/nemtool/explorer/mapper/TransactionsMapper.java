package com.nemtool.explorer.mapper;

import com.nemtool.explorer.pojo.Transactions;
import com.nemtool.explorer.pojo.TransactionsExample;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TransactionsMapper {
    int countByExample(TransactionsExample example);

    int deleteByExample(TransactionsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Transactions record);

    int insertSelective(Transactions record);

    List<Transactions> selectByExample(TransactionsExample example);

    Transactions selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Transactions record, @Param("example") TransactionsExample example);

    int updateByExample(@Param("record") Transactions record, @Param("example") TransactionsExample example);

    int updateByPrimaryKeySelective(Transactions record);

    int updateByPrimaryKey(Transactions record);
    
    int queryMaxTransactionHeight();
    
    void createTransactionList(List<Transactions> transactionsList);
    
    List<String> selectAllHash();
    
    List<Transactions> findByTypeOrMos(int type1, int type2, int mos, int skip, int pageSize);
    
}