package ru.kurbanov.presentation.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.kurbanov.domain.entities.details.Detail;
import ru.kurbanov.presentation.dto.requests.DetailRequestDto;
import ru.kurbanov.presentation.dto.responses.DetailResponseDto;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface DetailDtoMapper {

    DetailResponseDto toDto(Detail detail);

    @Mapping(target = "id", source = "id")
    Detail toDomain(UUID id, DetailRequestDto detailRequestDto);
}
