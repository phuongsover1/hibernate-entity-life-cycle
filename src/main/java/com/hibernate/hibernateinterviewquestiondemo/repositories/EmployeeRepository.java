package com.hibernate.hibernateinterviewquestiondemo.repositories;

import com.hibernate.hibernateinterviewquestiondemo.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository  extends JpaRepository<Employee, Long> {
}
