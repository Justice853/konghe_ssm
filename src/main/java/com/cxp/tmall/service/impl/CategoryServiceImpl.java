package com.cxp.tmall.service.impl;

import com.cxp.tmall.dao.CategoryDoMapper;
import com.cxp.tmall.database.CategoryDo;
import com.cxp.tmall.database.CategoryDoExample;
import com.cxp.tmall.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryDoMapper categoryDoMapper;


    @Override
    public List<CategoryDo> list ( ) {
//        return categoryDoMapper.list (  );
        CategoryDoExample example =new CategoryDoExample();
        example.setOrderByClause("id desc");
        return categoryDoMapper.selectByExample(example);
    }

    @Override
    public void add (CategoryDo categoryDo) {
        categoryDoMapper.add ( categoryDo );
    }

    @Override
    public void delete (int id) {
        categoryDoMapper.deleteByPrimaryKey ( id );
    }

    @Override
    public CategoryDo get (int id) {
        return categoryDoMapper.selectByPrimaryKey ( id );
    }

    @Override
    public void update (CategoryDo categoryDo) {
        categoryDoMapper.updateByPrimaryKey ( categoryDo );
    }
}
