package com.uygar.model;

public enum DeviceType {

    PC("pc"), ARDUINO("arduino"), PHONE("phone");

    private String name;

    DeviceType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static DeviceType fromName(String name) {
        DeviceType[] values = values();
        for (DeviceType deviceType : values)
            if (deviceType.name.equalsIgnoreCase(name))
                return deviceType;
        return null;
    }

}

