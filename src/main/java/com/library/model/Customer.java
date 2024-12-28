package com.library.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Date dob;
    private String address;

    public Customer() {
    }

    public Customer(Long id, String name, Date dob, String address) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.address = address;
    }
}
