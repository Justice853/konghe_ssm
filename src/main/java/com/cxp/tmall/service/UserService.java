package com.cxp.tmall.service;

import com.cxp.tmall.database.ProductDo;
import com.cxp.tmall.database.UserDo;

import java.util.List;

public interface UserService {
    void add(UserDo u);
    void delete(int id);
    void update(UserDo u);
    UserDo get(int id);
    List list();
    boolean isExist(String name);
    UserDo get(String name,String password);
}
