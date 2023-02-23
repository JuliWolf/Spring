package com.example.spring_hibernate_web.service;

import com.example.spring_hibernate_web.dao.CustomerDAO;
import com.example.spring_hibernate_web.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

  // need to inject customer dao
  @Autowired
  private CustomerDAO customerDAO;

  @Override
  @Transactional
  public List<Customer> getCustomers() {
    return customerDAO.getCustomers();
  }

  @Override
  @Transactional
  public void saveCustomer(Customer customer) {
    customerDAO.saveCustomer(customer);
  }

  @Override
  @Transactional
  public Customer getCustomer(int id) {
    return customerDAO.getCustomer(id);
  }

  @Override
  @Transactional
  public void deleteCustomer(int id) {
    customerDAO.deleteCustomer(id);
  }
}
