package com.cxp.tmall.comparator;

import com.cxp.tmall.database.ProductDo;

import java.util.Comparator;

public class ProductPriceComparator implements Comparator<ProductDo> {
    @Override
    public int compare (ProductDo o1, ProductDo o2) {
        return (int)(o1.getPromotePrice ()-o2.getPromotePrice ());
    }
}
