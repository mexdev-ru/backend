package ru.mexdev.application.controller;

import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.mexdev.application.entity.*;
import ru.mexdev.application.service.QuotaService;
import ru.mexdev.application.service.RoleService;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping("quota")
public class QuotaController {
  
  @Autowired
  private QuotaService quotaService;

//TODO CALL WITH USER user
  @RequestMapping(method = RequestMethod.GET, path = "/callService")
  public ResponseEntity<?> callService(@RequestBody QuotaLicense quotaLicense,
                                 KeycloakAuthenticationToken authentication) {
    int remainingRequests = quotaService.readQuotaLicense(quotaLicense.getUuid()).getAmount()-
            quotaService.readUsageQuota(UUID.fromString(authentication.getPrincipal().toString())).getCounter();
    if(remainingRequests>0){
      //WTF
      //return this.http.get("http://srvc:8080/test").map(result => result.json());

      ResponseEntity response = new ResponseEntity<>(getData().getBody(), HttpStatus.OK);
      quotaService.readUsageQuota(UUID.fromString(authentication.getPrincipal().toString())).incrementCounter();
      return (response);
    }
    return new ResponseEntity<>(false, HttpStatus.FORBIDDEN);
  }

  private ResponseEntity<?> getData() {
    final String uri = "https://api.myrestservice.com/data";
    RestTemplate restTemplate = new RestTemplate();
    String result = restTemplate.getForObject(uri, String.class);
    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  @RequestMapping(method = RequestMethod.GET, path = "/license/{id}")
  public ResponseEntity<QuotaLicense> readLicense(@PathVariable(name = "id") UUID id) {
    final QuotaLicense quotaLicense = quotaService.readQuotaLicense(id);

    return quotaLicense != null
        ? new ResponseEntity<>(quotaLicense, HttpStatus.OK)
        : new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @RequestMapping(method = RequestMethod.GET, path = "/usage/{id}")
  public ResponseEntity<QuotaUsage> readUsage(@PathVariable(name = "id") UUID id) {
    final QuotaUsage quotaUsage = quotaService.readUsageQuota(id);

    return quotaUsage != null
            ? new ResponseEntity<>(quotaUsage, HttpStatus.OK)
            : new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @RequestMapping(method = RequestMethod.GET, path = "/allLicense")
  public ResponseEntity<List<QuotaLicense>> readAllLicense() {
    final List<QuotaLicense> quotas = quotaService.readAllQuotaLicence();

    return quotas != null
        ? new ResponseEntity<>(quotas, HttpStatus.OK)
        : new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @RequestMapping(method = RequestMethod.GET, path = "/allusage")
  public ResponseEntity<List<QuotaUsage>> readAllUsage() {
    final List<QuotaUsage> quotas = quotaService.readAllQuotaUsage();

    return quotas != null
            ? new ResponseEntity<>(quotas, HttpStatus.OK)
            : new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @RequestMapping(method = RequestMethod.PUT, path = "/license/{id}")
  public ResponseEntity<?> updateLicense(@PathVariable(name = "id") User user,
                                  @RequestBody QuotaLicense quotaLicense) {
    return quotaService.updateQuotaLicense(quotaLicense, user)
        ? new ResponseEntity<>(HttpStatus.OK)
        : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
  }

  @RequestMapping(method = RequestMethod.PUT, path = "/usage/{id}")
  public ResponseEntity<?> updateUsage(@PathVariable(name = "id") User user,
                                  @RequestBody QuotaUsage quotaUsage) {
    return quotaService.updateQuotaUsage(quotaUsage, user)
            ? new ResponseEntity<>(HttpStatus.OK)
            : new ResponseEntity<>(HttpStatus.FORBIDDEN);
  }

  @RequestMapping(method = RequestMethod.POST, path = "")
  public ResponseEntity<?> create(@RequestBody QuotaLicense quotaLicense) {
    return quotaService.create(quotaLicense)
        ? new ResponseEntity<>(HttpStatus.CREATED)
        : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }

  @RequestMapping(method = RequestMethod.DELETE, path = "/license/{id}")
  public ResponseEntity<?> deleteLicense(@PathVariable(name = "id") User user) {
    return quotaService.deleteQuotaLicense(user)
        ? new ResponseEntity<>(HttpStatus.OK)
        : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
  }

  @RequestMapping(method = RequestMethod.DELETE, path = "/usage/{id}")
  public ResponseEntity<?> deleteUsage(@PathVariable(name = "id") User user) {
    return quotaService.deleteQuotaUsage(user)
            ? new ResponseEntity<>(HttpStatus.OK)
            : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
  }
}
