package ru.karpenko.practice.project3.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.karpenko.practice.project3.Services.SensorsService;
import ru.karpenko.practice.project3.dto.MeasurementDTO;

@Component
public class MeasurementDTOValidator implements Validator {
    private final SensorsService sensorsService;

    @Autowired
    public MeasurementDTOValidator(SensorsService sensorsService) {
        this.sensorsService = sensorsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return MeasurementDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MeasurementDTO measurementDTO = (MeasurementDTO) target;
        if(sensorsService.showByName(measurementDTO.getSensor().getName()).isEmpty()){
            errors.rejectValue("sensor", "", "The sensor is not registered in the system");
        }
    }
}
