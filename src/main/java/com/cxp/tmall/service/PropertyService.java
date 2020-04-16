package com.cxp.tmall.service;

import com.cxp.tmall.database.PropertyDo;

import java.util.List;

public interface PropertyService {
    void add(PropertyDo p);
    void delete(int id);
    void update(PropertyDo p);
    PropertyDo get(int id);
    List list(int cid);
}
