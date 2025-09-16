package org.example.dao;

import lombok.AllArgsConstructor;
import org.example.entity.Manufacturer;
import org.example.entity.Storekeeper;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class StorekeeperDao {
    private final DataSource pool;

    public List<Storekeeper> findAll() throws SQLException {
        String sql = "SELECT id, last_name, first_name, middle_name, email, birthday FROM storekeeper ORDER BY id";
        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<Storekeeper> list = new ArrayList<>();
            while (rs.next()) {
                list.add(new Storekeeper(rs.getLong("id"), rs.getString("last_name"),
                        rs.getString("first_name"), rs.getString("middle_name"),
                        rs.getString("email"), rs.getDate("birthday").toLocalDate()));
            }
            return list;
        }
    }

    public Storekeeper findById(Long id) throws SQLException {
        String sql = "SELECT id, last_name, first_name, middle_name, email, birthday FROM storekeeper WHERE id = ?";
        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Storekeeper(rs.getLong("id"), rs.getString("last_name"),
                            rs.getString("first_name"), rs.getString("middle_name"),
                            rs.getString("email"), rs.getDate("birthday").toLocalDate());
                }
            }
        }
        return null;
    }

    public void create(Storekeeper storekeeper) throws SQLException {
        String sql = "INSERT INTO storekeeper (last_name, first_name, middle_name, email, birthday) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, storekeeper.getLastName());
            ps.setString(2, storekeeper.getFirstName());
            ps.setString(3, storekeeper.getMiddleName());
            ps.setString(4, storekeeper.getEmail());
            ps.setDate(5, Date.valueOf(storekeeper.getBirthday()));
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) storekeeper.setId(rs.getLong(1));
            }
        }
    }

    public void update(Storekeeper storekeeper) throws SQLException {
        String sql = "UPDATE storekeeper SET last_name=?, first_name=?, middle_name=?, email=?, birthday=?  WHERE id=?";
        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, storekeeper.getLastName());
            ps.setString(2, storekeeper.getFirstName());
            ps.setString(3, storekeeper.getMiddleName());
            ps.setString(4, storekeeper.getEmail());
            ps.setDate(5, Date.valueOf(storekeeper.getBirthday()));
            ps.setLong(6, storekeeper.getId());
            ps.executeUpdate();
        }
    }

    public void delete(Long id) throws SQLException {
        String sql = "DELETE FROM storekeeper WHERE id = ?";
        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

    public List<Storekeeper> search(String keyword) throws SQLException {
        List<Storekeeper> list = new ArrayList<>();
        String sql = "SELECT * FROM storekeeper WHERE LOWER(last_name) LIKE ? OR LOWER(first_name) LIKE ?";
        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String pattern = "%" + keyword.toLowerCase() + "%";
            ps.setString(1, pattern);
            ps.setString(2, pattern);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Storekeeper s = new Storekeeper();
                    s.setId(rs.getLong("id"));
                    s.setLastName(rs.getString("last_name"));
                    s.setFirstName(rs.getString("first_name"));
                    s.setMiddleName(rs.getString("middle_name"));
                    s.setEmail(rs.getString("email"));
                    s.setBirthday(rs.getDate("birthday").toLocalDate());
                    list.add(s);
                }
            }
        }
        return list;
    }
}
