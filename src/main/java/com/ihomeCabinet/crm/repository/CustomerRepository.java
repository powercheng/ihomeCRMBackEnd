package com.ihomeCabinet.crm.repository;


import com.ihomeCabinet.crm.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Page<Customer> findBySalePlace(Integer salePlace, PageRequest pageRequest);

    Page<Customer> findBySalePlaceAndFinishFalse(Integer salePlace, PageRequest pageRequest);
}

