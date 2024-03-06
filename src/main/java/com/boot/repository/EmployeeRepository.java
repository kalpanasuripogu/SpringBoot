package com.boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boot.model.Employee;



public interface EmployeeRepository extends JpaRepository<Employee, Long>{

}