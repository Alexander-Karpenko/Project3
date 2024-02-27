package ru.karpenko.practice.project3.util;


import net.minidev.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.karpenko.practice.project3.dto.SensorDTO;

import java.text.DecimalFormat;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertNotNull;


public class MeasurementControllerTest {
    private static final String addSensorUrl = "http://localhost:8080/sensors/registration";
    private static final String addMeasurementUrl = "http://localhost:8080/measurements/add";
    private static final String getMeasurementUrl = "http://localhost:8080/measurements";
//    @BeforeAll
//    static void beforeClass(){
//    }
    @Test
    public void test(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        JSONObject sensorJsonObject = new JSONObject();
        sensorJsonObject.put("name", "Test3");

        HttpEntity<String> request = new HttpEntity<String>(sensorJsonObject.toString(), headers);

        ResponseEntity<String> responseEntityStr = restTemplate.
                postForEntity(addSensorUrl, request, String.class);
        System.out.println(responseEntityStr.getBody());
            assertNotNull(responseEntityStr.getBody());

        // производим 1000 запросов на отправку измерений
        Random rand = new Random();
        for (int i = 0; i < 1000; i++) {
            JSONObject measurementJsonObject = new JSONObject();
            String temperature = new DecimalFormat("#.#").format(rand.nextDouble(70) - 30);
            temperature = temperature.replace(",", ".");
            measurementJsonObject.put("temperature", temperature );
            measurementJsonObject.put("raining", rand.nextBoolean());
            SensorDTO sensor = new SensorDTO();
            sensor.setName(sensorJsonObject.getAsString("name"));
            measurementJsonObject.put("sensor", sensor);

            HttpEntity<String> addMeasurementRequest = new HttpEntity<String>(measurementJsonObject.toString(), headers);
            ResponseEntity<String> measurementResponseEntityStr = restTemplate.
                    postForEntity(addMeasurementUrl, addMeasurementRequest, String.class);
            System.out.println(measurementResponseEntityStr.getBody());
        }

        // получение всех измерений
        ResponseEntity<String> getMeasurementResponseEntity = restTemplate.
                getForEntity(getMeasurementUrl, String.class);
        System.out.println(getMeasurementResponseEntity.getBody());
        }

}
