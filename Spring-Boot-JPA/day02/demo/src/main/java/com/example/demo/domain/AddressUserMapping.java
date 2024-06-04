package com.example.demo.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "address_user_mapping")
@IdClass(AddressUserMappingPk.class)
public class AddressUserMapping {

    @Id
    @Column(name = "category_id", nullable = false)
    @ManyToOne(optional = false)
    private Address address;

    @Id
    @Column(name = "product_id", nullable = false)
    @ManyToOne(optional = false)
    private User user;
}