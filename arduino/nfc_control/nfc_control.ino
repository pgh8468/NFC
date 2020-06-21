#include <Wire.h>
#include <SPI.h>
#include <Adafruit_PN532.h>

//선연결
//#define PN532_SCK  (38)
//#define PN532_MOSI (34)
//#define PN532_SS   (32)
//#define PN532_MISO (36)


#define PN532_IRQ   (46)
#define PN532_RESET (47)  // Not connected by default on the NFC Shield

#define RELAY (50)
//nfc연결
//Adafruit_PN532 nfc(PN532_SCK, PN532_MISO, PN532_MOSI, PN532_SS);

Adafruit_PN532 nfc(PN532_IRQ, PN532_RESET);

String content = "";

void setup(void) {
  Serial.begin(115200);

  pinMode(RELAY,OUTPUT);
  while (!Serial) delay(10); // for Leonardo/Micro/Zero

  Serial.println("Hello!");

  nfc.begin();

  uint32_t versiondata = nfc.getFirmwareVersion();
  if (! versiondata) {
    Serial.print("Didn't find PN53x board");
    while (1); // halt
  }

  Serial.print("Found chip PN5"); Serial.println((versiondata >> 24) & 0xFF, HEX);
  Serial.print("Firmware ver. "); Serial.print((versiondata >> 16) & 0xFF, DEC);
  Serial.print('.'); Serial.println((versiondata >> 8) & 0xFF, DEC);


  nfc.SAMConfig();

  Serial.println("Waiting for an ISO14443A Card ...");
}


void loop(void) {
  uint8_t success;
  uint8_t uid[] = { 0, 0, 0, 0, 0, 0, 0 };  // Buffer to store the returned UID
  uint8_t uidLength;                        // Length of the UID (4 or 7 bytes depending on ISO14443A card type)

  success = nfc.readPassiveTargetID(PN532_MIFARE_ISO14443A, uid, &uidLength);

  if (success) {

    Serial.println("Found an ISO14443A card");
    Serial.print("  UID Length: ");
    Serial.print(uidLength, DEC);
    Serial.println(" bytes");
    Serial.print("  UID Value: ");
    nfc.PrintHex(uid, uidLength);
    Serial.println("");



    if (uidLength == 4)
    {

      Serial.println("Seems to be a Mifare Classic card (4 byte UID)");


      Serial.println("Trying to authenticate block 4 with default KEYA value");
      
      //UID에 저장된값이 내가 긁은카드
      //keya가 내가 정답이라고 저장한 값
      uint8_t keya[4] = { 0xB5, 0x3A, 0xBF, 0x53};


      success = 1;

      for (uint8_t i = 0; i < uidLength; i++)
      {
         if(uid[i]!=keya[i]){
          success=0;
        }
       
      }
     
      if (success)
      {
        //HIGH가 들어가는거고 LOW 가 삐죽나온다.
        digitalWrite(50,HIGH);
        delay(10000);
        digitalWrite(50,LOW);
      }
      else
      {
        Serial.println("123123 ... authentication failed: Try another key?");
      }
    }

    if (uidLength == 7)
    {
      // We probably have a Mifare Ultralight card ...
      Serial.println("Seems to be a Mifare Ultralight tag (7 byte UID)");

      // Try to read the first general-purpose user page (#4)
      Serial.println("Reading page 4");
      uint8_t data[32];
      success = nfc.mifareultralight_ReadPage (4, data);
      if (success)
      {
        // Data seems to have been read ... spit it out
        nfc.PrintHexChar(data, 4);
        Serial.println("");

        // Wait a bit before reading the card again
        delay(1000);
      }
      else
      {
        Serial.println("... unable to read the requested page!?");
      }
    }
  }
}
