package ru.karpenko.practice.project3.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Component
@Entity
@Table(name = "measurement")
public class Measurement {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "sensor_id", referencedColumnName = "id")
    private Sensor sensor;

    @Column(name = "sensor_name")
    private String sensorName;

    @Column(name = "temperature")
    @NotNull(message = "temperature should not be empty")
    @DecimalMin(value = "-100", message = "temperature should be greater than -100")
    @DecimalMax(value = "100", message = "temperature should be less than 100")
    private BigDecimal temperature;

    @NotNull(message = "raining should not be empty")
    @Column(name = "raining")
    private Boolean raining;



    @Column(name = "measurement_time")
    private LocalDateTime measurementTime;

    public Measurement(Sensor sensor, String sensorName, BigDecimal temperature, Boolean raining, LocalDateTime measurementTime) {
        this.sensor = sensor;
        this.sensorName = sensorName;
        this.temperature = temperature;
        this.raining = raining;
        this.measurementTime = measurementTime;
    }

    public Measurement() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

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

    public LocalDateTime getMeasurementTime() {
        return measurementTime;
    }

    public void setMeasurementTime(LocalDateTime measurementTime) {
        this.measurementTime = measurementTime;
    }

    @Override
    public String toString() {
        return "Measurement{" +
                "id=" + id +
                ", sensor=" + sensor +
                ", sensorName='" + sensorName + '\'' +
                ", temperature=" + temperature +
                ", raining=" + raining +
                ", measurementTime=" + measurementTime +
                '}';
    }
}
