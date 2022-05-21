package com.uygar.server;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;

@JsonSerialize
public class DeviceDto {

    @JsonProperty
    private String uuid;
    @JsonProperty
    private String name;
    @JsonProperty
    private String type;
    @JsonProperty
    private List<SensorDto> sensors;
    @JsonProperty
    private int userId;

    public DeviceDto(String uuid, String name, String type, List<SensorDto> sensors, int userId) {
        this.uuid = uuid;
        this.name = name;
        this.type = type;
        this.sensors = sensors;
        this.userId = userId;
    }

    public DeviceDto() {
    }

    public String getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public List<SensorDto> getSensors() {
        return sensors;
    }

    public int getUserId() {
        return userId;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSensors(List<SensorDto> sensors) {
        this.sensors = sensors;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
