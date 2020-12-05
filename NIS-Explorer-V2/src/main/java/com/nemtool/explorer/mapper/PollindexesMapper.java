package com.nemtool.explorer.mapper;

import com.nemtool.explorer.pojo.Pollindexes;
import com.nemtool.explorer.pojo.PollindexesExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PollindexesMapper {
    int countByExample(PollindexesExample example);

    int deleteByExample(PollindexesExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Pollindexes record);

    int insertSelective(Pollindexes record);

    List<Pollindexes> selectByExampleWithBLOBs(PollindexesExample example);

    List<Pollindexes> selectByExample(PollindexesExample example);

    Pollindexes selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Pollindexes record, @Param("example") PollindexesExample example);

    int updateByExampleWithBLOBs(@Param("record") Pollindexes record, @Param("example") PollindexesExample example);

    int updateByExample(@Param("record") Pollindexes record, @Param("example") PollindexesExample example);

    int updateByPrimaryKeySelective(Pollindexes record);

    int updateByPrimaryKeyWithBLOBs(Pollindexes record);

    int updateByPrimaryKey(Pollindexes record);
}