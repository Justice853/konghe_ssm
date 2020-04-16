package com.cxp.tmall.service;

import com.cxp.tmall.database.CategoryDo;

import java.util.List;

public interface CategoryService {
    List<CategoryDo> list();
    void add(CategoryDo categoryDo);
    void delete(int id);
    CategoryDo get(int id);
    void update(CategoryDo categoryDo);
}
