package com.myblog.version3.service.impl;

import com.myblog.version3.entity.Category;
import com.myblog.version3.mapper.categoryMapper;
import com.myblog.version3.service.categoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@Service
public class CategoryServiceImpl implements categoryService {

    @Autowired
    categoryMapper mapper;

    @Override
    public Category getByID(String ID) {
        return mapper.getByID(ID);
    }

    @Override
    public List<Category> getByUid(String Uid)  {
        return mapper.getByUid(Uid);
    }

    @Override
    public boolean add(Category category)throws SQLIntegrityConstraintViolationException {
        return mapper.Insert(category);
    }
}
