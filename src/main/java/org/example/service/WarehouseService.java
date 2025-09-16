package org.example.service;

import lombok.AllArgsConstructor;
import org.example.dao.WarehouseDao;
import org.example.entity.Category;
import org.example.entity.Storekeeper;
import org.example.entity.Warehouse;

import java.sql.SQLException;
import java.util.List;

@AllArgsConstructor
public class WarehouseService {
    private final WarehouseDao warehouseDao;


    public List<Warehouse> findAll() throws SQLException {
        return warehouseDao.findAll();
    }

    public Warehouse findById(Long id) throws SQLException {
        return warehouseDao.findById(id);
    }

    public void add(Warehouse warehouse) throws SQLException {
        if (warehouse.getName() == null || warehouse.getName().trim().isEmpty()) throw new IllegalArgumentException("name required");
        if (warehouse.getAddress() == null || warehouse.getAddress().trim().isEmpty()) throw new IllegalArgumentException("address required");
        warehouseDao.create(warehouse);
    }

    public void update(Warehouse warehouse) throws SQLException {
        if (warehouse.getId() == null || warehouse.getId() <= 0) {
            throw new IllegalArgumentException("Invalid id");
        }
        if (warehouse.getName() == null || warehouse.getName().trim().isEmpty()) throw new IllegalArgumentException("name required");
        if (warehouse.getAddress() == null || warehouse.getAddress().trim().isEmpty()) throw new IllegalArgumentException("address required");

        warehouseDao.update(warehouse);
    }

    public void delete(Long id) throws SQLException {
        if (id == null) throw new IllegalArgumentException("id required");
        warehouseDao.delete(id);
    }

    public List<Warehouse> search(String keyword) throws SQLException {
        return warehouseDao.search(keyword);
    }
}
