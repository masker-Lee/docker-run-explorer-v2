package com.nemtool.explorer.mapper;

import com.nemtool.explorer.pojo.Namespaces;
import com.nemtool.explorer.pojo.NamespacesExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NamespacesMapper {
    int countByExample(NamespacesExample example);

    int deleteByExample(NamespacesExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Namespaces record);

    int insertSelective(Namespaces record);

    List<Namespaces> selectByExample(NamespacesExample example);

    Namespaces selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Namespaces record, @Param("example") NamespacesExample example);

    int updateByExample(@Param("record") Namespaces record, @Param("example") NamespacesExample example);

    int updateByPrimaryKeySelective(Namespaces record);

    int updateByPrimaryKey(Namespaces record);
    
    void insertList(List<Namespaces> namespacesList);
    
    List<Namespaces> find(long no, int limit);
}