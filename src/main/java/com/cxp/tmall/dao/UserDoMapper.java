package com.cxp.tmall.dao;

import com.cxp.tmall.database.UserDo;
import com.cxp.tmall.database.UserDoExample;
import java.util.List;

public interface UserDoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserDo record);

    int insertSelective(UserDo record);

    List<UserDo> selectByExample(UserDoExample example);

    UserDo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserDo record);

    int updateByPrimaryKey(UserDo record);
}