package org.example.service;

import lombok.AllArgsConstructor;
import org.example.dao.CategoryDao;
import org.example.entity.Category;

import java.sql.SQLException;
import java.util.List;

@AllArgsConstructor
public class CategoryService {
    private final CategoryDao categoryDao;


    public List<Category> findAll() throws SQLException {
        return categoryDao.findAll();
    }

    public Category findById(Long id) throws SQLException {
        return categoryDao.findById(id);
    }

    public void add(Category category) throws SQLException {
        if (category.getName() == null || category.getName().trim().isEmpty()) throw new IllegalArgumentException("name required");
        categoryDao.create(category);
    }

    public void update(Category category) throws SQLException {
        if (category.getId() == null || category.getId() <= 0) {
            throw new IllegalArgumentException("Invalid category id");
        }
        if (category == null || category.getName() == null || category.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Category name required");
        }

        categoryDao.update(category);
    }

    public void delete(Long id) throws SQLException {
        if (id == null) throw new IllegalArgumentException("id required");
        categoryDao.delete(id);
    }

    public List<Category> search(String keyword) throws SQLException {
        return categoryDao.search(keyword);
    }
}