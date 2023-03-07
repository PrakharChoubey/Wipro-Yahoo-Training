package com.example.CopilotRestDemo.Service;

import com.example.CopilotRestDemo.Model.Employee;
import com.example.CopilotRestDemo.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    List<Employee> employees = new ArrayList<>();

    public EmployeeService() {
        employees.add(new Employee(1, "John", "Doe", "hello", "123", "123"));
    }
    //function named getEmployeesByFirstName
    public List<Employee> getEmployeesByFirstName(String firstName) {
        List<Employee> employeesByFirstName = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee.getFirstName().equals(firstName)) {
                employeesByFirstName.add(employee);
            }
        }
        return employeesByFirstName;
    }

    public Employee getEmployee(int id) {
        for (Employee employee : employees) {
            if (employee.getId() == id) {
                return employee;
            }
        }
        return null;
    }

    //function to upperCase LastName
    public String toUpperCaseLastName(String lastName) {
        return lastName.toUpperCase();
    }

    //method to return fullName
    public String getFullName(String firstName, String lastName) {
        return firstName + " " + lastName;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(int id) {
        return employeeRepository.findById(id).get();
    }

    public Employee addEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Employee employee, int id) {
        Employee TempEmployee = employeeRepository.findById(id).get();
        TempEmployee.setFirstName(employee.getFirstName());
        TempEmployee.setLastName(employee.getLastName());
        TempEmployee.setEmail(employee.getEmail());
        TempEmployee.setPhoneNumber(employee.getPhoneNumber());
        TempEmployee.setAddress(employee.getAddress());
        return employeeRepository.save(TempEmployee);
    }

    public void deleteEmployee(int id) {
        employeeRepository.deleteById(id);
    }
}
