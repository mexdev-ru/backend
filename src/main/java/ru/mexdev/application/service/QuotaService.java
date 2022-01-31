package ru.mexdev.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mexdev.application.entity.*;
import ru.mexdev.application.repository.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Service
public class QuotaService {

  @Autowired
  private QuotaUsageRepository quotaUsageRepository;
  @Autowired
  private QuotaLicenseRepository quotaLicenseRepository;

  public boolean create(@Valid QuotaLicense element) {
    element.setUuid(UUID.randomUUID());
    if (!quotaLicenseRepository.existsById(element.getUuid())) {
      //MYSOR
      QuotaLicense myquotaUsage = new QuotaLicense();
      User user = new User();
      user.setFirstName("asd");
      user.setEmail("@@@");
      user.setUuid(UUID.randomUUID());
      user.setPassword("123");
      user.setPhone("8800");
      user.setLastName("das");
      user.setPatronymic("asdafas");
      myquotaUsage.setUser(user);
      myquotaUsage.setPeriod(element.getPeriod());
      myquotaUsage.setAmount(element.getAmount());
      myquotaUsage.setUuid(UUID.randomUUID());

      quotaLicenseRepository.save(element);

      QuotaUsage quotaUsage = new QuotaUsage();
      quotaUsage.setCounter(element.getAmount());
      quotaUsage.setPeriod(element.getPeriod());
      quotaUsage.setUser(element.getUser());
      quotaUsage.setId(UUID.randomUUID());
      quotaUsageRepository.save(quotaUsage);
      return true;
    }
    return false;
  }

  public List<QuotaUsage> readAllQuotaUsage() {
    return quotaUsageRepository.findAll();
  }

  public QuotaUsage readUsageQuota(UUID id) {
    return quotaUsageRepository.findById(id).orElse(null);
  }

  public boolean updateQuotaUsage(@Valid QuotaUsage element, User user) {

    if (quotaUsageRepository.findQuotaUsagesByUser(user)==null) {
      return false;
    }
    element.setUser(user);
    quotaUsageRepository.save(element);
    return true;
  }

  public boolean deleteQuotaUsage(User user) {
    if (quotaUsageRepository.findQuotaUsagesByUser(user)==null) {
      return false;
    }
    quotaUsageRepository.deleteById(quotaUsageRepository.findQuotaUsagesByUser(user).get().getId());
    return true;
  }

  public List<QuotaLicense> readAllQuotaLicence() {
    return quotaLicenseRepository.findAll();
  }

  public QuotaLicense readQuotaLicense(UUID id) {
    return quotaLicenseRepository.findById(id).orElse(null);
  }

  public boolean updateQuotaLicense(@Valid QuotaLicense element, User user) {
    if (quotaLicenseRepository.findQuotaLicenseByUser(user)==null) {
      return false;
    }
    element.setUuid(quotaLicenseRepository.findQuotaLicenseByUser(user).get().getUuid());
    quotaLicenseRepository.save(element);
    return true;
  }

  public boolean deleteQuotaLicense(User user) {
    if (quotaLicenseRepository.findQuotaLicenseByUser(user)==null) {
      return false;
    }
    quotaLicenseRepository.deleteById(quotaLicenseRepository.findQuotaLicenseByUser(user).get().getUuid());
    return true;
  }
}

