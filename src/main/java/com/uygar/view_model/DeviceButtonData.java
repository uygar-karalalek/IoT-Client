package com.uygar.view_model;

import com.uygar.model.observable.ObservableDevice;

public class DeviceButtonData {
    public ObservableDevice device;
    public boolean selected;

    public DeviceButtonData(ObservableDevice device, boolean selected) {
        this.device = device;
        this.selected = selected;
    }
}
