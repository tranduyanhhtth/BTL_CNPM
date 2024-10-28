package com.inn.coffee.dao;

import com.inn.coffee.POJO.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryDao extends JpaRepository<Category, Integer> {
    List<Category> getAllCategory();
}
