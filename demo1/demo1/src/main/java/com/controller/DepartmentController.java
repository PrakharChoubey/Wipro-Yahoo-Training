package com.springboot.demo1.controller;

import com.springboot.demo1.entity.Department;
import com.springboot.demo1.error.DepartmentNotFoundException;
import com.springboot.demo1.error.IllegalNameUsedException;
import com.springboot.demo1.service.DepartmentService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    private final Logger LOGGER = LoggerFactory.getLogger(DepartmentController.class);

    @PostMapping("depts")
    public Department saveDepartment(@Valid @RequestBody Department department){
        LOGGER.info("Inside saveDept of  deptController");
        return departmentService.saveDepartment(department);
    }

    @GetMapping("depts")
    public List<Department> getDepartments(){
        LOGGER.info("Inside getDepts of deptController");
        return departmentService.fetchDepartmentList();
    }

    @GetMapping("/depts/{id}")
    public Department fetchDepartmentByID(@PathVariable("id") Long departmentId) throws DepartmentNotFoundException {
        return departmentService.fetchDepartmentById(departmentId);
    }

    @DeleteMapping("/depts/{id}")
    public String deleteDepartmentById(@PathVariable("id") Long departmentId){
        departmentService.deleteDepartmentById(departmentId);
    return "Department deleted successfully!!";
    }

    @PutMapping("/depts/{id}")
    public Department updateDepartment(@PathVariable("id") Long departmentId,
                                       @RequestBody Department department){
        return departmentService.updateDepartment(departmentId,department);
    }

    @GetMapping("/depts/name/{name}")
    public Department fetchDepartmentByName(@PathVariable("name") String departmentName) throws IllegalNameUsedException {
        return departmentService.fetchDepartmentByName(departmentName);
    }
}
