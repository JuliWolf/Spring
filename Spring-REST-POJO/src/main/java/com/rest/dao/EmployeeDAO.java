package com.rest.dao;

import com.rest.entity.Employee;

import java.util.List;

public interface EmployeeDAO {
  public List<Employee> getEmployees();

  public Employee getById(int id);

  public void save(Employee employee);

  public void deleteById (int id);
}
