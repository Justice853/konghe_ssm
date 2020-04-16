package com.cxp.tmall.controller;

import com.cxp.tmall.database.OrderDo;
import com.cxp.tmall.service.OrderItemService;
import com.cxp.tmall.service.OrderService;
import com.cxp.tmall.util.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
public class OrderController {
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    OrderService orderService;

    @RequestMapping("admin_order_list")
    public String list(Model model, Page page){
        PageHelper.offsetPage ( page.getStart (),page.getCount () );
        List<OrderDo> orderDos = orderService.list ();
        int total = (int) new PageInfo<> ( orderDos ).getTotal ();
        page.setTotal ( total );
        orderItemService.fill ( orderDos );
        model.addAttribute ( "os",orderDos );
        model.addAttribute ( "page",page );
        return "admin/listOrder";
    }
    @RequestMapping("admin_order_delivery")
    public String delivery(OrderDo orderDo) throws IOException {
        orderDo.setDeliveryDate (new Date (  ) );
        orderDo.setStatus (OrderService.waitConfirm);
        orderService.update ( orderDo );
        return ("redirect:admin_order_list");
    }

}
