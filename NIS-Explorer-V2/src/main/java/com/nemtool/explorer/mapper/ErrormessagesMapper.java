package com.nemtool.explorer.mapper;

import com.nemtool.explorer.pojo.Errormessages;
import com.nemtool.explorer.pojo.ErrormessagesExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ErrormessagesMapper {
    int countByExample(ErrormessagesExample example);

    int deleteByExample(ErrormessagesExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Errormessages record);

    int insertSelective(Errormessages record);

    List<Errormessages> selectByExampleWithBLOBs(ErrormessagesExample example);

    List<Errormessages> selectByExample(ErrormessagesExample example);

    Errormessages selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Errormessages record, @Param("example") ErrormessagesExample example);

    int updateByExampleWithBLOBs(@Param("record") Errormessages record, @Param("example") ErrormessagesExample example);

    int updateByExample(@Param("record") Errormessages record, @Param("example") ErrormessagesExample example);

    int updateByPrimaryKeySelective(Errormessages record);

    int updateByPrimaryKeyWithBLOBs(Errormessages record);
}