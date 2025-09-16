package org.example.dao;

import lombok.AllArgsConstructor;
import org.example.entity.Product;
import org.example.entity.Stock;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
public class StockDao {
    private final DataSource pool;


    public List<Stock> findAll() throws SQLException {
        String sql = "SELECT id, warehouse_id, product_id, quantity, arrival_date, storekeeper_id FROM stock ORDER BY id";
        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<Stock> list = new ArrayList<>();
            while (rs.next()) {
                list.add(new Stock(
                        rs.getLong("id"),
                        rs.getLong("warehouse_id"),
                        rs.getLong("product_id"),
                        rs.getInt("quantity"),
                        rs.getTimestamp("arrival_date").toLocalDateTime(),
                        rs.getLong("storekeeper_id"),
                        null, // manufacturer пока null
                        null ,
                        null// category пока null
                ));
            }
            return list;
        }
    }

    public Stock findById(Long id) throws SQLException {
        String sql = "SELECT id, warehouse_id, product_id, quantity, arrival_date, storekeeper_id FROM stock WHERE id = ?";
        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Stock(
                            rs.getLong("id"),
                            rs.getLong("warehouse_id"),
                            rs.getLong("product_id"),
                            rs.getInt("quantity"),
                            rs.getTimestamp("arrival_date").toLocalDateTime(),
                            rs.getLong("storekeeper_id"),
                            null, // manufacturer пока null
                            null ,
                            null// category пока null
                    );
                }
            }
        }
        return null;
    }

    public void create(Stock stock) throws SQLException {
        String sql = "INSERT INTO stock (warehouse_id, product_id, quantity, arrival_date, storekeeper_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, stock.getWarehouse().getId());
            ps.setLong(2, stock.getProduct().getId());
            ps.setInt(3, stock.getQuantity());
            ps.setTimestamp(4, Timestamp.valueOf(stock.getArrivalDate()));
            ps.setLong(5, stock.getStorekeeper().getId());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) stock.setId(rs.getLong(1));
            }
        }
    }

    public void update(Stock stock) throws SQLException {
        String sql = "UPDATE stock SET warehouse_id=?, product_id=?, quantity=?, arrival_date=?, storekeeper_id=? WHERE id=?";
        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, stock.getWarehouse().getId());
            ps.setLong(2, stock.getProduct().getId());
            ps.setInt(3, stock.getQuantity());
            ps.setTimestamp(4, Timestamp.valueOf(stock.getArrivalDate()));
            ps.setLong(5, stock.getStorekeeper().getId());
            ps.setLong(6, stock.getId());
            ps.executeUpdate();
        }
    }

    public void delete(Long id) throws SQLException {
        String sql = "DELETE FROM stock WHERE id = ?";
        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

    public List<Stock> findFiltered(Long warehouseId, Long productId, String sort) throws SQLException {
        StringBuilder sql = new StringBuilder(
                "SELECT id, warehouse_id, product_id, quantity, arrival_date, storekeeper_id " +
                        "FROM stock WHERE 1=1 "
        );

        List<Object> params = new ArrayList<>();

        if (warehouseId != null) {
            sql.append(" AND warehouse_id = ?");
            params.add(warehouseId);
        }

        if (productId != null) {
            sql.append(" AND product_id = ?");
            params.add(productId);
        }

        // сортировка по дате прихода
        if ("asc".equalsIgnoreCase(sort)) {
            sql.append(" ORDER BY arrival_date ASC");
        } else {
            sql.append(" ORDER BY arrival_date DESC");
        }

        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                List<Stock> list = new ArrayList<>();
                while (rs.next()) {
                    list.add(new Stock(
                            rs.getLong("id"),
                            rs.getLong("warehouse_id"),
                            rs.getLong("product_id"),
                            rs.getInt("quantity"),
                            rs.getTimestamp("arrival_date").toLocalDateTime(),
                            rs.getLong("storekeeper_id"),
                            null, // manufacturer пока null
                            null,
                            null  // category пока null
                    ));
                }
                return list;
            }
        }
    }

}