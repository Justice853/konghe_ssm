package com.cxp.tmall.comparator;

import com.cxp.tmall.database.ProductDo;

import java.util.Comparator;
//把 销量x评价 高的放前面
public class ProductAllComparator implements Comparator<ProductDo> {
    @Override
    public int compare (ProductDo o1, ProductDo o2) {
        return o2.getReviewCount ()*o2.getSaleCount ()-o1.getReviewCount ()*o1.getSaleCount ();
    }
}
