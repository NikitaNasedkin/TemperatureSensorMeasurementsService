package ru.nasedkin.TemperatureSensorMeasurementsService.repositories;

import ru.nasedkin.TemperatureSensorMeasurementsService.models.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeasurementsRepository extends JpaRepository<Measurement, Integer> {
    List<Measurement> findMeasurementsByRainingIsTrue();

}
