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

  public Employee createorUpdateEmployee(Employee employee) {
    if (employee.getId() == null) {
      // It comes in managed state.
      employee = repository.save(employee);
      return employee;
    }
    // It comes in managed state
    Optional<Employee> optionalEmployee = repository.findById(employee.getId());
    if (optionalEmployee.isPresent()) {
      Employee newEntity = optionalEmployee.get();
      newEntity.setFirstName(employee.getFirstName());
      newEntity.setLastName(employee.getLastName());
      newEntity.setEmail(employee.getEmail());

      newEntity = repository.save(newEntity);

      return newEntity;
    } else {
      employee = repository.save(employee);
      return employee;
    }
  }
}
