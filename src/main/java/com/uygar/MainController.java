package com.uygar;

import com.uygar.model.Sensor;
import com.uygar.server.CredentialsDto;
import com.uygar.server.DeviceDto;
import com.uygar.server.SensorDto;
import com.uygar.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping
public class MainController {

    private UserService service = new UserService();

    @GetMapping("/getAllDevices")
    public ResponseEntity<?> getAllDevices(@RequestBody CredentialsDto credentialsDto) {
        int userIdOnCredentials = service.getUserIdOnCredentials(credentialsDto.getUsername(), credentialsDto.getPassword());
        if (userIdOnCredentials > 0) {
            List<DeviceDto> devices = App.deviceRepository.getDevices(userIdOnCredentials).stream().map(device -> {
                List<SensorDto> sensors = device.getSensors().stream().map(sensor -> new SensorDto(sensor.getType(), sensor.getValue())).collect(Collectors.toList());
                return new DeviceDto(device.getUuid(), device.getName(), device.getType(), sensors, userIdOnCredentials);

            }).collect(Collectors.toList());
            return ResponseEntity.ok(devices);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }

}

