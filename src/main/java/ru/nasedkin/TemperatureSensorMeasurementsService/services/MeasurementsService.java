package ru.nasedkin.TemperatureSensorMeasurementsService.services;

import ru.nasedkin.TemperatureSensorMeasurementsService.models.Measurement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nasedkin.TemperatureSensorMeasurementsService.repositories.MeasurementsRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MeasurementsService {

    private final MeasurementsRepository measurementsRepository;

    @Autowired
    public MeasurementsService(MeasurementsRepository measurementsRepository) {
        this.measurementsRepository = measurementsRepository;
    }

    public List<Measurement> findAll() {
        return measurementsRepository.findAll();
    }

    public List<Measurement> findRainy() {
        return measurementsRepository.findMeasurementsByRainingIsTrue();
    }

    @Transactional
    public void addMeasurement(Measurement measurement){
        measurement.setAddedAt(LocalDateTime.now());
        measurementsRepository.save(measurement);
    }
}
