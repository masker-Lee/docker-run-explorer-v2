package com.nemtool.explorer.mapper;

import com.nemtool.explorer.pojo.Supernodes;
import com.nemtool.explorer.pojo.SupernodesExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SupernodesMapper {
    int countByExample(SupernodesExample example);

    int deleteByExample(SupernodesExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Supernodes record);

    int insertSelective(Supernodes record);

    List<Supernodes> selectByExample(SupernodesExample example);

    Supernodes selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Supernodes record, @Param("example") SupernodesExample example);

    int updateByExample(@Param("record") Supernodes record, @Param("example") SupernodesExample example);

    int updateByPrimaryKeySelective(Supernodes record);

    int updateByPrimaryKey(Supernodes record);
    
    List<Supernodes> findAllSupernodes();
    
    void deleteAll();
	
	void insertList(List<Supernodes> supernodeList);
}