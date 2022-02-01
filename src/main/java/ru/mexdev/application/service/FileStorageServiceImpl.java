package ru.mexdev.application.service;

import com.jlefebure.spring.boot.minio.MinioException;
import com.jlefebure.spring.boot.minio.MinioService;
import org.springframework.beans.factory.annotation.Autowired;
import ru.mexdev.application.mapper.FileResponseMapper;
import ru.mexdev.application.entity.FileResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.mexdev.application.repository.FileRepository;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;


@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Slf4j
public class FileStorageServiceImpl implements FileStorageService {
    @Autowired
    private FileRepository fileRepository;

    MinioService minioService;
    FileResponseMapper fileResponseMapper;

    @Override
    public FileResponse addFile(MultipartFile file) {
        Path path = Path.of(UUID.randomUUID().toString());
        /*Path path = Path.of(file.getOriginalFilename());*/
        System.out.println(file.getOriginalFilename());
        System.out.println(path);//Correct UUID
        try {
            minioService.upload(path, file.getInputStream(), file.getContentType());
            var metadata = minioService.getMetadata(path);

            //fileRepository.save(fileResponseMapper.fileAddResponse(metadata));
            //TODO
            //file.getName();
            FileResponse fileResponse = new FileResponse(UUID.fromString(path.toString()), file.getName(),
                    file.getContentType(), file.getSize(),
                    metadata.createdTime(), new InputStreamResource(file.getInputStream()));
            fileResponse.setUuid(UUID.fromString(path.toString()));
            System.out.println(fileResponse.getUuid());// Correct UUID
            fileRepository.save(fileResponse);//?? Maybe problem with save() ??

            log.info("this file {} storage in bucket: {} on date: {}", metadata.name(), metadata.bucketName(), metadata.createdTime());
            return fileResponseMapper.fileAddResponse(metadata);
        } catch (IOException | MinioException ex) {
            throw new IllegalStateException(ex.getMessage());
        }
    }

    @SneakyThrows
    @Override
    public List<FileResponse> readAll() { return fileRepository.findAll();  }//Returns some Hrenota. Wrong UUIDs

    @SneakyThrows
    @Override
    public void deleteFile(String uuid) {
        Path path = Path.of(uuid);
        var metadata = minioService.getMetadata(path);
        minioService.remove(path);
        log.info("this file {} removed in bucket: {} on date: {}", metadata.name(), metadata.bucketName(), metadata.createdTime());
    }

    @SneakyThrows
    @Override
    public FileResponse getFile(String uuid) {
        Path path = Path.of(uuid);
        var metadata = minioService.getMetadata(path);

        InputStream inputStream = minioService.get(path);
        InputStreamResource inputStreamResource = new InputStreamResource(inputStream);

        return FileResponse.builder()
                .filename(metadata.name())
                .fileSize(metadata.length())
                .contentType(metadata.contentType())
                .createdTime(metadata.createdTime())
                .stream(inputStreamResource)
                .build();
    }

    @SneakyThrows
    @Override
    public FileResponse getFileDetails(String uuid) {
        Path path = Path.of(uuid);
        var metadata = minioService.getMetadata(path);
        return fileResponseMapper.fileGetResponse(metadata);
    }


}
