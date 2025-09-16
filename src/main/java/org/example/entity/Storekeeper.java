package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Storekeeper {

    private Long id;

    private String lastName;

    private String firstName;

    private String middleName;

    private String email;

    private LocalDate birthday;


    public Storekeeper(String lastName, String firstName, String middleName, String email, LocalDate birthday) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.email = email;
        this.birthday = birthday;
    }
}