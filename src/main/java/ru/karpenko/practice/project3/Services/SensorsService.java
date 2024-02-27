package ru.karpenko.practice.project3.Services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.karpenko.practice.project3.dto.SensorDTO;
import ru.karpenko.practice.project3.models.Sensor;
import ru.karpenko.practice.project3.repositories.SensorsRepository;

import java.util.Optional;

@Service
public class SensorsService {
    private final SensorsRepository sensorsRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public SensorsService(SensorsRepository sensorsRepository, ModelMapper modelMapper) {
        this.sensorsRepository = sensorsRepository;
        this.modelMapper = modelMapper;
    }

    public void save(SensorDTO sensorDTO){
        sensorsRepository.save(convertToSensor(sensorDTO));
    }
    public Optional<Sensor> showByName(String name){
        return sensorsRepository.findByName(name);
    }
    public Sensor convertToSensor(SensorDTO sensorDTO) {
        return modelMapper.map(sensorDTO, Sensor.class);
    }

}
