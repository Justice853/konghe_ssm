package com.cxp.tmall.database;

import java.io.NotSerializableException;
import java.io.Serializable;
import java.util.List;

public class CategoryDo  {
    private Integer id;

    private String name;

    List<ProductDo> products;
    List<List<ProductDo>> productsByRow;

    public List<ProductDo> getProducts ( ) {
        return products;
    }

    public void setProducts (List<ProductDo> products) {
        this.products = products;
    }

    public List<List<ProductDo>> getProductsByRow ( ) {
        return productsByRow;
    }

    public void setProductsByRow (List<List<ProductDo>> productsByRow) {
        this.productsByRow = productsByRow;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}