package com.cxp.tmall.dao;

import com.cxp.tmall.database.PropertyValueDo;
import com.cxp.tmall.database.PropertyValueDoExample;
import java.util.List;

public interface PropertyValueDoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PropertyValueDo record);

    int insertSelective(PropertyValueDo record);

    List<PropertyValueDo> selectByExample(PropertyValueDoExample example);

    PropertyValueDo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PropertyValueDo record);

    int updateByPrimaryKey(PropertyValueDo record);
}