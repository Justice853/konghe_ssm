package com.cxp.tmall.service.impl;

import com.cxp.tmall.dao.ReviewDoMapper;
import com.cxp.tmall.database.ReviewDo;
import com.cxp.tmall.database.ReviewDoExample;
import com.cxp.tmall.database.UserDo;
import com.cxp.tmall.service.ReviewService;
import com.cxp.tmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    ReviewDoMapper reviewDoMapper;
    @Autowired
    UserService userService;
    @Override
    public void add (ReviewDo c) {
        reviewDoMapper.insert ( c );
    }

    @Override
    public void delete (int id) {
        reviewDoMapper.deleteByPrimaryKey ( id );
    }

    @Override
    public void update (ReviewDo c) {
        reviewDoMapper.updateByPrimaryKeySelective ( c );
    }

    @Override
    public ReviewDo get (int id) {
        return reviewDoMapper.selectByPrimaryKey ( id );
    }

    @Override
    public List list (int pid) {
        ReviewDoExample reviewDoExample = new ReviewDoExample ();
        reviewDoExample.createCriteria ().andPidEqualTo ( pid );
        reviewDoExample.setOrderByClause ( "id desc" );

        List<ReviewDo> reviewDos = reviewDoMapper.selectByExample ( reviewDoExample );
        setUser(reviewDos);
        return reviewDos;
    }

    @Override
    public int getCount (int pid) {
        return list ( pid ).size ();
    }
    public void setUser(List<ReviewDo> reviewDos){
        for(ReviewDo reviewDo :reviewDos){
            setUser ( reviewDo );
        }
    }
    public void setUser(ReviewDo reviewDo){
        int uid = reviewDo.getUid ();
        UserDo userDo = userService.get ( uid );
        reviewDo.setUserDo ( userDo );
    }
}
