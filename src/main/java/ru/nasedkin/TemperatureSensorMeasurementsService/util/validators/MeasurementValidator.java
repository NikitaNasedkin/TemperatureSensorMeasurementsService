package ru.nasedkin.TemperatureSensorMeasurementsService.util.validators;

import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.nasedkin.TemperatureSensorMeasurementsService.models.Measurement;
import ru.nasedkin.TemperatureSensorMeasurementsService.services.SensorsService;

@Component
public class MeasurementValidator implements Validator {

    private final SensorsService sensorsService;

    @Autowired
    public MeasurementValidator(SensorsService sensorsService) {
        this.sensorsService = sensorsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Measurement.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        Measurement measurement = (Measurement) target;

        if (measurement.getSensor() != null && sensorsService.isSensorExists(measurement.getSensor().getName()))
            return;

        errors.rejectValue("sensorDTO", "404", "This sensor not exists");
    }
}
