package com.nemtool.explorer.mapper;

import com.nemtool.explorer.pojo.Accountmosaics;
import com.nemtool.explorer.pojo.AccountmosaicsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AccountmosaicsMapper {
    int countByExample(AccountmosaicsExample example);

    int deleteByExample(AccountmosaicsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Accountmosaics record);

    int insertSelective(Accountmosaics record);

    List<Accountmosaics> selectByExample(AccountmosaicsExample example);

    Accountmosaics selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Accountmosaics record, @Param("example") AccountmosaicsExample example);

    int updateByExample(@Param("record") Accountmosaics record, @Param("example") AccountmosaicsExample example);

    int updateByPrimaryKeySelective(Accountmosaics record);

    int updateByPrimaryKey(Accountmosaics record);
    
    int updateByAddressSelective(Accountmosaics record);
    
    void insertList(List<Accountmosaics> accountmosaicsList);
}