package com.cxp.tmall.dao;

import com.cxp.tmall.database.PropertyDo;
import com.cxp.tmall.database.PropertyDoExample;
import java.util.List;

public interface PropertyDoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PropertyDo record);

    int insertSelective(PropertyDo record);

    List<PropertyDo> selectByExample(PropertyDoExample example);

    PropertyDo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PropertyDo record);

    int updateByPrimaryKey(PropertyDo record);
}