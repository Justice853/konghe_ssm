package com.cxp.tmall.dao;

import com.cxp.tmall.database.OrderItemDo;
import com.cxp.tmall.database.OrderItemDoExample;
import java.util.List;

public interface OrderItemDoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderItemDo record);

    int insertSelective(OrderItemDo record);

    List<OrderItemDo> selectByExample(OrderItemDoExample example);

    OrderItemDo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderItemDo record);

    int updateByPrimaryKey(OrderItemDo record);
}