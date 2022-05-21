package com.uygar;

import com.uygar.server.CredentialsDto;
import com.uygar.server.DeviceDto;
import com.uygar.server.SensorDto;
import com.uygar.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://127.0.0.1:8000"})
@Controller
@RequestMapping("/api")
public class MainController {

    @GetMapping("fetchAllSensors/user/{userId}/device/{deviceName}")
    public ResponseEntity<?> getAllSensors(@PathVariable int userId, @PathVariable String deviceName, @RequestParam String secret) {
        if (secret.contains("THIS_IS_A_SECRET")) {
            if (userId > 0) {
                List<SensorDto> devices = App.deviceRepository.getDeviceSensors(userId,"name", deviceName).stream().map(sensor ->
                    new SensorDto(sensor.getType(), sensor.getValue())).collect(Collectors.toList());
                return ResponseEntity.ok(devices);
            }
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }

    @GetMapping("/fetchAllDevices/{userId}")
    public ResponseEntity<?> getAllDevices(@PathVariable int userId, @RequestParam String secret) {
        if (secret.contains("THIS_IS_A_SECRET")) {
            if (userId > 0) {
                List<DeviceDto> devices = App.deviceRepository.getDevices(userId).stream().map(device -> {
                    List<SensorDto> sensors = device.getSensors().stream().map(sensor -> new SensorDto(sensor.getType(), sensor.getValue())).collect(Collectors.toList());
                    return new DeviceDto(device.getUuid(), device.getName(), device.getType(), sensors, userId);

                }).collect(Collectors.toList());
                return ResponseEntity.ok(devices);
            }
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }

}

