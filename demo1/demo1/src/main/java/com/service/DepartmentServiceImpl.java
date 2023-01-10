package com.springboot.demo1.service;

import com.springboot.demo1.entity.Department;
import com.springboot.demo1.error.DepartmentNotFoundException;
import com.springboot.demo1.error.IllegalNameUsedException;
import com.springboot.demo1.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentService{

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public Department saveDepartment(Department department) {
       return departmentRepository.save(department);
    }

    @Override
    public List<Department> fetchDepartmentList() {
        return departmentRepository.findAll();
    }

    @Override
    public Department fetchDepartmentById(Long departmentId) throws DepartmentNotFoundException {
        Optional<Department> department = departmentRepository.findById(departmentId);
        if(!department.isPresent()){
            throw new DepartmentNotFoundException("Department Not Available");
        }
        return department.get();
    }

    @Override
    public void deleteDepartmentById(Long departmentId) {
        departmentRepository.deleteById(departmentId);
    }

    @Override
    public Department updateDepartment(Long departmentId, Department department) {
        Department deptDB=departmentRepository.findById(departmentId).get();
        if(Objects.nonNull(department.getDepartmentName()) && !"".equalsIgnoreCase(department.getDepartmentName())){
            deptDB.setDepartmentName(department.getDepartmentName());
        }
        if(Objects.nonNull(department.getDepartmentCode()) && !"".equalsIgnoreCase(department.getDepartmentCode())){
            deptDB.setDepartmentName(department.getDepartmentCode());
        }
        if(Objects.nonNull(department.getDepartmentAddress()) && !"".equalsIgnoreCase(department.getDepartmentAddress())){
            deptDB.setDepartmentName(department.getDepartmentAddress());
        }
        return departmentRepository.save(deptDB);
    }

    @Override
    public Department fetchDepartmentByName(String departmentName) throws IllegalNameUsedException {
        if(departmentName.equalsIgnoreCase("shit"))
            throw new IllegalNameUsedException("Illegal name is used.");
        return departmentRepository.findByDepartmentNameIgnoreCase(departmentName);
    }


}
