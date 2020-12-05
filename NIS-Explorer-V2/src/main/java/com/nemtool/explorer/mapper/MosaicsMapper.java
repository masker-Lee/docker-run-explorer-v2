package com.nemtool.explorer.mapper;

import com.nemtool.explorer.pojo.Mosaics;
import com.nemtool.explorer.pojo.MosaicsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MosaicsMapper {
    int countByExample(MosaicsExample example);

    int deleteByExample(MosaicsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Mosaics record);

    int insertSelective(Mosaics record);

    List<Mosaics> selectByExampleWithBLOBs(MosaicsExample example);

    List<Mosaics> selectByExample(MosaicsExample example);

    Mosaics selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Mosaics record, @Param("example") MosaicsExample example);

    int updateByExampleWithBLOBs(@Param("record") Mosaics record, @Param("example") MosaicsExample example);

    int updateByExample(@Param("record") Mosaics record, @Param("example") MosaicsExample example);

    int updateByPrimaryKeySelective(Mosaics record);

    int updateByPrimaryKeyWithBLOBs(Mosaics record);

    int updateByPrimaryKey(Mosaics record);
    
    void insertList(List<Mosaics> mosaicsList);
    
    List<String> findAllMosaicId();
}