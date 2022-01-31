package ru.mexdev.application.mapper;

import ru.mexdev.application.decarator.FileResponseDecorator;
import ru.mexdev.application.entity.FileResponse;
import io.minio.ObjectStat;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
@DecoratedWith(FileResponseDecorator.class)
public interface FileResponseMapper {

    @Mapping(target = "stream", ignore = true)
    @Mapping(target = "filename", ignore = true)
    @Mapping(target = "fileSize", ignore = true)
    @Mapping(target = "createdTime", ignore = true)
    @Mapping(target = "contentType", ignore = true)
    FileResponse fileAddResponse(ObjectStat objectStat);


    @Mapping(target = "stream", ignore = true)
    @Mapping(target = "filename", ignore = true)
    @Mapping(target = "fileSize", ignore = true)
    @Mapping(target = "createdTime", ignore = true)
    @Mapping(target = "contentType", ignore = true)
    FileResponse fileGetResponse(ObjectStat objectStat);
}
