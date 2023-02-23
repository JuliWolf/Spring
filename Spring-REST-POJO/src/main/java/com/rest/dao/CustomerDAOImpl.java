package com.rest.dao;

import java.util.List;

import com.rest.entity.Customer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;


@Repository
public class CustomerDAOImpl implements CustomerDAO {

	// need to inject the session factory
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Customer> getCustomers() {
		// create a query ... sort by lst name
		TypedQuery<Customer> query = entityManager
				.createQuery("from Customer", Customer.class);

		// execute query and get result list
		// return the results
		return query.getResultList();
	}

	@Override
	public Customer saveCustomer(Customer customer) {
		// save or update the customer
		Customer newCustomer = entityManager.merge(customer);

		return newCustomer;
	}

	@Override
	public Customer getCustomer(int id) {
		return entityManager.find(Customer.class, id);
	}

	@Override
	public void deleteCustomer(int id) {
		// create query
		Query query = entityManager
				.createQuery("delete from Customer where id=:customerId");

		query.setParameter("customerId", id);

		query.executeUpdate();
	}

}











