package ru.nasedkin.TemperatureSensorMeasurementsService;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.nasedkin.TemperatureSensorMeasurementsService.dto.MeasurementDTO;
import ru.nasedkin.TemperatureSensorMeasurementsService.dto.SensorDTO;
import ru.nasedkin.TemperatureSensorMeasurementsService.models.Measurement;

@SpringBootApplication
public class TemperatureSensorMeasurementsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TemperatureSensorMeasurementsServiceApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
//        modelMapper
//                .createTypeMap(Measurement.class, MeasurementDTO.class)
//                .addMappings(mapper -> mapper.map(
//                        src -> modelMapper.map(src.getSensor(), SensorDTO.class), MeasurementDTO::setSensorDTO));
        return modelMapper;
    }
}
