package ru.mexdev.application.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "codes")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class Code {

    @Id
    @Column(name = "uuid")
    @GeneratedValue
    private UUID uuid;

    @Column(name = "code")
    private String code;

    @Column(name = "typeOfVocabulary")
    private String typeOfVocabulary;

    @Column(name = "coderOrganization")
    private String coderOrganization;

    @Column(name = "version")
    private String version;

    public UUID getUuid() {
        return uuid;
    }

    public String getCode() {
        return code;
    }

    public String getTypeOfVocabulary() {
        return typeOfVocabulary;
    }

    public String getCoderOrganization() {
        return coderOrganization;
    }

    public String getVersion() {
        return version;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setTypeOfVocabulary(String typeOfVocabulary) {
        this.typeOfVocabulary = typeOfVocabulary;
    }

    public void setCoderOrganization(String coderOrganization) {
        this.coderOrganization = coderOrganization;
    }

    public void setVersion(String version) {
        this.version = version;
    }

}
