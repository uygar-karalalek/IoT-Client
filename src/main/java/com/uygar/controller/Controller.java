package com.uygar.controller;

import com.uygar.Application;
import com.uygar.properties.ApplicationProperties;
import com.uygar.repo.DeviceRepository;
import com.uygar.repo.DeviceSensorRepository;

public abstract class Controller {

    private Application parentApplication;
    private DeviceRepository deviceRepository;
    private DeviceSensorRepository deviceSensorRepository;

    public void setParentApplication(Application parentApplication) {
        this.parentApplication = parentApplication;
    }

    public Application getParentApplication() {
        return parentApplication;
    }

    public ApplicationProperties appProperties() {
        return getParentApplication().getAppProps();
    }

    public DeviceRepository deviceRepository() {
        return parentApplication.getDeviceRepository();
    }

    public DeviceSensorRepository getDeviceSensorMySqlRepository() {
        return getParentApplication().getDeviceSensorRepository();
    }

    public int getUserId() {
        return getParentApplication().getUserId();
    }
}
