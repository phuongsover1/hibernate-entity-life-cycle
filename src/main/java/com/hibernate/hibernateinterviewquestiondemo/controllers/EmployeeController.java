package com.hibernate.hibernateinterviewquestiondemo.controllers;


import com.hibernate.hibernateinterviewquestiondemo.entities.Employee;
import com.hibernate.hibernateinterviewquestiondemo.services.EmployeeService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
  private final EmployeeService employeeService;

  private final SessionFactory sessionFactory;

  public EmployeeController(EmployeeService employeeService, SessionFactory sessionFactory) {
    this.employeeService = employeeService;
    this.sessionFactory = sessionFactory;
  }

  @GetMapping("/{id}")
  public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) throws Exception {
    Employee entity = employeeService.getEmployeeById(id);
    return new ResponseEntity<>(entity, new HttpHeaders(), HttpStatus.OK);
  }

  @PostMapping(consumes = "application/json")
  public ResponseEntity<Employee> createOrUpdateEmployee(@RequestBody Employee employee) throws Exception {
    // Here employee object will be in new state
    Session session = sessionFactory.openSession();
    session.beginTransaction();

    // Here employee objecct will be in new state hence its not in session / first level cache.
    Boolean doesExist = session.contains(employee);
    System.out.println(employee.getFirstName() + " " + employee.getLastName() + " exists status : " + doesExist);

    Employee updated = employeeService.createorUpdateEmployee(employee);
    doesExist = session.contains(updated);
    System.out.println(updated.getFirstName() + " " + updated.getLastName() + " exists status : " + doesExist);

    // Here employee object will be in persistent state as u can ses query is fired
    Employee employee2 = session.get(Employee.class, 2L);
    boolean doesExistEmp2 = session.contains(employee2);
    System.out.println(employee2.getFirstName() + " " + employee2.getLastName() + " exists status : " + doesExistEmp2);

    // Query is not fired as session now has this object with id 2
    Employee employee3 = session.get(Employee.class, 2L);
    boolean doesExistEmp3 = session.contains(employee3);
    System.out.println(employee3.getFirstName() + " " + employee3.getLastName() + " exists status : " + doesExistEmp3);

    return new ResponseEntity<>(updated, HttpStatus.CREATED);
  }


}
