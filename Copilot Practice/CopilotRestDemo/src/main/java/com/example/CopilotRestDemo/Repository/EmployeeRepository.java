package com.example.CopilotRestDemo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.CopilotRestDemo.Model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
