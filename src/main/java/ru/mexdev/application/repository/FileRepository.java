package ru.mexdev.application.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mexdev.application.entity.FileResponse;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FileRepository extends JpaRepository<FileResponse, UUID> {
    boolean existsByFilename(String filename);
    @Override
    Optional<FileResponse> findById(UUID uuid);

}
