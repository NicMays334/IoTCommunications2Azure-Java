package com.krakn.IoTDeviceSimulation;

import com.google.gson.Gson;

public class TelemetryDataPoint {

    private String deviceId;
    private String name;
    private double value;

    public TelemetryDataPoint(String deviceId, String name, double value) {
        this.deviceId = deviceId;
        this.name = name;
        this.value = value;
    }

    public void setDataName(String name) {
        this.name = name;
    }

    public void setDataValue(double value) {
        this.value = value;
    }

    public String getDataName() {
        return this.name;
    }

    public double getDataValue() {
        return (Math.round(this.value * 100.0) / 100.0);
    }

    public String serialize() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

}
