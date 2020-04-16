package com.cxp.tmall.dao;

import com.cxp.tmall.database.OrderDo;
import com.cxp.tmall.database.OrderDoExample;
import java.util.List;

public interface OrderDoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderDo record);

    int insertSelective(OrderDo record);

    List<OrderDo> selectByExample(OrderDoExample example);

    OrderDo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderDo record);

    int updateByPrimaryKey(OrderDo record);
}