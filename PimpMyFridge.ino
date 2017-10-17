#include "DHT.h"
#include <ArduinoJson.h>

#define DHTPIN 2     

#define DHTTYPE DHT22   

DHT dht(DHTPIN, DHTTYPE);

unsigned long previousMillis = 0;

const long interval = 1000;           

void setup() {
  Serial.begin(9600);
  
  dht.begin();
}

void loop() {
  
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

  DynamicJsonBuffer  jsonBuffer(200);

  JsonObject& root = jsonBuffer.createObject();

  root["type"] = "refresh-data";
  root["temperature"] =  t;
  root["humidity"] = h;

   if (currentMillis - previousMillis >= interval) {
    
    previousMillis = currentMillis;

    root.printTo(Serial);
    Serial.println();
   }
  }
 
