package ru.karpenko.practice.project3.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.karpenko.practice.project3.Services.SensorsService;
import ru.karpenko.practice.project3.dto.SensorDTO;
import ru.karpenko.practice.project3.util.ErrorsOut;
import ru.karpenko.practice.project3.util.NotCreatedException;
import ru.karpenko.practice.project3.util.SensorErrorResponse;
import ru.karpenko.practice.project3.util.SensorValidator;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/sensors")
public class SensorsController {
    private final SensorsService sensorsService;
    private final SensorValidator sensorValidator;

    @Autowired
    public SensorsController(SensorsService sensorsService, SensorValidator sensorValidator) {
        this.sensorsService = sensorsService;
        this.sensorValidator = sensorValidator;
    }

    @RequestMapping("/registration")
    @PostMapping
    public ResponseEntity<HttpStatus> registration(@RequestBody @Valid SensorDTO sensorDTO,
                                             BindingResult bindingResult){
        sensorValidator.validate(sensorsService.convertToSensor(sensorDTO), bindingResult);
        if (bindingResult.hasErrors()){
            ErrorsOut.returnErrorsToClient(bindingResult);
        }
        sensorsService.save(sensorDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException (NotCreatedException e){
        SensorErrorResponse sensorErrorResponse = new SensorErrorResponse(e.getMessage(),
                System.currentTimeMillis());
        return new ResponseEntity<>(sensorErrorResponse, HttpStatusCode.valueOf(400));
    }
}
