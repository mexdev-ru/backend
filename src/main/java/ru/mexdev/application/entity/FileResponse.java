package ru.mexdev.application.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.core.io.InputStreamResource;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Entity
@Table(name = "files")
@JsonInclude(value = JsonInclude.Include.NON_NULL, valueFilter = RepresentationModel.class)
public class FileResponse extends RepresentationModel<FileResponse> implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue
    UUID uuid;
    @Column(name = "filename")
    String filename;
    @Column(name = "contentType")
    String contentType;
    @Column(name = "fileSize")
    Long fileSize;
    @Column(name = "createdTime")
    Date createdTime;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Schema(hidden = true)
    @Column(name = "stream")
    transient InputStreamResource stream;

    public FileResponse(UUID uuid, String filename, String contentType, Long fileSize, Date createdTime, InputStreamResource stream) {
        this.uuid = uuid;
        this.filename = filename;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.createdTime = createdTime;
        this.stream = stream;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public InputStreamResource getStream() {
        return stream;
    }

    public void setStream(InputStreamResource stream) {
        this.stream = stream;
    }


}
