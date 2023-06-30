package ru.nasedkin.TemperatureSensorMeasurementsService.services;

import ru.nasedkin.TemperatureSensorMeasurementsService.models.Sensor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nasedkin.TemperatureSensorMeasurementsService.repositories.SensorsRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class SensorsService {

    private final SensorsRepository sensorsRepository;

    @Autowired
    public SensorsService(SensorsRepository sensorsRepository) {
        this.sensorsRepository = sensorsRepository;
    }

    @Transactional
    public void addSensor(Sensor sensor) {
        sensorsRepository.save(sensor);
    }

    public List<Sensor> findAll() {
        return sensorsRepository.findAll();
    }

    public boolean isSensorExists(String name) {
        if (name.isEmpty())
            return false;
        return sensorsRepository.findByName(name).isPresent();
    }
}
