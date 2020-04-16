package com.cxp.tmall.dao;

import com.cxp.tmall.database.ProductDo;
import com.cxp.tmall.database.ProductDoExample;
import java.util.List;

public interface ProductDoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProductDo record);

    int insertSelective(ProductDo record);

    List<ProductDo> selectByExample(ProductDoExample example);

    ProductDo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProductDo record);

    int updateByPrimaryKey(ProductDo record);
}