package org.example.service;

import lombok.AllArgsConstructor;
import org.example.dao.StockDao;
import org.example.entity.Stock;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@AllArgsConstructor
public class StockService {
    private final StockDao stockDao;
    private final WarehouseService warehouseService;
    private final ProductService productService;
    private final StorekeeperService storekeeperService;


    public String getArrivalDateFormatted(LocalDateTime arrivalDate) {
        if (arrivalDate == null) return "";
        return arrivalDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
    }

    public List<Stock> findAll() throws SQLException {
        List<Stock> stocks = stockDao.findAll();

        for (Stock s : stocks) {
            s.setWarehouse(warehouseService.findById(s.getWarehouseId()));
            s.setProduct(productService.findById(s.getProductId()));
            s.setStorekeeper(storekeeperService.findById(s.getStorekeeperId()));
            s.setFormatter(getArrivalDateFormatted(s.getArrivalDate()));
        }

        return stocks;
    }

    public Stock findById(Long id) throws SQLException {
        Stock stock = stockDao.findById(id);
        stock.setWarehouse(warehouseService.findById(stock.getWarehouseId()));
        stock.setStorekeeper(storekeeperService.findById(stock.getStorekeeperId()));
        return stock;
    }

    public void add(Stock stock) throws SQLException {
        if (stock.getWarehouse().getId() == null || stock.getWarehouse().getId() <= 0) throw new IllegalArgumentException("warehouseId required");
        if (stock.getStorekeeper().getId() == null || stock.getStorekeeper().getId() <= 0) throw new IllegalArgumentException("storekeeperId required");
        if (stock.getProduct().getId() == null || stock.getProduct().getId() <= 0) throw new IllegalArgumentException("product Id required");
        if (stock.getQuantity() == null || stock.getQuantity() <= 0) throw new IllegalArgumentException("quantity required");
        stockDao.create(stock);
    }

    public void update(Stock stock, LocalDateTime arrivalDate) throws SQLException {
        if (stock.getId() == null || stock.getId() <= 0) {
            throw new IllegalArgumentException("Invalid id");
        }
        if (stock.getWarehouse().getId() == null || stock.getWarehouse().getId() <= 0) throw new IllegalArgumentException("warehouseId required");
        if (stock.getStorekeeper().getId() == null || stock.getStorekeeper().getId() <= 0) throw new IllegalArgumentException("storekeeperId required");
        if (stock.getProduct().getId() == null || stock.getProduct().getId() <= 0) throw new IllegalArgumentException("product Id required");
        if (stock.getQuantity() == null || stock.getQuantity() <= 0) throw new IllegalArgumentException("quantity required");

        if(arrivalDate == null){
            Stock stockExists = stockDao.findById(stock.getId());
            stock.setArrivalDate(stockExists.getArrivalDate());
        }
        stockDao.update(stock);
    }

    public void delete(Long id) throws SQLException {
        if (id == null) throw new IllegalArgumentException("id required");
        stockDao.delete(id);
    }

    public List<Stock> findFiltered(Long warehouseId, Long productId, String sort) throws SQLException {
        List<Stock> stocks = stockDao.findFiltered(warehouseId, productId, sort);

        for (Stock s : stocks) {
            s.setWarehouse(warehouseService.findById(s.getWarehouseId()));
            s.setProduct(productService.findById(s.getProductId()));
            s.setStorekeeper(storekeeperService.findById(s.getStorekeeperId()));
            s.setFormatter(getArrivalDateFormatted(s.getArrivalDate()));
        }

        return stocks;
    }
}