package com.nemtool.explorer.mapper;

import com.nemtool.explorer.pojo.Unconfirmedtransactions;
import com.nemtool.explorer.pojo.UnconfirmedtransactionsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UnconfirmedtransactionsMapper {
    int countByExample(UnconfirmedtransactionsExample example);

    int deleteByExample(UnconfirmedtransactionsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Unconfirmedtransactions record);

    int insertSelective(Unconfirmedtransactions record);

    List<Unconfirmedtransactions> selectByExampleWithBLOBs(UnconfirmedtransactionsExample example);

    List<Unconfirmedtransactions> selectByExample(UnconfirmedtransactionsExample example);

    Unconfirmedtransactions selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Unconfirmedtransactions record, @Param("example") UnconfirmedtransactionsExample example);

    int updateByExampleWithBLOBs(@Param("record") Unconfirmedtransactions record, @Param("example") UnconfirmedtransactionsExample example);

    int updateByExample(@Param("record") Unconfirmedtransactions record, @Param("example") UnconfirmedtransactionsExample example);

    int updateByPrimaryKeySelective(Unconfirmedtransactions record);

    int updateByPrimaryKeyWithBLOBs(Unconfirmedtransactions record);

    int updateByPrimaryKey(Unconfirmedtransactions record);
}