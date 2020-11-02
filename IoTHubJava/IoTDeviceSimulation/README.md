## IoTDeviceSimulation/

###### Version: 1.0.0

###### This sample simulates the behavior of an IoT device connected via it's connection string.  

---



#### Sections:

- Dependencies and PreReqs

- Building and Running the Application

- Source Code Modification

  

---



#### Dependencies:

- Java SDK 8+

- com.microsoft.azure.sdk.iot:1.16.0
- com.google.code.gson:2.3.1
- Apache Maven:3.6.3

#### Prerequisites:

- Creation of an IoT Hub in Azure

- Using Azure CLI add the IoT Extension:

  ```powershell
  az extension add --name azure-iot
  ```

- Register an IoT device to your hub choose and remember a device name:

  ```powershell
  az iot hub device-identity create --hub-name <yourIoTHubName> --device-id <deviceName>
  ```

- Reveal the connection string for your newly registered device using your previously selected device name:

  ```powershell
  az iot hub device-identity show-connection-string --hub-name <yourIoTHubName> --device-id <deviceName> --output table
  ```

- The above command should reveal the device string in the form of:

  ```powershell
  "HostName=<hostname>;DeviceId=<deviceid>;SharedAccessKey=<sharedaccesskey>"
  ```

  Save this output somewhere safe.
  
  

---



#### Building and Running the Java Application (PowerShell, Bash, Terminal):

- Move into the correct directory:

```bash
cd IoTCommunications2Azure-Java/IoTHubJava/IoTDeviceSimulation/
```

- Contents of this directory should be:

```php
IoTDeviceSimulation/
├── src/main/java/com/krakn/IoTDeviceSimulation/
│  └── EventCallback.java
│  └── IotTDeviceSim.java
│  └── IoTDeviceSimulation.java
│  └── TelemetryDataPoint.java
├── pom.xml
```

- Run maven against the project to generate the project's jar file

```bash
mvn clean package
```

- Run the jar file using java

```bash
java -jar .\target\iotdevicesimulation-1.0.0-with-deps.jar <"ConnectionString"> <delayMS> <iterateNTimes>
```

- The arguments to run the jar are:
  - `<"ConnectionString">` : The **string** containing the necessary information to connect to the IoT Hub. Was generated above utilizing Azure CLI. 
  - `delayMS` : The amount of time between messages being sent from the device to the IoT Hub
  - `<iterateNTimes>` : The amount of times a message will be sent. 
  - Thus, `(<iterateNTimes> * <delayMS>)/1000 + 2.5 = ApproximateConnectionTime ` in seconds. 2.5 is added to the connection time to account for open and close of the IoT Client. This is a **very** rough approximation.

- If successfully built and run the output should appear along the lines of: 

  This was run with a delay of 1000 ms and iterated 5 times:

  ``````
  
  Communicating from device: IoTDeviceSim:{hostname:'omitted', deviceId:'myEdgeDevice'}
  Sending Message #1: {"deviceId":"myEdgeDevice","name":"temperature","value":38.55083763985875}
  Responded with OK_EMPTY
  
  Sending Message #2: {"deviceId":"myEdgeDevice","name":"temperature","value":44.49629503871056}
  Responded with OK_EMPTY
  
  Sending Message #3: {"deviceId":"myEdgeDevice","name":"temperature","value":39.10910025034964}
  Responded with OK_EMPTY
  
  Sending Message #4: {"deviceId":"myEdgeDevice","name":"temperature","value":44.17125333078465}
  Responded with OK_EMPTY
  
  Sending Message #5: {"deviceId":"myEdgeDevice","name":"temperature","value":39.07998237373732}
  Responded with OK_EMPTY
  
  Done
  
  
  ``````

  

---



### Quick Ways to Modify the Source Code\

@TODO