package com.myblog.version3.service;

import com.myblog.version3.entity.Category;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

public interface categoryService {

    Category getByID(String ID);

    List<Category> getByUid(String Uid);

    boolean add(Category category) throws SQLIntegrityConstraintViolationException;
}
