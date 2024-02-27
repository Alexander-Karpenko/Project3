package ru.karpenko.practice.project3.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.karpenko.practice.project3.Services.MeasurementsService;
import ru.karpenko.practice.project3.dto.MeasurementDTO;
import ru.karpenko.practice.project3.models.Measurement;
import ru.karpenko.practice.project3.util.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/measurements")
public class MeasurementsController {
    private final MeasurementsService measurementsService;
    private final MeasurementDTOValidator measurementsDTOValidation;
    private final ModelMapper modelMapper;

    @Autowired
    public MeasurementsController(MeasurementsService measurementsService, MeasurementDTOValidator measurementsDTOValidation, ModelMapper modelMapper) {
        this.measurementsService = measurementsService;
        this.measurementsDTOValidation = measurementsDTOValidation;
        this.modelMapper = modelMapper;
    }

    @RequestMapping("/add")
    @PostMapping
    public ResponseEntity<HttpStatus> add(@RequestBody @Valid MeasurementDTO measurementDTO,
                                                   BindingResult bindingResult){
        measurementsDTOValidation.validate(measurementDTO, bindingResult);
        if (bindingResult.hasErrors()){
            ErrorsOut.returnErrorsToClient(bindingResult);
        }
        measurementsService.save(measurementDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @GetMapping
    public List<MeasurementDTO> showMeasurements(){
        return measurementsService.findAll().stream().map(this::convertToMeasurementDTO)
                .collect(Collectors.toList());

    }
    @RequestMapping("/rainyDaysCount")
    @GetMapping
    public int rainyDaysCount(){
        return (int) measurementsService.findAll().stream().filter(Measurement::isRaining).count();
    }

    public MeasurementDTO convertToMeasurementDTO(Measurement measurement){
        return modelMapper.map(measurement, MeasurementDTO.class);
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> handleException (NotCreatedException e){
        MeasurementErrorResponse measurementErrorResponse = new MeasurementErrorResponse(e.getMessage(),
                System.currentTimeMillis());
        return new ResponseEntity<>(measurementErrorResponse, HttpStatusCode.valueOf(400));
    }


}
