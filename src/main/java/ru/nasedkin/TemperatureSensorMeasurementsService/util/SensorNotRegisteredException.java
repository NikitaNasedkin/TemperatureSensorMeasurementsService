package ru.nasedkin.TemperatureSensorMeasurementsService.util;

public class SensorNotRegisteredException extends RuntimeException {

    public SensorNotRegisteredException(String errMsg) {
        super(errMsg);
    }
}
