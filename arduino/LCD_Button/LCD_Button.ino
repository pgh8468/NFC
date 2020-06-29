#include <Adafruit_GFX.h>
#include <EEPROM.h>
#include <stdint.h>
#include <UTFTGLUE.h> 
#include "TouchScreen.h"

#define MINPRESSURE 10
#define MAXPRESSURE 1000
#define EEPROMBASE  0
const uint8_t analog_in_0 = A0;

UTFTGLUE myGLCD(0x1526, A2, A1, A3, A4, A0);

TouchScreen *myTouchp;

uint8_t b_anp, b_dip;   
uint8_t a_anp, a_dip;  

int b_top, b_bottom; int a_top, a_bottom;
uint8_t xydata = 0xBA;

int dispx, dispy;

int x, y;
char stCurrent[4]="";
int stCurrentLen=0;
char stLast[4]="";
char password[4]={'4','3','2','1'};


void geteeparm(UTFTGLUE *lcdp){
  int stadr = EEPROMBASE;
  bool rltok = true;
  int *sdata[4] = {&a_top, &a_bottom, &b_top, &b_bottom};
  
  for(int i = 0 ; i < 4 ; i++){
    *sdata[i] = ((int)EEPROM.read(stadr++) << 8) | EEPROM.read(stadr++);
    if((*sdata[i] > 1024) || (*sdata[i] < 10)) rltok = false;
  }
 
  uint8_t rdata = EEPROM.read(stadr++);
  a_anp = rdata >> 4; a_dip = rdata & 0x0F;   
  rdata = EEPROM.read(stadr++);
  b_anp = rdata >> 4; b_dip = rdata & 0x0F; 
  
  xydata = EEPROM.read(stadr);
  if((xydata != 0xAB) && (xydata != 0xBA)) rltok = false;


  if(!rltok){
    
    lcdp->clrScr();
    lcdp->MCUFRIEND_kbv::setTextSize(2);
    bool flash = true;
    while(1){
      lcdp->setColor(flash ? 255 : 0, 0, 0);
      flash = flash ? false : true;
      lcdp->print("    EEPROM data error!", LEFT, dispy/2 - 8);
      delay(500);
    }
  }
  
  
  return;
}


void rsmpin(void){
  pinMode(analog_in_0 + a_anp, OUTPUT);
  pinMode(analog_in_0 + b_anp, OUTPUT);
  return;
}


TSPoint getpoint(){
  
  for(int i = 2 ; i <= 9 ; i++) digitalWrite(i, 1);
  TSPoint pi = myTouchp->getPoint();
  TSPoint prt = pi;
  rsmpin();         
  
  int dispa = (xydata == 0xAB) ? dispx : dispy;
  int dispb = (xydata == 0xAB) ? dispy : dispx;
  int cposb, cposa;
 
  if(b_top > b_bottom) cposb = (int)(((long)(b_top - pi.x) * (long)dispb) / (long)(b_top - b_bottom));
  else cposb = (int)(((long)(pi.x - b_top) * (long)dispb) / (long)(b_bottom - b_top));
  
  if(a_top > a_bottom) cposa = (int)(((long)(a_top - pi.y) * (long)dispa) / (long)(a_top - a_bottom));
  else cposa = (int)(((long)(pi.y - a_top) * (long)dispa) / (long)(a_bottom - a_top));
  
  prt.x = (xydata == 0xAB) ? cposa : cposb;
  prt.y = (xydata == 0xAB) ? cposb : cposa;
  
  return prt;
}


void wrelease(){
  #define RCHKCNT 50  
  int zcnt = RCHKCNT;
  
  while(zcnt > 0){
    delay(2);
    for(int i = 2 ; i <= 9 ; i++) digitalWrite(i, 1);
    TSPoint ct = myTouchp->getPoint();
    if((ct.z >= MINPRESSURE) && (ct.z <= MAXPRESSURE)) zcnt = RCHKCNT; 
    else zcnt--;                                                     
  }

  rsmpin();
  
  return;
}

void drawButtons(){
  
  for (x=0; x<5; x++)
  {
    myGLCD.setColor(0, 0, 0);
    myGLCD.fillRect (10+(x*60), 10, 60+(x*60), 60);
    myGLCD.setColor(255, 255, 255);
    myGLCD.drawRect (10+(x*60), 10, 60+(x*60), 60);
    myGLCD.printNumI(x+1, 27+(x*60), 27);
  }
  
  for (x=0; x<5; x++)
  {
    myGLCD.setColor(0, 0, 0);
    myGLCD.fillRect (10+(x*60), 70, 60+(x*60), 120);
    myGLCD.setColor(255, 255, 255);
    myGLCD.drawRect (10+(x*60), 70, 60+(x*60), 120);
    if (x<4)
      myGLCD.printNumI(x+6, 27+(x*60), 87);
  }
  myGLCD.print("0", 267, 87);
  
  myGLCD.setColor(0, 0, 0);
  myGLCD.fillRect (10, 130, 150, 180);
  myGLCD.setColor(255, 255, 255);
  myGLCD.drawRect (10, 130, 150, 180);
  myGLCD.print("CANCEL", 50, 147);
  myGLCD.setColor(0, 0, 0);
  myGLCD.fillRect (160, 130, 300, 180);
  myGLCD.setColor(255, 255, 255);
  myGLCD.drawRect (160, 130, 300, 180);
  myGLCD.print("OK", 215, 147);
  myGLCD.setBackColor (0, 0, 0);
}

void updateStr(int val){
  if (stCurrentLen<4)
  {
    stCurrent[stCurrentLen]=val;
    stCurrent[stCurrentLen+1]='\0';
    stCurrentLen++;
    myGLCD.setColor(255, 255, 255);
    myGLCD.print(stCurrent, LEFT, 224);
  }
  else
  {
    for(int i = 0 ; i < 4 ; i++){
       myGLCD.setColor((i&1) ? 0 : 255, 0, 0);
       myGLCD.print("BUFFER FULL!", CENTER, 192);
       if(i < 3) delay(500);
    }
    myGLCD.setColor(255, 255, 255);
  }
}


void waitForIt(int x1, int y1, int x2, int y2)
{
  myGLCD.setColor(0, 0, 0); 
  myGLCD.drawRect (x1, y1, x2, y2);
  wrelease();                
  myGLCD.setColor(255, 255, 255); 
  myGLCD.drawRect (x1, y1, x2, y2);
}

void setup()
{
  
  myGLCD.InitLCD();
  myGLCD.clrScr();

 
  dispx = myGLCD.getDisplayXSize();
  dispy = myGLCD.getDisplayYSize();

  
  geteeparm(&myGLCD);


  myGLCD.MCUFRIEND_kbv::setTextSize(2);
  myGLCD.setBackColor(0, 0, 0);
  drawButtons();


  static TouchScreen lmyTouch = TouchScreen(b_dip, analog_in_0 + a_anp, analog_in_0 + b_anp, a_dip, 300);
  myTouchp = &lmyTouch;
}

void loop()
{
  TSPoint p;

 
  while (true){

    TSPoint p = getpoint();
 

    if((p.z >= MINPRESSURE) && (p.z <= MAXPRESSURE)){
      x = p.x; y = p.y;
     
      if((y >= 130) && (y <= 180)){
        if ((x>=10) && (x<=150)){  // Button: Cancel
          stCurrent[0]='\0';
          stCurrentLen=0;
          myGLCD.setColor(0, 0, 0);
          myGLCD.fillRect(0, 224, 319, 239);
          waitForIt(10, 130, 150, 180);
        }
        if ((x>=160) && (x<=300)){  // Button: OK
          if (stCurrentLen>0){
            
            int result = 0; // 0이면 같음, 1이면 틀림
            for (int j = 0; j < 4; j++){
              if(stCurrent[j] == password[j]){
                result = 0;
              }
              else{
                result = 1;
                break;
              }
           }
           if (result == 0){ // 비밀번호가 같을때
            stCurrent[0]='\0';
            stCurrentLen=0;
            myGLCD.setColor(0, 255, 0);     // change green frame
            myGLCD.drawRect (160, 130, 300, 180);
            for(int i = 0 ; i < 2 ; i++){
              myGLCD.print("SUCCESS", CENTER, 192);
              delay(500);
              myGLCD.print("         ", CENTER, 192);
              if(i < 1) delay(500);
            }
              myGLCD.setColor(255, 255, 255);
           }
           else if (result == 1){ //비밀번호가 다를때
            stCurrent[0]='\0';
            stCurrentLen=0;
            myGLCD.setColor(255, 0, 0);     // change red frame
            myGLCD.drawRect (160, 130, 300, 180);
            for(int i = 0 ; i < 4 ; i++){
              myGLCD.setColor((i&1) ? 0 : 255, 0, 0);
              myGLCD.print("FAIL", CENTER, 192);
              if(i < 3) delay(500);
            }
              myGLCD.setColor(255, 255, 255);
           }
            
            stCurrent[0]='\0';
            stCurrentLen=0;
            myGLCD.setColor(0, 0, 0);
            myGLCD.fillRect(0, 208, 319, 239);
            
          } else { 
            myGLCD.setColor(255, 0, 0);    
            myGLCD.drawRect (160, 130, 300, 180);
            for(int i = 0 ; i < 4 ; i++){
               myGLCD.setColor((i&1) ? 0 : 255, 0, 0);
               myGLCD.print("BUFFER EMPTY", CENTER, 192);
               if(i < 3) delay(500);
            }
            myGLCD.setColor(0, 0, 0);
          }
          waitForIt(160, 130, 300, 180);
        }

      
      } else {
        uint8_t xth = x / 2; uint8_t yth = y / 2;
        static const uint8_t ypos[2][2] = {{5, 30}, {35, 60}};
        static const uint8_t xpos[5][2] = {{5, 30}, {35, 60}, {65, 90}, {95, 120}, {125, 150}};
        static char num[10] = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};
        uint8_t ympt = 0;
       
        for(int i = 0 ; i < 10 ; i++){
          if(i == 5) ympt++;
          uint8_t xmpt = i % 5;
          if((yth >= ypos[ympt][0]) && (yth <= ypos[ympt][1])){
            if((xth >= xpos[xmpt][0]) && (xth <= xpos[xmpt][1])){
              updateStr(num[i]);
              waitForIt(xpos[xmpt][0] << 1, ypos[ympt][0] << 1, xpos[xmpt][1] << 1, ypos[ympt][1] << 1);
            }
          }
        }
      }
    }
    delay(10);
  }
}
