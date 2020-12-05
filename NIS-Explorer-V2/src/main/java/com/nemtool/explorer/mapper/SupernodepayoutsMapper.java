package com.nemtool.explorer.mapper;

import com.nemtool.explorer.pojo.Supernodepayouts;
import com.nemtool.explorer.pojo.SupernodepayoutsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SupernodepayoutsMapper {
    int countByExample(SupernodepayoutsExample example);

    int deleteByExample(SupernodepayoutsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Supernodepayouts record);

    int insertSelective(Supernodepayouts record);

    List<Supernodepayouts> selectByExample(SupernodepayoutsExample example);

    Supernodepayouts selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Supernodepayouts record, @Param("example") SupernodepayoutsExample example);

    int updateByExample(@Param("record") Supernodepayouts record, @Param("example") SupernodepayoutsExample example);

    int updateByPrimaryKeySelective(Supernodepayouts record);

    int updateByPrimaryKey(Supernodepayouts record);
    
}