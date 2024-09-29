package com.hibernate.hibernateinterviewquestiondemo.services;

import com.hibernate.hibernateinterviewquestiondemo.entities.Employee;
import com.hibernate.hibernateinterviewquestiondemo.repositories.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeService {
  private final EmployeeRepository repository;

  public EmployeeService(EmployeeRepository repository) {
    this.repository = repository;
  }

  public Employee getEmployeeById(Long id) throws Exception {
    Optional<Employee> employee = repository.findById(id);
    if (employee.isPresent()) {
      return employee.get();
    } else {
      throw new Exception("No employee record exist for given id");
    }
  }
}
