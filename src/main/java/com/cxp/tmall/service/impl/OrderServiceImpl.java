package com.cxp.tmall.service.impl;

import com.cxp.tmall.dao.OrderDoMapper;
import com.cxp.tmall.database.OrderDo;
import com.cxp.tmall.database.OrderDoExample;
import com.cxp.tmall.database.OrderItemDo;
import com.cxp.tmall.database.UserDo;
import com.cxp.tmall.service.OrderItemService;
import com.cxp.tmall.service.OrderService;
import com.cxp.tmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderDoMapper orderDoMapper;
    @Autowired
    UserService userService;
    @Autowired
    OrderItemService orderItemService;

    @Override
    public List list (int uid, String excludedStatus) {
        OrderDoExample orderDoExample = new OrderDoExample ();
        orderDoExample.createCriteria ().andUidEqualTo ( uid ).andStatusNotEqualTo (excludedStatus  );
        orderDoExample.setOrderByClause ( "id desc" );
        return orderDoMapper.selectByExample ( orderDoExample );
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,rollbackForClassName="Exception")

    public float add (OrderDo c, List<OrderItemDo> ois) {
        float total =0;
        add ( c );
//用来模拟当增加订单后出现异常，观察事务管理是否预期发生。（需要把false修改为true才能观察到）
        if(false)
            throw new RuntimeException (  );

        for(OrderItemDo orderItemDo:ois){
            orderItemDo.setOid ( c.getId () );
            orderItemService.update ( orderItemDo );
            total+=orderItemDo.getProductDo ().getPromotePrice ()*orderItemDo.getNumber ();
        }
        return total;
    }

    @Override
    public void add (OrderDo o) {
        orderDoMapper.insert ( o );
    }

    @Override
    public void delete (int id) {
        orderDoMapper.deleteByPrimaryKey ( id );
    }

    @Override
    public void update (OrderDo o) {
        orderDoMapper.updateByPrimaryKeySelective ( o );
    }

    @Override
    public OrderDo get (int id) {
        return orderDoMapper.selectByPrimaryKey ( id );
    }

    @Override
    public List<OrderDo> list ( ) {
        OrderDoExample orderDoExample = new OrderDoExample ();
        orderDoExample.setOrderByClause ( "id desc" );
        List<OrderDo> result = orderDoMapper.selectByExample ( orderDoExample );
        setUser(result);
        return result;
    }
    public void setUser(List<OrderDo> os ){
        for(OrderDo o : os){
            setUser ( o );
        }

    }
    private void setUser(OrderDo o){
        int uid = o.getUid ();
        UserDo u = userService.get ( uid );
        o.setUserDo ( u );
    }
}
