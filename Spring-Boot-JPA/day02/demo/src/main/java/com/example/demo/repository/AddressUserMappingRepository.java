package com.example.demo.repository;

import com.example.demo.domain.AddressUserMapping;
import com.example.demo.domain.AddressUserMappingPk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressUserMappingRepository extends JpaRepository<AddressUserMapping, AddressUserMappingPk> {
    void updateByUserUserId(String userId, AddressUserMapping addressUserMapping);

    void deleteByUserUserId(String userId);

    int countAddressUserMappingByUserUserId(String userId);
}
