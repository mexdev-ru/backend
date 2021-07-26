package ru.mexdev.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mexdev.application.entity.Employee;
import ru.mexdev.application.repository.EmployeeRepository;

import java.util.List;
import java.util.UUID;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private RoleService roleService;

    public boolean create(Employee element) {
        if (read(element.getIdOfRoleIssuer()) != null && roleService.create(element.getRole())) {
            employeeRepository.save(element);
            return true;
        }
        return false;
    }

    public List<Employee> readAll() {
        return employeeRepository.findAll();
    }

    public Employee read(UUID id) {
        return employeeRepository.findById(id).orElse(null);
    }

    public boolean update(Employee element, UUID id) {
        if (employeeRepository.existsById(id)) {
            element.setUuid(id);
            employeeRepository.save(element);
            return true;
        }
        return false;
    }

    public boolean delete(UUID id) {
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
