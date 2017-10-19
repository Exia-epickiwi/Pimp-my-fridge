#include "DHT.h"
#include <ArduinoJson.h>

#define DHTPIN 11
#define DHTTYPE DHT22

DHT dht(DHTPIN, DHTTYPE);

int tempSensor = 0;
int LED = 12;
int Peltier = 5;
float A = 0.00109613;
float B = 0.000240164;
float C = 5.87433*pow(10,-8);

unsigned long previousMillis = 0;
const long interval = 1000;
float tempThermi = 0;
void setup() {
  Serial.begin(9600);
  pinMode(Peltier,OUTPUT);
  pinMode(LED,OUTPUT);
  
  dht.begin();
}

void loop() {
  analogWrite(Peltier,255);
  digitalWrite(LED,HIGH);
  
  unsigned long currentMillis = millis();
  
  float h = dht.readHumidity();
  float t = dht.readTemperature();
  float f = dht.readTemperature(true);
  
  if (isnan(h) || isnan(t) || isnan(f)) {
    Serial.println("Failed to read from DHT sensor!");
    return;
  }
  
  float hif = dht.computeHeatIndex(f, h);
  float hic = dht.computeHeatIndex(t, h, false);

   if (currentMillis - previousMillis >= interval) {
    previousMillis = currentMillis;
    DynamicJsonBuffer  jsonBuffer(200);

    JsonObject& root = jsonBuffer.createObject();

    root["type"] = "refresh-data";

    float output = analogRead(tempSensor);
    float tension = output * 0.0048;
    float resistance = 10000/((5/tension)-1);

    float tempThermi = 1/(A+B*log(resistance)+C*pow(log(resistance),3));
    tempThermi = tempThermi-273.15;
    root["temperature"] =  t;
    root["humidity"] = tempThermi;    
    root.printTo(Serial);
    Serial.println();
   }
  }

 void serialEvent()
{
    String text = "";
    char data = Serial.read();
    while(data != '\n' && data != '\r' && data > 0){
      text += data;
      data = Serial.read();
    }
    while(Serial.read() != -1);
    DynamicJsonBuffer  jsonBufferMessage(200);
    JsonObject& message = jsonBufferMessage.parseObject(text);
    
    float order = message["order-temperature"];
    Serial.println(order);
    if(order <= tempThermi) {
      analogWrite(Peltier,0);
      digitalWrite(LED,LOW);
    }
    
}


 
