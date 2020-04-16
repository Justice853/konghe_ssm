package com.cxp.tmall.dao;

import com.cxp.tmall.database.ReviewDo;
import com.cxp.tmall.database.ReviewDoExample;
import java.util.List;

public interface ReviewDoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ReviewDo record);

    int insertSelective(ReviewDo record);

    List<ReviewDo> selectByExample(ReviewDoExample example);

    ReviewDo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ReviewDo record);

    int updateByPrimaryKey(ReviewDo record);
}