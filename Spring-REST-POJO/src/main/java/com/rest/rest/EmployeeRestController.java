package com.rest.rest;

import com.rest.dao.EmployeeDAO;
import com.rest.entity.Customer;
import com.rest.entity.Employee;
import com.rest.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RestController
//@RequestMapping("/api")
public class EmployeeRestController {

  private EmployeeService employeeService;

  @Autowired
  public EmployeeRestController(EmployeeService employeeService) {
    this.employeeService = employeeService;
  }

  // expose "/employees" and return list of employees
  @GetMapping("/employees")
  public List<Employee> getEmployees () {
    return employeeService.getEmployees();
  }

  @GetMapping("/employees/{employeeId}")
  public Employee getEmployee (@PathVariable int employeeId) {
    Employee employee = employeeService.getById(employeeId);

    if (employee == null) {
      throw new RuntimeException("Employee id not found " + employeeId);
    }

    return employee;
  }

  @PostMapping("/employees")
  public Employee addEmployee(@RequestBody Employee employee) {
    // set id to 0
    employee.setId(0);

    employeeService.save(employee);

    return employee;
  }

  @PutMapping("/employees")
  public Employee updateEmployee(@RequestBody Employee employee) {
    employeeService.save(employee);

    return employee;
  }

  @DeleteMapping("/employees/{employeeId}")
  public String deleteEmployee (@PathVariable int employeeId) {
    Employee employee = employeeService.getById(employeeId);

    if (employee == null) {
      throw new RuntimeException("Employee id not found " + employeeId);
    }

    employeeService.deleteById(employeeId);

    return "Deleted employee id - " + employeeId;
  }

}
