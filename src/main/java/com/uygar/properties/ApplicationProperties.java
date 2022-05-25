package com.uygar.properties;

import java.io.*;
import java.util.Properties;

public class ApplicationProperties extends Properties {

    public static final String PROPERTIES_PATH = "src/main/resources/com/uygar/application.properties";

    public ApplicationProperties() {
        try {
            this.load(new InputStreamReader(new FileInputStream(PROPERTIES_PATH)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getClientEndPoint() {
        return this.getProperty("remoteEndPoint")+":"+this.getProperty("remotePort");
    }

    public void putProperty(String propName, String propValue) {
        this.put(propName, propValue);
        try {
            this.store(new OutputStreamWriter(new FileOutputStream(PROPERTIES_PATH)), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}