package com.ing.brokagetest.repository;

import com.ing.brokagetest.entity.Customer;

import java.util.Optional;

public interface CustomerRepository extends BaseRepository<Customer> {
    Customer findByNameAndPassword(String name, String password);
    Optional<Customer> findByName(String name);
}
