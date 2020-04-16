package com.cxp.tmall.comparator;

import com.cxp.tmall.database.ProductDo;

import java.util.Comparator;

public class ProductSaleCountComparator implements Comparator<ProductDo> {
    @Override
    public int compare (ProductDo o1, ProductDo o2) {
        return o2.getSaleCount()-o1.getSaleCount();
    }
}
