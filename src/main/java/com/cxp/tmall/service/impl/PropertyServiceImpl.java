package com.cxp.tmall.service.impl;

import com.cxp.tmall.dao.PropertyDoMapper;
import com.cxp.tmall.database.PropertyDo;
import com.cxp.tmall.database.PropertyDoExample;
import com.cxp.tmall.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyServiceImpl implements PropertyService {

    @Autowired
    PropertyDoMapper propertyDoMapper;
    @Override
    public void add (PropertyDo p) {
        propertyDoMapper.insert ( p );
    }

    @Override
    public void delete (int id) {
        propertyDoMapper.deleteByPrimaryKey ( id );
    }

    @Override
    public void update (PropertyDo p) {
        propertyDoMapper.updateByPrimaryKeySelective ( p );
    }

    @Override
    public PropertyDo get (int id) {
        return propertyDoMapper.selectByPrimaryKey ( id );
    }

    @Override
    public List list (int cid) {
        PropertyDoExample example =new PropertyDoExample ();
        example.createCriteria().andCidEqualTo(cid);
        example.setOrderByClause("id desc");
        return propertyDoMapper.selectByExample(example);
    }
}
