package com.cxp.tmall.service.impl;

import com.cxp.tmall.dao.ProductDoMapper;
import com.cxp.tmall.database.CategoryDo;
import com.cxp.tmall.database.ProductDo;
import com.cxp.tmall.database.ProductDoExample;
import com.cxp.tmall.database.ProductImageDo;
import com.cxp.tmall.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductDoMapper productDoMapper;
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductImageService productImageService;
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    ReviewService reviewService;

    @Override
    public void add (ProductDo p) {
        productDoMapper.insert ( p );
    }

    @Override
    public void delete (int id) {
        productDoMapper.deleteByPrimaryKey ( id );
    }

    @Override
    public void update (ProductDo p) {
        productDoMapper.updateByPrimaryKeySelective ( p );
    }

    @Override
    public ProductDo get (int id) {
        ProductDo p = productDoMapper.selectByPrimaryKey ( id );
        setCategory ( p );
        setFirstProductImage ( p );
        return p;
    }

    public void setCategory (List<ProductDo> ps) {
        for (ProductDo p : ps)
            setCategory ( p );
    }

    public void setCategory (ProductDo p) {
        int cid = p.getCid ();
        CategoryDo c = categoryService.get ( cid );
        p.setCategoryDo ( c );
    }

    @Override
    public List list (int cid) {
        ProductDoExample productDoExample = new ProductDoExample ();
        productDoExample.createCriteria ().andCidEqualTo ( cid );
        productDoExample.setOrderByClause ( "id desc" );
        List result = productDoMapper.selectByExample ( productDoExample );
        setCategory ( result );
        setFirstProductImage ( result );
        return result;
    }

    public void setFirstProductImage (ProductDo p) {
        List<ProductImageDo> pis = productImageService.list ( p.getId (), ProductImageService.type_single );
        if (!pis.isEmpty ()) {
            ProductImageDo pi = pis.get ( 0 );
            p.setFirstProductImage ( pi );
        }
    }

    @Override
    public void setSaleAndReviewNumber (ProductDo p) {
        int saleCount = orderItemService.getSaleCount(p.getId());
        p.setSaleCount(saleCount);

        int reviewCount = reviewService.getCount(p.getId());
        p.setReviewCount(reviewCount);
    }

    @Override
    public void setSaleAndReviewNumber (List<ProductDo> ps) {
            for (ProductDo p:ps){
                setSaleAndReviewNumber ( p );
            }
    }

    @Override
    public void fill (List<CategoryDo> categorys) {
        for(CategoryDo c : categorys){
            fill ( c );
        }
    }

    @Override
    public void fill (CategoryDo category) {
        List<ProductDo> ps =list ( category.getId () );
        category.setProducts ( ps );
    }

    @Override
    public void fillByRow (List<CategoryDo> categorys) {
        int productNumberEachRow=8;
        for(CategoryDo c :categorys){
            List<ProductDo> productDos = c.getProducts ();
            List<List<ProductDo>> productsByRow = new ArrayList<> (  );
            for(int i=0;i<productDos.size ();i+=productNumberEachRow){
                int size = i+productNumberEachRow;
                size=size>productDos.size ()?productDos.size ():size;
                List<ProductDo> productDoList = productDos.subList ( i,size );
                productsByRow.add ( productDoList );
            }
            c.setProductsByRow ( productsByRow );
        }
    }

    @Override
    public List<ProductDo> search (String keyword) {
        ProductDoExample productDoExample = new ProductDoExample ();
        productDoExample.createCriteria ().andNameLike ( "%"+keyword+"%" );
        productDoExample.setOrderByClause ( "id desc" );
        List result = productDoMapper.selectByExample ( productDoExample );
        setFirstProductImage ( result );
        setCategory ( result );
        return result;
    }

    public void setFirstProductImage  (List<ProductDo> ps) {
        for (ProductDo p : ps) {
            setFirstProductImage ( p );
        }
    }
}
