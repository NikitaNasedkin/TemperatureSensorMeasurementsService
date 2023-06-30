package ru.nasedkin.TemperatureSensorMeasurementsService.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.nasedkin.TemperatureSensorMeasurementsService.dto.MeasurementDTO;
import ru.nasedkin.TemperatureSensorMeasurementsService.models.Measurement;
import ru.nasedkin.TemperatureSensorMeasurementsService.models.Sensor;
import ru.nasedkin.TemperatureSensorMeasurementsService.services.MeasurementsService;
import ru.nasedkin.TemperatureSensorMeasurementsService.util.ErrorResponse;
import ru.nasedkin.TemperatureSensorMeasurementsService.util.MeasurementNotAddedException;
import ru.nasedkin.TemperatureSensorMeasurementsService.util.validators.MeasurementValidator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/measurements")
public class MeasurementsController {

    private final ModelMapper modelMapper;
    private final MeasurementValidator measurementValidator;

    private final MeasurementsService measurementsService;

    @Autowired
    public MeasurementsController(ModelMapper modelMapper, MeasurementValidator measurementValidator, MeasurementsService measurementsService) {
        this.modelMapper = modelMapper;
        this.measurementValidator = measurementValidator;
        this.measurementsService = measurementsService;
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@RequestBody @Valid MeasurementDTO measurementDTO,
                                          BindingResult bindingResult) {
        Measurement measurement = convertToMeasurement(measurementDTO);

        measurementValidator.validate(measurement,
                bindingResult);

        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();

            for (FieldError error : errors) {
                errorMessage.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }
            throw new MeasurementNotAddedException(errorMessage.toString());
        }

        measurementsService.addMeasurement(measurement);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping()
    public List<MeasurementDTO> getAll(){
        return measurementsService.findAll().stream()
                .map(this::convertToMeasurementDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/rainyDaysCount")
    private List<MeasurementDTO> getRaining(){
        return measurementsService.findRainy().stream()
                .map(this::convertToMeasurementDTO)
                .collect(Collectors.toList());
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(MeasurementNotAddedException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage(), LocalDateTime.now()),
                HttpStatus.CONFLICT);
    }

    private Measurement convertToMeasurement(MeasurementDTO measurementDTO) {
        return modelMapper.map(measurementDTO, Measurement.class);
    }

    private MeasurementDTO convertToMeasurementDTO(Measurement measurement){
        return modelMapper.map(measurement, MeasurementDTO.class);
    }
}
