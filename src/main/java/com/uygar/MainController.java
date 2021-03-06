package com.uygar;

import com.uygar.model.Device;
import com.uygar.model.Sensor;
import com.uygar.repo.mysql.DeviceMySqlRepository;
import com.uygar.server.DeviceDto;
import com.uygar.server.SensorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"*", "http://127.0.0.1:8000"})
@Controller
@RequestMapping("/api")
public class MainController {

    DeviceMySqlRepository deviceRepository = new DeviceMySqlRepository();

    @PostMapping("createDevice/{userId}")
    public void createNewDevice(@RequestBody DeviceDto deviceDto, @PathVariable int userId) {
        deviceRepository.createDevice(new Device(deviceDto.getUuid(), deviceDto.getName(), deviceDto.getType(),
                deviceDto.getSensors().stream().map(sensorDto -> new Sensor(sensorDto.getType(), sensorDto.getValue())).collect(Collectors.toList()), userId), userId);
    }

    @PostMapping("createSensor/{deviceUuid}")
    public String createNewSensor(@RequestBody SensorDto sensorDto, @PathVariable String deviceUuid) {
        deviceRepository.createSensor(new Sensor(sensorDto.getType(), sensorDto.getValue()), deviceUuid);
        return "";
    }

    @GetMapping("fetchAllSensors/user/{userId}/device/{deviceName}")
    public ResponseEntity<?> getAllSensors(@PathVariable int userId, @PathVariable String deviceName, @RequestParam String secret) {
        if (secret.contains("THIS_IS_A_SECRET")) {
            if (userId > 0) {
                List<SensorDto> devices = deviceRepository.getDeviceSensors(userId, "name", deviceName).stream().map(sensor ->
                        new SensorDto(sensor.getType(), sensor.getValue())).collect(Collectors.toList());
                return ResponseEntity.ok(devices);
            }
        }
        return ResponseEntity.ok("");
    }

    @GetMapping("/fetchAllDevices/{userId}")
    public ResponseEntity<?> getAllDevices(@PathVariable int userId, @RequestParam String secret) {
        if (secret.contains("THIS_IS_A_SECRET")) {
            if (userId > 0) {
                List<DeviceDto> devices = deviceRepository.getDevices(userId).stream().map(device -> {
                    List<SensorDto> sensors = device.getSensors().stream().map(sensor -> new SensorDto(sensor.getType(), sensor.getValue())).collect(Collectors.toList());
                    return new DeviceDto(device.getUuid(), device.getName(), device.getType(), sensors, userId);

                }).collect(Collectors.toList());
                return ResponseEntity.ok(devices);
            }
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }

}

