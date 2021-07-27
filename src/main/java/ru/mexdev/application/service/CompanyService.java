package ru.mexdev.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mexdev.application.entity.Company;
import ru.mexdev.application.entity.Employee;
import ru.mexdev.application.entity.EmployeeRole;
import ru.mexdev.application.entity.RoleInCompany;
import ru.mexdev.application.repository.CompanyRepository;
import ru.mexdev.application.repository.EmployeeRepository;
import ru.mexdev.application.repository.RoleRepository;

import java.util.List;
import java.util.UUID;

@Service
public class CompanyService {
    private final static UUID ADMIN_UUID = UUID.fromString("00000000-0000-0000-0000-000000000000");

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private RoleRepository roleRepository;

    public boolean create(Company element) {
        if (companyRepository.findByName(element.getName()).orElse(null) == null) {
            RoleInCompany role = new RoleInCompany(element.getUuid(), EmployeeRole.FOUNDER);
            roleRepository.save(role);
            Employee employee = new Employee(role, ADMIN_UUID);
            employeeRepository.save(employee);
            companyRepository.save(element);
            role.setCompanyUuid(element.getUuid());
            roleRepository.save(role);
            return true;
        }
        return false;
    }

    public List<Company> readAll() {
        return companyRepository.findAll();
    }

    public Company read(UUID id) {
        return companyRepository.findById(id).orElse(null);
    }

    public boolean update(Company element, UUID id) {
        if (companyRepository.existsById(id)) {
            element.setUuid(id);
            companyRepository.save(element);
            return true;
        }
        return false;
    }

    public boolean delete(UUID id) {
        if (companyRepository.existsById(id)) {
            companyRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
