package com.deep.customer.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "customer")
@Data
public class Customer {

    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String email;
    private String phone;
    private String gender;
}
