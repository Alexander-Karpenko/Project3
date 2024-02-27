package ru.karpenko.practice.project3.dto;

import jakarta.validation.constraints.*;
import ru.karpenko.practice.project3.models.Sensor;

import java.math.BigDecimal;
import java.util.List;


public class MeasurementDTO {
    @NotNull(message = "temperature should not be empty")
    @DecimalMin(value = "-100", message = "temperature should be greater than -100")
    @DecimalMax(value = "100", message = "temperature should be less than 100")
    private BigDecimal temperature;

    @NotNull(message = "raining should not be empty")
    private Boolean raining;

    @NotNull(message = "sensor should not be empty")
    private Sensor sensor;

    public BigDecimal getTemperature() {
        return temperature;
    }

    public void setTemperature(BigDecimal temperature) {
        this.temperature = temperature;
    }

    public Boolean isRaining() {
        return raining;
    }

    public void setRaining(Boolean raining) {
        this.raining = raining;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    @Override
    public String toString() {
        return "MeasurementDTO{" +
                "temperature=" + temperature +
                ", raining=" + raining +
                ", sensor=" + sensor +
                '}';
    }
}
