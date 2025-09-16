package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Warehouse {

    private Long id;

    private String name;

    private String address;

    public Warehouse(String name, String address) {
        this.name = name;
        this.address = address;
    }
}
