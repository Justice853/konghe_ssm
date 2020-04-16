package com.cxp.tmall.comparator;

import com.cxp.tmall.database.ProductDo;

import java.util.Comparator;

//评价比较器
public class ProductReviewComparator implements Comparator<ProductDo> {
    @Override
    public int compare (ProductDo o1, ProductDo o2) {
        return o2.getReviewCount ()-o1.getReviewCount ();
    }
}
