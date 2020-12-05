package com.nemtool.explorer.mapper;

import com.nemtool.explorer.pojo.Accounts;
import com.nemtool.explorer.pojo.AccountsExample;
import com.nemtool.explorer.pojo.AccountsWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AccountsMapper {
    int countByExample(AccountsExample example);

    int deleteByExample(AccountsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Accounts record);

    int insertSelective(Accounts record);

    List<AccountsWithBLOBs> selectByExampleWithBLOBs(AccountsExample example);

    List<Accounts> selectByExample(AccountsExample example);

    AccountsWithBLOBs selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AccountsWithBLOBs record, @Param("example") AccountsExample example);

    int updateByExampleWithBLOBs(@Param("record") AccountsWithBLOBs record, @Param("example") AccountsExample example);

    int updateByExample(@Param("record") Accounts record, @Param("example") AccountsExample example);

    int updateByPrimaryKeySelective(AccountsWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(AccountsWithBLOBs record);

    int updateByPrimaryKey(Accounts record);
    
    void updateAccountsByAddress(Accounts account);
    
    void insertList(List<Accounts> accountsList);
    
    List<String> selectAllAddress();
    
    List<String> selectAllPublickey();
    
    List<Accounts> selectPage();
}