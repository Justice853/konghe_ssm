package com.cxp.tmall.service;

import com.cxp.tmall.database.OrderDo;
import com.cxp.tmall.database.OrderItemDo;
import org.springframework.core.annotation.Order;

import java.util.List;

public interface OrderService {

    String waitPay = "waitPay";
    String waitDelivery = "waitDelivery";
    String waitConfirm = "waitConfirm";
    String waitReview = "waitReview";
    String finish = "finish";
    String delete = "delete";

    List list(int uid, String excludedStatus);
    float add(OrderDo c, List<OrderItemDo>ois);
    void add(OrderDo o);
    void delete(int id);
    void update(OrderDo o);
    OrderDo get(int id);
    List<OrderDo> list();

}
