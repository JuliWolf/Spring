package com.example.spring_hibernate_web.service;

import com.example.spring_hibernate_web.entity.Customer;

import java.util.List;

public interface CustomerService {

  public List<Customer> getCustomers();

  void saveCustomer(Customer customer);

  Customer getCustomer(int id);

  void deleteCustomer(int id);
}
