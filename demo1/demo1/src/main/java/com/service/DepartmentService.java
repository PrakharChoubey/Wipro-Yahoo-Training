package com.springboot.demo1.service;

import com.springboot.demo1.entity.Department;
import com.springboot.demo1.error.DepartmentNotFoundException;
import com.springboot.demo1.error.IllegalNameUsedException;

import java.util.List;

public interface DepartmentService {

    public Department saveDepartment(Department department);

    public List<Department> fetchDepartmentList();


    public Department fetchDepartmentById(Long departmentId) throws DepartmentNotFoundException;


    public void deleteDepartmentById(Long departmentId);

    public Department updateDepartment(Long departmentId, Department department);

    public Department fetchDepartmentByName(String departmentName) throws IllegalNameUsedException;
}
