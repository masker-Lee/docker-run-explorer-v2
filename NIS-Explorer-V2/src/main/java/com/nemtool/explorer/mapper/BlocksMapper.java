package com.nemtool.explorer.mapper;

import com.nemtool.explorer.pojo.Blocks;
import com.nemtool.explorer.pojo.BlocksExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: blocksMapper
 * @author Masker
 * @date 2020.06.28
 */

public interface BlocksMapper {
    int countByExample(BlocksExample example);

    int deleteByExample(BlocksExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Blocks record);

    int insertSelective(Blocks record);

    List<Blocks> selectByExample(BlocksExample example);

    Blocks selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Blocks record, @Param("example") BlocksExample example);

    int updateByExample(@Param("record") Blocks record, @Param("example") BlocksExample example);

    int updateByPrimaryKeySelective(Blocks record);

    int updateByPrimaryKey(Blocks record);
    
    List<Blocks> latest10Blocks();
    
    int insertList(List<Blocks> blocksList);
    
    Blocks maxBlock();
    
    List<Blocks> minBlock();
    
    
}