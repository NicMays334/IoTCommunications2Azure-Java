package com.krakn.iotdevicesimulation;

import com.microsoft.azure.sdk.iot.device.IotHubEventCallback;
import com.microsoft.azure.sdk.iot.device.IotHubStatusCode;

class EventCallback implements IotHubEventCallback {
    public void execute(IotHubStatusCode status, Object context) {
        System.out.println("Responded with "+status.name()+"\n");

        if (context != null) {
            synchronized (context) {
                context.notify();
            }
        }
    }
}