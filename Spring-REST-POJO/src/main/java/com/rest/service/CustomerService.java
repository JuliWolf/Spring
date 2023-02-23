package com.rest.service;

import com.rest.entity.Customer;

import java.util.List;

public interface CustomerService {

  public List<Customer> getCustomers();

  Customer saveCustomer(Customer customer);

  Customer getCustomer(int id);

  void deleteCustomer(int id);
}
