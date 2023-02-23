package com.rest.dao;

import com.rest.entity.Employee;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class EmployeeDAOImpl implements EmployeeDAO {

  // define field for entityManager
  private final EntityManager entityManager;

  // set up constructor injection
  @Autowired
  public EmployeeDAOImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public List<Employee> getEmployees() {
    // get the current hibernate session
    Session currentSession = entityManager.unwrap(Session.class);

    // create query
    Query<Employee> query = currentSession
        .createQuery("from Employee", Employee.class);

    // execute query and get result list
    // return the results
    return query.getResultList();
  }

  @Override
  public Employee getById(int id) {
    // get the current hibernate session
    Session currentSession = entityManager.unwrap(Session.class);

    // get the employee
    // return the employee
    return currentSession.get(Employee.class, id);
  }

  @Override
  public void save(Employee employee) {
    // get the current hibernate session
    Session currentSession = entityManager.unwrap(Session.class);

    // save employee
    currentSession.saveOrUpdate(employee);
  }

  @Override
  public void deleteById(int id) {
    // get the current hibernate session
    Session currentSession = entityManager.unwrap(Session.class);

    // delete object with primary key
    Query query = currentSession.createQuery("delete from Employee where id=:employeeId");

    query.setParameter("employeeId", id);

    query.executeUpdate();
  }
}
