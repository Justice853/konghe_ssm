package com.cxp.tmall.dao;

import com.cxp.tmall.database.ProductImageDo;
import com.cxp.tmall.database.ProductImageDoExample;
import java.util.List;

public interface ProductImageDoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProductImageDo record);

    int insertSelective(ProductImageDo record);

    List<ProductImageDo> selectByExample(ProductImageDoExample example);

    ProductImageDo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProductImageDo record);

    int updateByPrimaryKey(ProductImageDo record);
}