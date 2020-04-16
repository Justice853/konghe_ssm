package com.cxp.tmall.service;

import com.cxp.tmall.database.OrderDo;
import com.cxp.tmall.database.OrderItemDo;

import java.util.List;

public interface OrderItemService
{
    void add(OrderItemDo oi);
    void delete(int id);
    void update(OrderItemDo oi);
    OrderItemDo get(int id);
    List <OrderItemDo>list();
    void fill(OrderDo orderDo);
    void fill(List<OrderDo> orderDos);
    int getSaleCount(int pid);
    List<OrderItemDo> listByUser(int uid);
}
