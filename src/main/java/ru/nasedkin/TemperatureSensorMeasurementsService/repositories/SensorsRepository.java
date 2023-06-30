package ru.nasedkin.TemperatureSensorMeasurementsService.repositories;

import ru.nasedkin.TemperatureSensorMeasurementsService.models.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SensorsRepository extends JpaRepository<Sensor, Integer> {
    Optional<Sensor> findByName(java.lang.String name);
}
