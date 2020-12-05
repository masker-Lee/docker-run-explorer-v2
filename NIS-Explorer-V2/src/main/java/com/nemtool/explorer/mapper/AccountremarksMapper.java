package com.nemtool.explorer.mapper;

import com.nemtool.explorer.pojo.Accountremarks;
import com.nemtool.explorer.pojo.AccountremarksExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AccountremarksMapper {
    int countByExample(AccountremarksExample example);

    int deleteByExample(AccountremarksExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Accountremarks record);

    int insertSelective(Accountremarks record);

    List<Accountremarks> selectByExampleWithBLOBs(AccountremarksExample example);

    List<Accountremarks> selectByExample(AccountremarksExample example);

    Accountremarks selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Accountremarks record, @Param("example") AccountremarksExample example);

    int updateByExampleWithBLOBs(@Param("record") Accountremarks record, @Param("example") AccountremarksExample example);

    int updateByExample(@Param("record") Accountremarks record, @Param("example") AccountremarksExample example);

    int updateByPrimaryKeySelective(Accountremarks record);

    int updateByPrimaryKeyWithBLOBs(Accountremarks record);

    int updateByPrimaryKey(Accountremarks record);
    
    Accountremarks findByAddress(String address);
    
    int deleteByaddress(String address);
    
    void insertList(List<Accountremarks> accountremarksList);
}