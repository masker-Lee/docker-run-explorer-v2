package com.nemtool.explorer.mapper;

import com.nemtool.explorer.pojo.Poll;
import com.nemtool.explorer.pojo.PollExample;
import com.nemtool.explorer.pojo.PollWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PollMapper {
    int countByExample(PollExample example);

    int deleteByExample(PollExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PollWithBLOBs record);

    int insertSelective(PollWithBLOBs record);

    List<PollWithBLOBs> selectByExampleWithBLOBs(PollExample example);

    List<Poll> selectByExample(PollExample example);

    PollWithBLOBs selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PollWithBLOBs record, @Param("example") PollExample example);

    int updateByExampleWithBLOBs(@Param("record") PollWithBLOBs record, @Param("example") PollExample example);

    int updateByExample(@Param("record") Poll record, @Param("example") PollExample example);

    int updateByPrimaryKeySelective(PollWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(PollWithBLOBs record);

    int updateByPrimaryKey(Poll record);
}