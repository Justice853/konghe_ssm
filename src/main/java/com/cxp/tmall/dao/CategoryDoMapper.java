package com.cxp.tmall.dao;

import com.cxp.tmall.database.CategoryDo;
import com.cxp.tmall.database.CategoryDoExample;
import java.util.List;

public interface CategoryDoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CategoryDo record);

    int insertSelective(CategoryDo record);

    List<CategoryDo> selectByExample(CategoryDoExample example);

    CategoryDo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CategoryDo record);

    int updateByPrimaryKey(CategoryDo record);

    List<CategoryDo> list();

    public int total();

    void add(CategoryDo categoryDo);
}