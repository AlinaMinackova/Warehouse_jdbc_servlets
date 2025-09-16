package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Manufacturer {

    private Long id;

    private String name;

    private String country;

    private String email;

    public Manufacturer(String name, String country, String email) {
        this.name = name;
        this.country = country;
        this.email = email;
    }
}
