package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.Store;

import java.sql.Date;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor@NoArgsConstructor
public class Stock {

    private Long id;

    private Long warehouseId;

    private Long productId;

    private Warehouse warehouse;

    private Product product;

    private Integer quantity;

    private LocalDateTime arrivalDate = LocalDateTime.now();

    private Long storekeeperId;

    private Storekeeper storekeeper;

    private String formatter;

    public Stock(Long id, Long warehouseId, Long productId, Integer quantity, LocalDateTime arrivalDate, Long storekeeperId, Object warehouse, Object product, Object storekeeper) {
        this.id = id;
        this.warehouseId = warehouseId;
        this.productId = productId;
        this.quantity = quantity;
        this.arrivalDate = arrivalDate;
        this.storekeeperId = storekeeperId;

    }

    public Stock(Warehouse warehouse, Product product, String quantity, LocalDateTime arrivalDate, Storekeeper storekeeper) {
        this.warehouse = warehouse;
        this.product = product;
        this.quantity = Integer.parseInt(quantity);
        this.arrivalDate = arrivalDate;
        this.storekeeper = storekeeper;
    }

    public Stock(Long id, Warehouse warehouse, Product product, String quantity, LocalDateTime arrivalDate, Storekeeper storekeeper) {
        this.id = id;
        this.warehouse = warehouse;
        this.product = product;
        this.quantity = Integer.parseInt(quantity);
        this.arrivalDate = arrivalDate;
        this.storekeeper = storekeeper;
    }
}


