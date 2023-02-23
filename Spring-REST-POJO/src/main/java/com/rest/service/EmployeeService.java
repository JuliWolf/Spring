package com.rest.service;

import com.rest.dao.EmployeeDAO;
import com.rest.entity.Employee;

import java.util.List;

public interface EmployeeService {
  public List<Employee> getEmployees();

  public Employee getById(int id);

  public void save (Employee employee);

  public void deleteById (int id);
}
