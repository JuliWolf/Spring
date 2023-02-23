package com.rest.rest;

import com.rest.entity.Customer;
import com.rest.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CustomerRestController {

  // autowire the CustomerService
  @Autowired
  private CustomerService customerService;

  // add mapping for Get /customers
  @GetMapping("/customers")
  public List<Customer> getCustomers () {
    return customerService.getCustomers();
  }

  // add mapping for GET /customers/{customerId}
  @GetMapping("/customers/{customerId}")
  public Customer getCustomer (@PathVariable int customerId) {
    Customer customer = customerService.getCustomer(customerId);

    if (customer == null) {
      throw new CustomerNotFoundException("Customer is not found - " + customerId);
    }

    return customer;
  }

  // add mapping for POST /customers - add new customer
  @PostMapping("/customers")
  public Customer addCustomer (@RequestBody Customer customer) {
    // also just in case the pass an id in jSON ... set id to -0
    customer.setId(0);

    return customerService.saveCustomer(customer);
  }

  // add mapping for PUT /customers - update existing customer
  @PutMapping("/customers")
  public Customer updateCustomer(@RequestBody Customer customer) {
    return customerService.saveCustomer(customer);
  }

  // add mapping for DELETE /customers - delete customer
  @DeleteMapping("/customers/{customerId}")
  public String deleteCustomer(@PathVariable int customerId) {
    Customer customer = customerService.getCustomer(customerId);

    if (customer == null) {
      throw new CustomerNotFoundException("Customer is not found - " + customerId);
    }

    customerService.deleteCustomer(customerId);

    return "Deleted customer id - " + customerId;
  }

}
