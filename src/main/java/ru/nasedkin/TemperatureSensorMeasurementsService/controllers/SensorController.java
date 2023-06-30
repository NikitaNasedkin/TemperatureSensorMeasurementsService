package ru.nasedkin.TemperatureSensorMeasurementsService.controllers;

import jakarta.validation.Valid;
import ru.nasedkin.TemperatureSensorMeasurementsService.dto.SensorDTO;
import ru.nasedkin.TemperatureSensorMeasurementsService.models.Sensor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.nasedkin.TemperatureSensorMeasurementsService.services.SensorsService;
import ru.nasedkin.TemperatureSensorMeasurementsService.util.SensorNotRegisteredException;
import ru.nasedkin.TemperatureSensorMeasurementsService.util.ErrorResponse;
import ru.nasedkin.TemperatureSensorMeasurementsService.util.validators.SensorValidator;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/sensors")
public class SensorController {

    private final ModelMapper modelMapper;
    private final SensorsService sensorsService;

    private final SensorValidator sensorValidator;

    @Autowired
    public SensorController(ModelMapper modelMapper, SensorsService sensorsService, SensorValidator sensorValidator) {
        this.modelMapper = modelMapper;
        this.sensorsService = sensorsService;
        this.sensorValidator = sensorValidator;
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> addSensor(@RequestBody @Valid SensorDTO sensorDTO,
                                                BindingResult bindingResult) {
        Sensor sensor = convertToSensor(sensorDTO);

        sensorValidator.validate(sensor, bindingResult);

        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();

            for (FieldError error : errors) {
                errorMessage.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }
            throw new SensorNotRegisteredException(errorMessage.toString());
        }

        sensorsService.addSensor(sensor);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(SensorNotRegisteredException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage(), LocalDateTime.now()),
                HttpStatus.CONFLICT);
    }


    private Sensor convertToSensor(SensorDTO sensorDTO) {
        return modelMapper.map(sensorDTO, Sensor.class);
    }

    private SensorDTO convertToSensorDTO(Sensor sensor) {
        return modelMapper.map(sensor, SensorDTO.class);
    }
}
