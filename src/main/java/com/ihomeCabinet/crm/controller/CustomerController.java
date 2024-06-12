package com.ihomeCabinet.crm.controller;

import com.ihomeCabinet.crm.model.Customer;
import com.ihomeCabinet.crm.service.CustomerService;
import com.ihomeCabinet.crm.tools.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/list")
    @CrossOrigin(origins = Tool.FRONTADDR)
    public List<Customer> getAllCustomers() {
        System.out.println("get customers");
        return customerService.findAll();
    }

    @GetMapping("/{id}")
    @CrossOrigin(origins = Tool.FRONTADDR)
    public ResponseEntity<Customer> getCustomerById(@PathVariable Integer id) {
        return customerService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/save")
    @CrossOrigin(origins = Tool.FRONTADDR)
    public Customer createCustomer(@RequestBody Customer customer) {
        customer.setStatus(1);
        return customerService.save(customer);
    }

    @PutMapping("/{id}")
    @CrossOrigin(origins = Tool.FRONTADDR)
    public ResponseEntity<Customer> updateCustomer(@PathVariable Integer id, @RequestBody Customer customer) {
        return customerService.findById(id)
                .map(existingCustomer -> {
                    customer.setId(existingCustomer.getId());
                    return ResponseEntity.ok(customerService.save(customer));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Integer id) {
        return customerService.findById(id)
                .map(existingCustomer -> {
                    customerService.deleteById(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
