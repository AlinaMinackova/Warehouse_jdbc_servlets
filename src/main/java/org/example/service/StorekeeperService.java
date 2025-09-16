package org.example.service;

import lombok.AllArgsConstructor;
import org.example.dao.StorekeeperDao;
import org.example.entity.Manufacturer;
import org.example.entity.Storekeeper;

import java.sql.SQLException;
import java.util.List;

@AllArgsConstructor
public class StorekeeperService {
    private final StorekeeperDao storekeeperDao;


    public List<Storekeeper> findAll() throws SQLException {
        return storekeeperDao.findAll();
    }

    public Storekeeper findById(Long id) throws SQLException {
        return storekeeperDao.findById(id);
    }

    public void add(Storekeeper storekeeper) throws SQLException {
        if (storekeeper.getFirstName() == null || storekeeper.getFirstName().trim().isEmpty()) throw new IllegalArgumentException("first_name required");
        if (storekeeper.getLastName() == null || storekeeper.getLastName().trim().isEmpty()) throw new IllegalArgumentException("last_name required");
        if (storekeeper.getEmail() == null || storekeeper.getEmail().trim().isEmpty()) throw new IllegalArgumentException("email required");
        storekeeperDao.create(storekeeper);
    }

    public void update(Storekeeper storekeeper) throws SQLException {
        if (storekeeper.getId() == null || storekeeper.getId() <= 0) {
            throw new IllegalArgumentException("Invalid id");
        }
        if (storekeeper.getFirstName() == null || storekeeper.getFirstName().trim().isEmpty()) throw new IllegalArgumentException("first_name required");
        if (storekeeper.getLastName() == null || storekeeper.getLastName().trim().isEmpty()) throw new IllegalArgumentException("last_name required");
        if (storekeeper.getEmail() == null || storekeeper.getEmail().trim().isEmpty()) throw new IllegalArgumentException("email required");

        storekeeperDao.update(storekeeper);
    }

    public void delete(Long id) throws SQLException {
        if (id == null) throw new IllegalArgumentException("id required");
        storekeeperDao.delete(id);
    }

    public List<Storekeeper> search(String keyword) throws SQLException {
        return storekeeperDao.search(keyword);
    }
}

