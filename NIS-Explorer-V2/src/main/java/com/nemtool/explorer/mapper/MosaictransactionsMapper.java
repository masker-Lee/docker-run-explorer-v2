package com.nemtool.explorer.mapper;

import com.nemtool.explorer.pojo.Mosaictransactions;
import com.nemtool.explorer.pojo.MosaictransactionsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MosaictransactionsMapper {
    int countByExample(MosaictransactionsExample example);

    int deleteByExample(MosaictransactionsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Mosaictransactions record);

    int insertSelective(Mosaictransactions record);

    List<Mosaictransactions> selectByExample(MosaictransactionsExample example);

    Mosaictransactions selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Mosaictransactions record, @Param("example") MosaictransactionsExample example);

    int updateByExample(@Param("record") Mosaictransactions record, @Param("example") MosaictransactionsExample example);

    int updateByPrimaryKeySelective(Mosaictransactions record);

    int updateByPrimaryKey(Mosaictransactions record);
    
    int insertList(List<Mosaictransactions> mosaictransactionsList);
    
    List<Long> findAllMosaicTransactionNo();
    
    Mosaictransactions findByTransactionNo(long no);
    
    List<Mosaictransactions> selectByAddressAndNoPage(@Param("address")String address, @Param("no")long no);
}