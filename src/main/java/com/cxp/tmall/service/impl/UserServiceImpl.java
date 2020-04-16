package com.cxp.tmall.service.impl;

import com.cxp.tmall.dao.UserDoMapper;
import com.cxp.tmall.database.UserDo;
import com.cxp.tmall.database.UserDoExample;
import com.cxp.tmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDoMapper userDoMapper;
    @Override
    public void add (UserDo u) {
        userDoMapper.insert ( u );
    }

    @Override
    public void delete (int id) {
        userDoMapper.deleteByPrimaryKey ( id );
    }

    @Override
    public void update (UserDo u) {
        userDoMapper.updateByPrimaryKeySelective ( u );
    }

    @Override
    public UserDo get (int id) {
        return userDoMapper.selectByPrimaryKey ( id );
    }

    @Override
    public List list ( ) {
        UserDoExample userDoExample=new UserDoExample ();
        userDoExample.setOrderByClause ( "id desc" );
        return userDoMapper.selectByExample ( userDoExample );
    }

    @Override
    public boolean isExist (String name) {
        UserDoExample example =new UserDoExample ();
        example.createCriteria().andNameEqualTo(name);
        List<UserDo> result= userDoMapper.selectByExample(example);
        if(!result.isEmpty())
            return true;
        return false;
    }

    @Override
    public UserDo get (String name, String password) {
        UserDoExample example = new UserDoExample ();
        example.createCriteria ().andNameEqualTo ( name ).andPasswordEqualTo ( password );
        List<UserDo> result =userDoMapper.selectByExample ( example );
        if(result.isEmpty ())
            return null;
        return result.get ( 0 );
    }
}
