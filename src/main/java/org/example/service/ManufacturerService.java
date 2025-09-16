package org.example.service;

import lombok.AllArgsConstructor;
import org.example.dao.ManufacturerDao;
import org.example.entity.Category;
import org.example.entity.Manufacturer;

import java.sql.SQLException;
import java.util.List;


@AllArgsConstructor
public class ManufacturerService {
    private final ManufacturerDao manufacturerDao;


    public List<Manufacturer> findAll() throws SQLException {
        return manufacturerDao.findAll();
    }

    public Manufacturer findById(Long id) throws SQLException {
        return manufacturerDao.findById(id);
    }

    public void add(Manufacturer manufacturer) throws SQLException {
        if (manufacturer.getName() == null || manufacturer.getName().trim().isEmpty()) throw new IllegalArgumentException("name required");
        if (manufacturer.getCountry() == null || manufacturer.getCountry().trim().isEmpty()) throw new IllegalArgumentException("country required");
        if (manufacturer.getEmail() == null || manufacturer.getEmail().trim().isEmpty()) throw new IllegalArgumentException("email required");
        manufacturerDao.create(manufacturer);
    }

    public void update(Manufacturer manufacturer) throws SQLException {
        if (manufacturer.getId() == null || manufacturer.getId() <= 0) {
            throw new IllegalArgumentException("Invalid id");
        }
        if (manufacturer.getName() == null || manufacturer.getName().trim().isEmpty()) throw new IllegalArgumentException("name required");
        if (manufacturer.getCountry() == null || manufacturer.getCountry().trim().isEmpty()) throw new IllegalArgumentException("country required");
        if (manufacturer.getEmail() == null || manufacturer.getEmail().trim().isEmpty()) throw new IllegalArgumentException("email required");

        manufacturerDao.update(manufacturer);
    }

    public void delete(Long id) throws SQLException {
        if (id == null) throw new IllegalArgumentException("id required");
        manufacturerDao.delete(id);
    }

    public List<Manufacturer> search(String keyword) throws SQLException {
        // Можно искать по name или address
        return manufacturerDao.search(keyword);
    }
}
