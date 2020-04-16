package com.cxp.tmall.comparator;

import com.cxp.tmall.database.ProductDo;

import java.util.Comparator;
//把 创建日期晚的放前面,新品比较器
public class ProductDateComparator implements Comparator<ProductDo> {
    @Override
    public int compare (ProductDo o1, ProductDo o2) {
        return o2.getCreateDate ().compareTo ( o1.getCreateDate () );
    }
}
