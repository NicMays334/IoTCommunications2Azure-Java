package com.krakn.iotdevicesimulation;

import com.microsoft.azure.sdk.iot.device.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Random;

class IoTDeviceSim
{
    private String connectionString;
    private String deviceId;
    private String hostname;
    private IotHubClientProtocol messagingProtocol;
    private DeviceClient client;
    public TelemetryDataPoint teleData;

    //"HostName=<hostname>;DeviceId=<deviceid>;SharedAccessKey=<sharedaccesskey>
    public IoTDeviceSim(String connectionString) throws URISyntaxException {
        this.connectionString = connectionString;
        this.hostname = connectionString.split(";")[0].split("=")[1];
        this.deviceId = connectionString.split(";")[1].split("=")[1];

        //MQTT is the standard for IoT Messaging ... This can be modified
        /* @TODO create a way to pass in the protocol without importing
        libraries in the main method*/
        this.messagingProtocol = IotHubClientProtocol.MQTT;

        this.client = new DeviceClient(this.connectionString,
          this.messagingProtocol);
        this.teleData = new TelemetryDataPoint(this.deviceId, null, 0.00);
    }

    public void sendIoTTelemetryData(boolean delta, int iterateNTimes,
      int delayMS) throws IOException, InterruptedException {

        //Message Generate
        client.open();
        Object lock = new Object();
        String messageString_json;
        EventCallback callback = new EventCallback();
        Random rand = new Random();

        //Send Telemetry Data N Times
        for( int i = 0; i < iterateNTimes; i++) {

            //tweak values to simulate data changing if delta is true
            if(delta) {
                this.teleData.setDataValue(30 + rand.nextDouble() * 15);
            }

            //generate a message from JSON string
            messageString_json = this.teleData.serialize();
            Message message = new Message(messageString_json);

            //testing a message property
            message.setProperty("temperatureSet",
              Double.toString(this.teleData.getDataValue()));

            // Send the message.
            client.sendEventAsync(message, callback, lock);
            System.out.println("Sending Message: "+messageString_json);

            synchronized (lock) {
                lock.wait();
            }

            Thread.sleep(delayMS);
        }

        client.closeNow();
    }

    @Override
    public String toString() {
        return "IoTDeviceSim:{" +
                "hostname:'" + hostname + '\'' +
                ", deviceId:'" + deviceId + '\'' +
                 '}';
    }
}
