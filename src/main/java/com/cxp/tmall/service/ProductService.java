package com.cxp.tmall.service;

import com.cxp.tmall.database.CategoryDo;
import com.cxp.tmall.database.ProductDo;
import com.cxp.tmall.database.ProductImageDo;

import java.util.List;

public interface ProductService {
    void add(ProductDo p);
    void delete(int id);
    void update(ProductDo p);
    ProductDo get(int id);
    List list(int cid);
    void setFirstProductImage(ProductDo p);
//增加为产品设置销量和评价数量的方法：
    void setSaleAndReviewNumber(ProductDo p);
    void setSaleAndReviewNumber(List<ProductDo> ps);

    public void fill(List<CategoryDo> categorys);
    public void fill(CategoryDo category);
    public void fillByRow(List<CategoryDo> categorys);

    List<ProductDo>search(String keyword);
}
