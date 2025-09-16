package org.example.service;

import jakarta.servlet.http.Part;
import lombok.AllArgsConstructor;
import org.example.dao.ProductDao;
import org.example.entity.Category;
import org.example.entity.Product;
import org.example.entity.Stock;

import java.sql.SQLException;
import java.util.List;

@AllArgsConstructor
public class ProductService {
    private final ProductDao productDao;
    private final ManufacturerService manufacturerService;
    private final CategoryService categoryService;



    public List<Product> findAll() throws SQLException {
        List<Product> products = productDao.findAll();

        for (Product p : products) {
            p.setManufacturer(manufacturerService.findById(p.getManufacturerId()));
            p.setCategory(categoryService.findById(p.getCategoryId()));
        }

        return products;
    }

    public Product findById(Long id) throws SQLException {
        Product product = productDao.findById(id);
        product.setManufacturer(manufacturerService.findById(product.getManufacturerId()));
        product.setCategory(categoryService.findById(product.getCategoryId()));
        return product;
    }

    public void add(Product product) throws SQLException {
        if (product.getName() == null || product.getName().trim().isEmpty()) throw new IllegalArgumentException("name required");
        if (product.getManufacturer().getId() == null || product.getManufacturer().getId() <= 0) {
            throw new IllegalArgumentException("Invalid manufacturer id");
        }
        if (product.getCategory().getId() == null || product.getCategory().getId() <= 0) {
            throw new IllegalArgumentException("Invalid category id");
        }
        productDao.create(product);
    }

    public void update(Product product, String fileName) throws SQLException {
        if (product.getId() == null || product.getId() <= 0) {
            throw new IllegalArgumentException("Invalid id");
        }
        if (product.getName() == null || product.getName().trim().isEmpty()) throw new IllegalArgumentException("name required");
        if (product.getManufacturer().getId() == null || product.getManufacturer().getId() <= 0) {
            throw new IllegalArgumentException("Invalid manufacturer id");
        }
        if (product.getCategory().getId() == null || product.getCategory().getId() <= 0) {
            throw new IllegalArgumentException("Invalid category id");
        }

        if (fileName == null || fileName.trim().isEmpty()){
            Product productExists = productDao.findById(product.getId());
            product.setImageUrl(productExists.getName());
        }

        productDao.update(product);
    }

    public void delete(Long id) throws SQLException {
        if (id == null) throw new IllegalArgumentException("id required");
        productDao.delete(id);
    }

    public List<Product> findFiltered(Long manufacturerId, Long categoryId) throws SQLException {
        List<Product> products = productDao.findFiltered(manufacturerId, categoryId);

        for (Product p : products) {
            p.setManufacturer(manufacturerService.findById(p.getManufacturerId()));
            p.setCategory(categoryService.findById(p.getCategoryId()));
        }

        return products;
    }
}