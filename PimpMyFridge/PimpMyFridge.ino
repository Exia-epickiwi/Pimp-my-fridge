#include "DHT.h"
#include <ArduinoJson.h>

#define DHTPIN 2     
#define DHTTYPE DHT22

DHT dht(DHTPIN, DHTTYPE);

int tempSensor = 0;
float A = 0.00109613;
float B = 0.000240164;
float C = 5.87433*pow(10,-8);
unsigned long previousMillis = 0;
const long interval = 1000;

void setup() {
  Serial.begin(115200);
  
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
    float output = analogRead(tempSensor);
    float tension = output * 0.0048;
    float resistance = 10000/((5/tension)-1);
    Serial.println(resistance);

    float tempThermi = 1/(A+B*log(resistance)+C*pow(log(resistance),3));
    Serial.println(tempThermi-273.15);
   }
  }
 
