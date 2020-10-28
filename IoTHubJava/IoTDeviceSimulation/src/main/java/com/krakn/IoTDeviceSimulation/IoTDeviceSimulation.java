package com.krakn.iotdevicesimulation;

import java.io.IOException;
import java.net.URISyntaxException;

public class IoTDeviceSimulation{
    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {

        int delayMS = 1000;
        int iterateNTimes = 100;
        boolean delta = true;
        String connString = "";

        IoTDeviceSim device = new IoTDeviceSim(connString);
        device.teleData.setDataName("temperature");
        device.teleData.setDataValue(30.0);
        device.sendIoTTelemetryData(delta, iterateNTimes, delayMS);
    }

}
