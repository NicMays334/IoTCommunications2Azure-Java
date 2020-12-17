package com.krakn.IoTHubJava;
import com.microsoft.azure.sdk.iot.device.*;

import java.io.IOException;
import java.net.URISyntaxException;
import com.google.gson.Gson;

public class JMeterIoTDevice {

    private String connectionString;
    private String deviceId;
    private String hostname;
    private IotHubClientProtocol messagingProtocol;
    private DeviceClient client;
    private Message message;
    private EventCallback callback;
    private Object lock;

    public static final IotHubClientProtocol Protocol_MQTT = IotHubClientProtocol.MQTT;
    public static final IotHubClientProtocol Protocol_HTTPS = IotHubClientProtocol.HTTPS;
    public static final IotHubClientProtocol Protocol_AMPQS = IotHubClientProtocol.AMQPS;


    //intended function signature is:
    //public JMeterIotDevice(String connectionString, IoTMessageProtocol)
    //note to Nic from past Nic
    //wrap IotHubClientProtocol as JMeter Protocol so you can use above static variables to set message :)
    public JMeterIoTDevice(String connectionString, IotHubClientProtocol messagingProtocol) throws URISyntaxException {
        
        this.connectionString = connectionString;
        
        this.hostname = connectionString.split(";")[0].split("=")[1];
        this.deviceId = connectionString.split(";")[1].split("=")[1];
        
        this.messagingProtocol = messagingProtocol;
        this.client  = new DeviceClient(this.connectionString, this.messagingProtocol);

        DeviceData metaData = new DeviceData(this.deviceId, this.hostname);
        this.message = new Message(metaData.serialize());

        this.callback = new EventCallback();
        this.lock = new Object();
    }

    public void openClient() throws IOException {
        this.client.open();
    }

    public void closeClient() throws IOException {
        this.client.closeNow();
    }

    public String getDeviceID()
    {
        return (this.deviceId);
    }

    public String getHostname() {
        return (this.hostname);
    }

    public void setMessageProperty(String key, String value){
        this.message.setProperty(key, value);
    }


    public String messageIotHub() throws InterruptedException {
        this.client.sendEventAsync(this.message, this.callback, this.lock);
        synchronized (lock) {
            lock.wait();
            return (this.callback.statusCode);
        }
        
    }
}

class EventCallback implements IotHubEventCallback {

    public String statusCode;
    
    
    public void execute(IotHubStatusCode status, Object context) {
        
            //System.out.println("Responded with "+status.name()+"\n");    
        this.statusCode = status.name().toString();

        if (context != null) {
            synchronized (context) {
                context.notify();
            }
        }
    }
}

class DeviceData {
    private String deviceId;
    private String hostName;

    DeviceData(String deviceId, String hostName) {
        this.deviceId = deviceId;
        this.hostName = hostName;
    }

    public String serialize() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
