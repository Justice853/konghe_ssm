package com.cxp.tmall.service.impl;

import com.cxp.tmall.dao.ProductImageDoMapper;
import com.cxp.tmall.database.ProductDo;
import com.cxp.tmall.database.ProductImageDo;
import com.cxp.tmall.database.ProductImageDoExample;
import com.cxp.tmall.service.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductImageServiceImpl implements ProductImageService {

    @Autowired
    ProductImageDoMapper productImageDoMapper;
    @Override
    public void add (ProductImageDo pi) {
        productImageDoMapper.insert ( pi );
    }

    @Override
    public void delete (int id) {
        productImageDoMapper.deleteByPrimaryKey ( id );
    }

    @Override
    public void update (ProductImageDo pi) {
        productImageDoMapper.updateByPrimaryKeySelective ( pi );
    }

    @Override
    public ProductImageDo get (int id) {
        return productImageDoMapper.selectByPrimaryKey ( id );
    }

    @Override
    public List list (int pid, String type) {
        ProductImageDoExample productImageDoExample =new ProductImageDoExample ();
        productImageDoExample.createCriteria ().andPidEqualTo ( pid ).andTypeEqualTo ( type );
        productImageDoExample.setOrderByClause ( "id desc" );
        return productImageDoMapper.selectByExample ( productImageDoExample );
    }
}
