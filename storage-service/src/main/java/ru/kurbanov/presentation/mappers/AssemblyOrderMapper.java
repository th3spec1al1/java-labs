package ru.kurbanov.presentation.mappers;

import org.mapstruct.Mapper;
import ru.kurbanov.infrastructure.persistence.jpa.model.AssemblyOrderEntity;
import ru.kurbanov.presentation.dto.requests.AssemblyOrderRequestDto;
import ru.kurbanov.presentation.dto.responses.AssemblyOrderResponseDto;

@Mapper(componentModel = "spring")
public interface AssemblyOrderMapper {

    AssemblyOrderEntity toEntity(AssemblyOrderRequestDto dto);

    AssemblyOrderResponseDto toDto(AssemblyOrderEntity entity);
}