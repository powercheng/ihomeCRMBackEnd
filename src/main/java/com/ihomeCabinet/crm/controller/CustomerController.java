package com.ihomeCabinet.crm.controller;

import com.ihomeCabinet.crm.model.Customer;
import com.ihomeCabinet.crm.service.UserService;
import com.ihomeCabinet.crm.tools.JwtSubject;
import com.ihomeCabinet.crm.tools.TokenUtil;
import com.ihomeCabinet.crm.tools.response.PaginatedResult;
import com.ihomeCabinet.crm.service.CustomerService;
import com.ihomeCabinet.crm.tools.response.Result;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private UserService userService;


    @GetMapping("/list")
    public Result getAllCustomers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Integer salePlace
    ) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Customer> customerPage;
        if (salePlace != null) {
            customerPage = customerService.findBySalePlaceAndFinishFalse(salePlace, pageRequest);
        } else {
            customerPage = customerService.findAll(pageRequest);
        }

        List<Customer> customers = customerPage.getContent();
        long totalElements = customerPage.getTotalElements();

        // Create a response object with customers and total count
        PaginatedResult<Customer> paginatedResult = new PaginatedResult<>(customers, totalElements);

        return Result.ok(paginatedResult);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Integer id) {
        return customerService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/submit")
    public Result submitCustomer(@RequestBody Customer customer, HttpServletRequest request) {
        customer.setStatus(customer.getStatus()+1);
        if (customer.getId() == 0) {
            if (customer.getMeasurer() == null) {
                return Result.fail("please assign a measure person");
            }
            customer.setOwnerId(userService.findByUsername(customer.getMeasurer()).getId());
            customer.setId(null);
            String token = request.getHeader("Authorization").substring(7);
            JwtSubject user = TokenUtil.getUserFromToken(token);
            String path = this.getClass().getClassLoader().getResource("").getPath() + "static";
            customer.setSalePlace(user.getRegion());
            Customer res = customerService.save(customer);
            File directory = new File(path+File.separator+res.getId());
            directory.mkdir();
            File directory1 = new File(path+File.separator+res.getId()+File.separator+"measureFiles");
            directory1.mkdir();
            File directory2 = new File(path+File.separator+res.getId()+File.separator+"designFiles");
            directory2.mkdir();
            File directory3 = new File(path+File.separator+res.getId()+File.separator+"orderFiles");
            directory3.mkdir();
            File directory4 = new File(path+File.separator+res.getId()+File.separator+"finalFiles");
            directory4.mkdir();
            return Result.ok(res);
        } else {
            int status = customer.getStatus();
            if (status == 2) {
                if (customer.getMeasurer() == null) {
                    return Result.fail("please assign a measure person");
                }
                customer.setOwnerId(userService.findByUsername(customer.getMeasurer()).getId());
                customer.setMeasuredAt(LocalDateTime.now());
            } else if (status == 3) {
                if (customer.getDesigner() == null) {
                    return Result.fail("please assign a designer person");
                }
                customer.setOwnerId(userService.findByUsername(customer.getDesigner()).getId());
                customer.setDesignedAt(LocalDateTime.now());
            } else if (status == 4) {
                customer.setFinish(true);
                customer.setOrderedAt(LocalDateTime.now());
            } else if (status == 5) {
                customer.setFinish(true);
                customer.setProducedAt(LocalDateTime.now());
            } else if (status == 6) {
                customer.setFinish(true);
                customer.setFinishedAt(LocalDateTime.now());
            } else if (status == 7) {
                customer.setFinish(true);
            }
            customerService.save(customer);
            return Result.ok(customer);
        }
    }

    @PostMapping("/save")
    public Result saveCustomer(@RequestBody Customer customer, HttpServletRequest request) {
        if (customer.getId() == 0) {
            // if designer not assigned, return error
            if (customer.getSaleRep() == null) {
                return Result.fail("please assign a sale representative");
            }
            customer.setOwnerId(userService.findByUsername(customer.getSaleRep()).getId());
            customer.setId(null);
            String token = request.getHeader("Authorization").substring(7);
            JwtSubject user = TokenUtil.getUserFromToken(token);
            String path = this.getClass().getClassLoader().getResource("").getPath() + "static";
            customer.setSalePlace(user.getRegion());
            Customer res = customerService.save(customer);
            File directory = new File(path+File.separator+res.getId());
            directory.mkdir();
            File directory1 = new File(path+File.separator+res.getId()+File.separator+"measureFiles");
            directory1.mkdir();
            File directory2 = new File(path+File.separator+res.getId()+File.separator+"designFiles");
            directory2.mkdir();
            File directory3 = new File(path+File.separator+res.getId()+File.separator+"orderFiles");
            directory3.mkdir();
            File directory4 = new File(path+File.separator+res.getId()+File.separator+"finalFiles");
            directory4.mkdir();
            return Result.ok(res);
        } else {
            customerService.save(customer);
            return Result.ok(customer);
        }

    }

    @PutMapping("/{id}")
    public Result updateCustomer(@PathVariable Integer id, @RequestBody Customer customer) {
        customerService.save(customer);
        return Result.ok();
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
