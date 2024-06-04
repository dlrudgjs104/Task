package com.example.demo.repository;

import com.example.demo.domain.Address;
import com.example.demo.domain.AddressUserMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, String> {
    List<Address> findAllByAddressNameLike(String name);

    List<Address> findAllByAddressNameContaining(String name);

    List<Address> findAllByAddressNameStartingWith(String name);

    List<Address> findAllByAddressNameEndingWith(String name);

    int countDistinctByAddressNameLike(String name);

    int countDistinctByAddressNameStartingWith(String name);

    int countDistinctByAddressNameEndingWith(String name);

    int countDistinctByAddressNameContaining(String name);

    void updateAddressByAddressNameEquals(String addressName, String newAddressName);

    void deleteAddressByAddressNameEquals(String addressName);
}
