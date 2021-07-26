package ru.mexdev.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mexdev.application.entity.RoleInCompany;
import ru.mexdev.application.repository.CompanyRepository;
import ru.mexdev.application.repository.RoleRepository;

import java.util.List;
import java.util.UUID;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private CompanyService companyService;

    public boolean create(RoleInCompany element) {
        if (companyService.read(element.getCompanyUuid()) != null) {
            roleRepository.save(element);
            return true;
        }
        return false;
    }

    public List<RoleInCompany> readAll() {
        return roleRepository.findAll();
    }

    public RoleInCompany read(UUID id) {
        return roleRepository.findById(id).orElse(null);
    }

    public boolean update(RoleInCompany element, UUID id) {
        if (roleRepository.existsById(id)) {
            element.setUuid(id);
            roleRepository.save(element);
            return true;
        }
        return false;
    }

    public boolean delete(UUID id) {
        if (roleRepository.existsById(id)) {
            roleRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
