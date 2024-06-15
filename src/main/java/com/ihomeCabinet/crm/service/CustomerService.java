package com.ihomeCabinet.crm.service;

import com.ihomeCabinet.crm.model.Customer;
import com.ihomeCabinet.crm.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Page<Customer> findAll(PageRequest pageRequest) {
        return customerRepository.findAll(pageRequest);
    }

    public Optional<Customer> findById(Integer id) {
        return customerRepository.findById(id);
    }

    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    public void deleteById(Integer id) {
        customerRepository.deleteById(id);
    }

    public Page<Customer> findBySalePlace(String salePlace, PageRequest pageRequest) { return customerRepository.findBySalePlace(salePlace, pageRequest);
    }
}
