package ru.karpenko.practice.project3.Services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.karpenko.practice.project3.dto.MeasurementDTO;
import ru.karpenko.practice.project3.models.Measurement;
import ru.karpenko.practice.project3.models.Sensor;
import ru.karpenko.practice.project3.repositories.MeasurementsRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MeasurementsService {
    private final MeasurementsRepository measurementsRepository;
    private final SensorsService sensorsService;
    private final ModelMapper modelMapper;

    @Autowired
    public MeasurementsService(MeasurementsRepository measurementsRepository,SensorsService sensorsService, ModelMapper modelMapper) {
        this.measurementsRepository = measurementsRepository;
        this.sensorsService = sensorsService;
        this.modelMapper = modelMapper;
    }

    public List<Measurement> findAll(){
        return measurementsRepository.findAll();
    }

    @Transactional
    public void save(MeasurementDTO measurementDTO){
        measurementDTO.setSensor(sensorsService.showByName(measurementDTO.getSensor().getName()).orElse(null));
        measurementsRepository.save(convertToMeasurement(measurementDTO));

    }

    public Measurement convertToMeasurement(MeasurementDTO measurementDTO) {
        Measurement measurement = modelMapper.map(measurementDTO, Measurement.class);
        enrichMeasurement(measurement);
        return measurement;
    }

    private void enrichMeasurement(Measurement measurement){
        measurement.setMeasurementTime(LocalDateTime.now());
    }
}
