package com.cxp.tmall.service;

import com.cxp.tmall.database.ProductDo;
import com.cxp.tmall.database.ProductImageDo;

import java.util.List;

public interface ProductImageService {

    String type_single = "type_single";
    String type_detail = "type_detail";

    void add(ProductImageDo pi);
    void delete(int id);
    void update(ProductImageDo  pi);
    ProductImageDo get(int id);
    List list(int pid,String type);

}
