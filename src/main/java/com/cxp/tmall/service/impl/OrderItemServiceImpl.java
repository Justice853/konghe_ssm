package com.cxp.tmall.service.impl;

import com.cxp.tmall.dao.OrderItemDoMapper;
import com.cxp.tmall.database.OrderDo;
import com.cxp.tmall.database.OrderItemDo;
import com.cxp.tmall.database.OrderItemDoExample;
import com.cxp.tmall.database.ProductDo;
import com.cxp.tmall.service.OrderItemService;
import com.cxp.tmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {
    @Autowired
    OrderItemDoMapper orderItemDoMapper;
    @Autowired
    ProductService productService;
    @Override
    public void add (OrderItemDo oi) {
        orderItemDoMapper.insert ( oi );
    }

    @Override
    public void delete (int id) {
        orderItemDoMapper.deleteByPrimaryKey ( id );
    }

    @Override
    public void update (OrderItemDo oi) {
        orderItemDoMapper.updateByPrimaryKeySelective ( oi );
    }

    @Override
    public OrderItemDo get (int id) {
        OrderItemDo orderItemDo = orderItemDoMapper.selectByPrimaryKey ( id );
        setProduct(orderItemDo);
        return orderItemDo;
    }

    @Override
    public List<OrderItemDo> list ( ) {
        OrderItemDoExample example = new OrderItemDoExample ();
        example.setOrderByClause ( "id desc" );
        return orderItemDoMapper.selectByExample ( example );
    }

    @Override
    public void fill (OrderDo orderDo) {
        OrderItemDoExample example = new OrderItemDoExample ();
        example.createCriteria ().andOidEqualTo ( orderDo.getId () );
        example.setOrderByClause ( "id desc" );
        List<OrderItemDo> ois = orderItemDoMapper.selectByExample ( example );
        setProduct(ois);

        float total = 0;
        int totalNumber=0;
        for(OrderItemDo oi :ois){
            total+=oi.getNumber ()*oi.getProductDo ().getPromotePrice ();
            totalNumber+=oi.getNumber ();
        }
        orderDo.setTotal (total);
        orderDo.setTotalNumber ( totalNumber );
        orderDo.setOrderItemDos ( ois );
    }

    @Override
    public void fill (List<OrderDo> orderDos) {
        for(OrderDo o:orderDos){
            fill ( o );
        }
    }

    @Override
    public int getSaleCount (int pid) {
        OrderItemDoExample example = new OrderItemDoExample ();
        example.createCriteria ().andPidEqualTo ( pid );
        List<OrderItemDo >orderItemDos=orderItemDoMapper.selectByExample ( example );
        int result=0;
        for(OrderItemDo o : orderItemDos){
            result+=o.getNumber ();
        }
        return result;
    }

    @Override
    public List<OrderItemDo> listByUser (int uid) {
        OrderItemDoExample orderItemDoExample = new OrderItemDoExample ();
        orderItemDoExample.createCriteria ().andUidEqualTo ( uid ).andOidIsNull ();
        List<OrderItemDo> orderItemDos = orderItemDoMapper.selectByExample ( orderItemDoExample );
        setProduct ( orderItemDos );
        return orderItemDos;
    }

    public void setProduct(List<OrderItemDo> orderItemDos){
        for(OrderItemDo oi :orderItemDos){
            setProduct ( oi );
        }
    }
    private void setProduct(OrderItemDo oi){
        ProductDo p = productService.get ( oi.getPid () );
        oi.setProductDo ( p );
    }
}
