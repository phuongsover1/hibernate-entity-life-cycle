package com.hibernate.hibernateinterviewquestiondemo.services;

import com.hibernate.hibernateinterviewquestiondemo.entities.Employee;
import com.hibernate.hibernateinterviewquestiondemo.repositories.EmployeeRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeService {
  private final EmployeeRepository repository;
  private final SessionFactory sessionFactory;

  public EmployeeService(EmployeeRepository repository, SessionFactory sessionFactory) {
    this.repository = repository;
    this.sessionFactory = sessionFactory;
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

  public void showEntityLifeCycle() {
    Session session = sessionFactory.openSession();
    session.beginTransaction();

    Employee employee = session.get(Employee.class, 2L);
    System.out.println(employee);

    boolean doesExist =  session.contains(employee);
    System.out.println(employee + ", exists: " + doesExist);

    // session cleared, it comes in detached state
    session.clear();
    boolean doesExistAfterCleared = session.contains(employee);
    System.out.println(employee + ", exists: " + doesExistAfterCleared);

    session.save(employee);
    boolean doesExistAfterInserted = session.contains(employee);
    System.out.println(employee + ", exists: " + doesExistAfterInserted);
    // remove object it goes into removed state
    session.delete(employee);
    boolean doesExistAfterDeleted = session.contains(employee);
    System.out.println(employee + ", exists: " + doesExistAfterDeleted);
    // this is the diff between JPA and hibernate -> hibernate allows us to remove  even in detached entities
    session.getTransaction().commit();
    session.close();
  }
}
