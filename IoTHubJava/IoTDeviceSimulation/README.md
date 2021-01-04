## IoTDeviceSimulation/

###### Version: 1.0.0

###### This sample simulates the behavior of an IoT device connected via it's connection string.  

---



#### Sections:

- Dependencies and PreReqs

- Building and Running the Application

- Source Code Modification

- Real Time Data Visualization
  

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
- Create a Resource Group (change the location to your closest Azure location)

  ```powershell
  az group create `
  --name <resourceGroupName> `
  --location eastus
  ```

- Create a IoT Hub (name must be globally unique)

  ```powershell
  az iot hub create `
  --name <yourIoTHubName> `
  --resource-group <resourceGroupName> `
  --sku S1
  ```

- Register an IoT device to your hub choose and remember a device name:

  ```powershell
  az iot hub device-identity create `
  --hub-name <yourIoTHubName> `
  --device-id <deviceName>
  ```

- Reveal the connection string for your newly registered device using your previously selected device name:

  ```powershell
  az iot hub device-identity show-connection-string `
  --hub-name <yourIoTHubName> `
  --device-id <deviceName> `
  --output table
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



### Quick Ways to Modify the Source Code

@TODO

---



### Real Time Streaming Visualization

#### Prerequisites:
- Azure Account

- Power BI Subscription

#### Setup:

- Create a Stream Analytics Job using the [Azure Portal](https://ms.portal.azure.com/)

- Create a new resource using the "+ Create a Resource" button

- Type into the search bar "Stream Analytics Job" & create the resource

![Stream Analytics Job](./images/streamanalytics.png)

- As you create the resource pick the resource group you created earlier from the drop down menu, leave the other options default

- After the resource deploys, go to the resource & click the "Inputs" button (under Job topology)

- Add an IoT Hub Stream Input from the dropdown at the top

![IoT Hub Input](./images/iothubinput.png)

- Select the IoT Hub you created earlier, leave other options default and click "save"

- Create a Stream Output by clicking the "Outputs" button

- Add a Power BI output & Authorize

- Under "Authentication mode" make sure that User token is selected

![Power BI Output](./images/powerbioutput.png)

- Select "My workspace" under Group workspace 

- Click "Save" at the bottom of the blade

- Now to create the job itself, click the "Query" button (under Job topology)

- Insert the following query
  ```sql
  SELECT 
    deviceId AS Device, 
    value AS Temperature, 
    EventProcessedUtcTime AS Time
  INTO [<yourOutputAlias>]
  FROM [<yourInputAlias>]
  ```

>**_Note_**: <br> If your device is generating data you should be able to preview the data. There is also an option to preview from a selected time range.


- Start Device & Streaming Job

- Open [Power BI Service](https://msit.powerbi.com/)

- Click on "My workspace"

- Create a New Dashboard

![New Dashboard](./images/newdashboard.png)

- Click the "Edit" button on the task bar

![Edit Button](./images/edit.png)

- Add a Tile & click "Custom Streaming Data"

- A blade should appear with datasets, select the dataset you created as your stream analytics output

>**_Note_**: <br> It takes a bit for data to stream to your dataset, so wait a moment if the dataset is not appearing.

- Change the visualization type to "Line chart"

![Line Chart](./images/linechart.png)

- Select the necessary fields to display

- Congratulations you are now able to view your device data in real-time

![Dashboard](./images/dashboard.png)
