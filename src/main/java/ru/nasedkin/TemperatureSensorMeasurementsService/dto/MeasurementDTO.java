package ru.nasedkin.TemperatureSensorMeasurementsService.dto;

import jakarta.validation.constraints.*;

public class MeasurementDTO {


    @Min(-100)
    @Max(100)
    private float value;

    private boolean raining;

    private SensorDTO sensor;

    public SensorDTO getSensor() {
        return sensor;
    }

    public void setSensor(SensorDTO sensor) {
        this.sensor = sensor;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public boolean isRaining() {
        return raining;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }
}
