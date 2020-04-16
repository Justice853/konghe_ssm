package com.cxp.tmall.service;

import com.cxp.tmall.database.ReviewDo;

import java.util.List;

public interface ReviewService {
    void add(ReviewDo c);

    void delete(int id);
    void update(ReviewDo c);
    ReviewDo get(int id);
    List list(int pid);

    int getCount(int pid);
}
