package org.example.dao;

import lombok.AllArgsConstructor;
import org.example.entity.Category;
import org.example.entity.Manufacturer;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
public class ManufacturerDao {
    private final DataSource pool;


    public List<Manufacturer> findAll() throws SQLException {
        String sql = "SELECT id, name, country, email FROM manufacturer ORDER BY id";
        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<Manufacturer> list = new ArrayList<>();
            while (rs.next()) {
                list.add(new Manufacturer(rs.getLong("id"), rs.getString("name"), rs.getString("country"), rs.getString("email")));
            }
            return list;
        }
    }

    public Manufacturer findById(Long id) throws SQLException {
        String sql = "SELECT id, name, country, email FROM manufacturer WHERE id = ?";
        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Manufacturer(rs.getLong("id"), rs.getString("name"),
                            rs.getString("country"), rs.getString("email"));
                }
            }
        }
        return null;
    }

    public void create(Manufacturer manufacturer) throws SQLException {
        String sql = "INSERT INTO manufacturer (name, country, email) VALUES (?, ?, ?)";
        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, manufacturer.getName());
            ps.setString(2, manufacturer.getCountry());
            ps.setString(3, manufacturer.getEmail());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) manufacturer.setId(rs.getLong(1));
            }
        }
    }

    public void update(Manufacturer manufacturer) throws SQLException {
        String sql = "UPDATE manufacturer SET name=?, country=?, email=?  WHERE id=?";
        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, manufacturer.getName());
            ps.setString(2, manufacturer.getCountry());
            ps.setString(3, manufacturer.getEmail());
            ps.setLong(4, manufacturer.getId());
            ps.executeUpdate();
        }
    }

    public void delete(Long id) throws SQLException {
        String sql = "DELETE FROM manufacturer WHERE id = ?";
        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }


    public List<Manufacturer> search(String keyword) throws SQLException {
        List<Manufacturer> list = new ArrayList<>();
        String sql = "SELECT * FROM manufacturer WHERE LOWER(name) LIKE ? OR LOWER(country) LIKE ?";
        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            String pattern = "%" + keyword.toLowerCase() + "%";
            ps.setString(1, pattern);
            ps.setString(2, pattern);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Manufacturer m = new Manufacturer();
                    m.setId(rs.getLong("id"));
                    m.setName(rs.getString("name"));
                    m.setCountry(rs.getString("country"));
                    m.setEmail(rs.getString("email"));
                    list.add(m);
                }
            }
        }
        return list;
    }
}
