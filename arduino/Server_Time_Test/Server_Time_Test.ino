#include <Arduino.h>
#include <ESP8266WiFi.h>
#include <ESP8266WiFiMulti.h>
#include <ESP8266HTTPClient.h>

//#define USE_SERIAL Serial

//아두이노 MAC ADDRESS
ESP8266WiFiClass wifi8266mac;
ESP8266WiFiMulti WiFiMulti;

//와이파이 이름, 비밀번호
const char* ssid = "PSL";
const char* password = "PilsungKang87695";

//서버 IP, PORT
IPAddress server{192, 168, 0, 50};
int serverport = 8000; // web이기 때문에 80이 default값이다.

WiFiClient wificlient;

HTTPClient http;

void setup() {

  Serial.begin(115200);
  // USE_SERIAL.setDebugOutput(true);

  Serial.println();
  Serial.println();
  Serial.println();

  for (uint8_t t = 4; t > 0; t--) {
    Serial.printf("[SETUP] WAIT %d...\n", t);
    Serial.flush();
    delay(1000);
  }

  //와이파이 연결
  WiFiMulti.addAP(ssid, password);

  //서버에 접속
  wificlient.connect(server, serverport);

}

void post() {
  Serial.println("Connecting to server...");

}

void loop() {
  // wait for WiFi connection
  if ((WiFiMulti.run() == WL_CONNECTED)) {

    String macID = wifi8266mac.macAddress();

    Serial.println();
    Serial.println("MAC Address = " + macID);

    Serial.print("[HTTP] begin...\n");
    //configure traged server and url
    //http.begin("https://192.168.1.12/test.html", "7a 9c f4 db 40 d3 62 5a 6e 21 bc 5c cc 66 c8 3e a1 45 59 38"); //HTTPS
    http.begin("http://192.168.0.50:8000/hotel/timetest"); //HTTP

    Serial.print("[HTTP] GET...\n");
    // start connection and send HTTP header
    int httpCode = http.GET();

    // httpCode will be negative on error
    if (httpCode > 0) {
      // HTTP header has been send and Server response header has been handled
      Serial.printf("[HTTP] GET... code: %d\n", httpCode);

      // file found at server
      if (httpCode == HTTP_CODE_OK) {
        String payload = http.getString();
        Serial.println(payload);
      }
    } else {
      Serial.printf("[HTTP] GET... failed, error: %s\n", http.errorToString(httpCode).c_str());
    }

    http.end();
  }

  delay(10000);
}
