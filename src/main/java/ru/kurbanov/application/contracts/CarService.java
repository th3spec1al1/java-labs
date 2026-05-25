package ru.kurbanov.application.contracts;

import ru.kurbanov.presentation.dto.requests.CarConfigureRequestDto;
import ru.kurbanov.presentation.dto.requests.CarFilterRequestDto;
import ru.kurbanov.presentation.dto.requests.CarRequestDto;
import ru.kurbanov.presentation.dto.responses.CarResponseDto;

import java.util.List;
import java.util.UUID;

public interface CarService {

    CarResponseDto getCar(UUID carId);

    List<CarResponseDto> getCars(CarFilterRequestDto carFilterRequestDto);

    CarResponseDto addCar(CarRequestDto carRequestDto);

    CarResponseDto updateCar(UUID carId, CarRequestDto carRequestDto);

    void removeCar(UUID carId);

    CarResponseDto configureCar(UUID carId, CarConfigureRequestDto carConfigureRequestDto);
}
