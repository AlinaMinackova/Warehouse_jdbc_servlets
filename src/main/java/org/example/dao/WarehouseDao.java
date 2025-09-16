package org.example.dao;

import lombok.AllArgsConstructor;
import org.example.entity.Category;
import org.example.entity.Warehouse;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class WarehouseDao {
    private final DataSource pool;


    public List<Warehouse> findAll() throws SQLException {
        String sql = "SELECT id, name, address FROM warehouse ORDER BY id";
        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<Warehouse> list = new ArrayList<>();
            while (rs.next()) {
                list.add(new Warehouse(rs.getLong("id"), rs.getString("name"), rs.getString("address")));
            }
            return list;
        }
    }

    public Warehouse findById(Long id) throws SQLException {
        String sql = "SELECT id, name, address FROM warehouse WHERE id = ?";
        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Warehouse(rs.getLong("id"), rs.getString("name"), rs.getString("address"));
                }
            }
        }
        return null;
    }

    public void create(Warehouse warehouse) throws SQLException {
        String sql = "INSERT INTO warehouse  (name, address) VALUES (?, ?)";
        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, warehouse.getName());
            ps.setString(2, warehouse.getAddress());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) warehouse.setId(rs.getLong(1));
            }
        }
    }

    public void update(Warehouse warehouse) throws SQLException {
        String sql = "UPDATE warehouse SET name=?, address=? WHERE id=?";
        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, warehouse.getName());
            ps.setString(2, warehouse.getAddress());
            ps.setLong(3, warehouse.getId());
            ps.executeUpdate();
        }
    }

    public void delete(Long id) throws SQLException {
        String sql = "DELETE FROM warehouse WHERE id = ?";
        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

    public List<Warehouse> search(String keyword) throws SQLException {
        List<Warehouse> warehouses = new ArrayList<>();
        String sql = "SELECT * FROM warehouse WHERE LOWER(name) LIKE ? OR LOWER(address) LIKE ?";
        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String pattern = "%" + keyword.toLowerCase() + "%";
            ps.setString(1, pattern);
            ps.setString(2, pattern);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Warehouse warehouse = new Warehouse(
                            rs.getString("name"),
                            rs.getString("address")
                    );
                    warehouse.setId(rs.getLong("id"));
                    warehouses.add(warehouse);
                }
            }
        }
        return warehouses;
    }
}
