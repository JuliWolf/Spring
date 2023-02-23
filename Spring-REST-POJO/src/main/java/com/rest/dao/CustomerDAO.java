package com.rest.dao;

import com.rest.entity.Customer;

import java.util.List;

public interface CustomerDAO {

	public List<Customer> getCustomers();

	public Customer saveCustomer(Customer theCustomer);

	public Customer getCustomer(int theId);

	public void deleteCustomer(int theId);
	
}
