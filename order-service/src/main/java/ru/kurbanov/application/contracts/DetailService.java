package ru.kurbanov.application.contracts;

import ru.kurbanov.presentation.dto.requests.DetailRequestDto;
import ru.kurbanov.presentation.dto.responses.DetailResponseDto;

import java.util.List;
import java.util.UUID;

public interface DetailService {

    DetailResponseDto getDetail(UUID detailId);

    List<DetailResponseDto> getDetails();

    DetailResponseDto addDetail(DetailRequestDto detailRequestDto);

    DetailResponseDto updateDetail(UUID detailId, DetailRequestDto detailRequestDto);

    void removeDetail(UUID detailId);
}
