package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    private Long id;

    private String name;

    private Long manufacturerId;

    private Long categoryId;

    private Manufacturer manufacturer;

    private Category category;

    private String imageUrl;

    private Integer lifeDays;

    private BigDecimal weight;

    private String composition;


    public Product(Long id, String name, Long manufacturerId, Long categoryId, String imageUrl, Integer lifeDays, BigDecimal weight, String composition, Object manufacturer, Object category) {

        this.id = id;
        this.name = name;
        this.manufacturerId = manufacturerId;
        this.categoryId = categoryId;
        this.imageUrl = imageUrl;
        this.lifeDays = lifeDays;
        this.weight = weight;
        this.composition = composition;
    }

    public Product(String name, Manufacturer manufacturer, Category category, String imageUrl, Integer lifeDays, BigDecimal weight, String composition) {
        this.name = name;
        this.manufacturer = manufacturer;
        this.category = category;
        this.imageUrl = imageUrl;
        this.lifeDays = lifeDays;
        this.weight = weight;
        this.composition = composition;
    }

    public Product(Long id, String name, Manufacturer manufacturer, Category category, String fileName, Integer lifeDays, BigDecimal weight, String composition) {
        this.id = id;
        this.name = name;
        this.manufacturer = manufacturer;
        this.category = category;
        this.imageUrl = fileName;
        this.lifeDays = lifeDays;
        this.weight = weight;
        this.composition = composition;
    }
}
