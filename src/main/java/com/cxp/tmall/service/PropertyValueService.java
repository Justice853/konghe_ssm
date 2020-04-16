package com.cxp.tmall.service;

import com.cxp.tmall.database.ProductDo;
import com.cxp.tmall.database.PropertyDo;
import com.cxp.tmall.database.PropertyValueDo;


import java.util.List;

public interface PropertyValueService {
    void init(ProductDo p);
    void update(PropertyValueDo pv);
    PropertyValueDo get(int ptid,int pid);
    List<PropertyValueDo> list(int pid);
}
