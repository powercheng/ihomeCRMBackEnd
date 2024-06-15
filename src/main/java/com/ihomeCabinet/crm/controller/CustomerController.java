package com.ihomeCabinet.crm.controller;

import com.ihomeCabinet.crm.model.Customer;
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
import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;


    @GetMapping("/list")
    public Result getAllCustomers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String salePlace
    ) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Customer> customerPage;
        if (salePlace != null && !salePlace.isEmpty()) {
            customerPage = customerService.findBySalePlace(salePlace, pageRequest);
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

    @PostMapping("/save")
    public Result createCustomer(@RequestBody Customer customer, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        JwtSubject user = TokenUtil.getUserFromToken(token);
        if (customer.getId() == 0) {
            String path = this.getClass().getClassLoader().getResource("").getPath() + "static";
            customer.setId(null);
            customer.setStatus(1);
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
