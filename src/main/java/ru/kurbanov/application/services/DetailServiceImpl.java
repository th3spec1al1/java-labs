package ru.kurbanov.application.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kurbanov.application.abstractions.repositories.jpa.JpaDetailRepository;
import ru.kurbanov.application.contracts.DetailService;
import ru.kurbanov.domain.entities.details.Detail;
import ru.kurbanov.infrastructure.persistence.jpa.model.DetailEntity;
import ru.kurbanov.infrastructure.persistence.mappers.DetailEntityMapper;
import ru.kurbanov.presentation.dto.requests.DetailRequestDto;
import ru.kurbanov.presentation.dto.responses.DetailResponseDto;
import ru.kurbanov.presentation.mappers.DetailDtoMapper;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class DetailServiceImpl implements DetailService {

    private final JpaDetailRepository detailRepository;
    private final DetailEntityMapper detailEntityMapper;
    private final DetailDtoMapper detailDtoMapper;

    @Override
    public DetailResponseDto getDetail(UUID detailId) {
        DetailEntity detailEntity = detailRepository.findById(detailId)
                .orElseThrow(() -> new EntityNotFoundException("Detail not found: " + detailId));
        Detail detail = detailEntityMapper.toDomain(detailEntity);
        return detailDtoMapper.toDto(detail);
    }

    @Override
    public List<DetailResponseDto> getDetails() {
        return detailRepository.findAll().stream()
                .map(detailEntityMapper::toDomain)
                .map(detailDtoMapper::toDto)
                .toList();
    }

    @Override
    public DetailResponseDto addDetail(DetailRequestDto detailRequestDto) {
        Detail detail = detailDtoMapper.toDomain(UUID.randomUUID(), detailRequestDto);
        DetailEntity detailEntity = detailEntityMapper.toEntity(detail);
        DetailEntity saved = detailRepository.save(detailEntity);
        Detail savedDetail = detailEntityMapper.toDomain(saved);
        return detailDtoMapper.toDto(savedDetail);
    }

    @Override
    public DetailResponseDto updateDetail(UUID detailId, DetailRequestDto detailRequestDto) {
        DetailEntity detailEntity = detailRepository.findById(detailId)
                .orElseThrow(() -> new EntityNotFoundException("Detail not found: " + detailId));
        detailEntity.setName(detailRequestDto.getName());
        detailEntity.setType(detailRequestDto.getType());
        detailEntity.setPrice(detailRequestDto.getPrice());
        detailEntity.setCompatibleModels(detailRequestDto.getCompatibleModels());
        Detail detail = detailEntityMapper.toDomain(detailEntity);
        return detailDtoMapper.toDto(detail);
    }

    @Override
    public void removeDetail(UUID detailId) {
        if (!detailRepository.existsById(detailId)) {
            throw new EntityNotFoundException("Detail not found: " + detailId);
        }

        detailRepository.deleteById(detailId);
    }
}
