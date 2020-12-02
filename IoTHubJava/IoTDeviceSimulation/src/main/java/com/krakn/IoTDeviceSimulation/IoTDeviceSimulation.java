package com.krakn.IoTDeviceSimulation;

import java.io.IOException;
import java.net.URISyntaxException;

public class IoTDeviceSimulation{
    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {

        String connString = args[0];
        int delayMS = Integer.parseInt(args[1]);
        int iterateNTimes = Integer.parseInt(args[2]);
        
        //tweak the telemtry values as they send. 
        boolean delta = true;

        IoTDeviceSim device = new IoTDeviceSim(connString);
        //device.teleData.setDataName("temperature");
        //device.teleData.setDataValue(30.0);
        System.out.println("\nCommunicating from device: "+device.toString());
        device.sendIoTTelemetryData(delta, iterateNTimes, delayMS);
        System.out.println("Done");
    }

}
