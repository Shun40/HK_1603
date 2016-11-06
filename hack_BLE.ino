
#include <CurieBLE.h>
#include <Servo.h>

BLEPeripheral blePeripheral;  // BLEペリフェラルデバイス
BLEService ledService("19B10000-E8F2-537E-4F6C-D104768A1214"); // BLEサービス

// BLE LED Characteristic　UUIDはandroid側と統一
BLEUnsignedCharCharacteristic switchCharacteristic("19B10001-E8F2-537E-4F6C-D104768A1214", BLERead | BLEWrite);

//袖引っ張る（pull）用ピン
const int tapPin = 6;
//肩たたく（tap）用ピン
const int pullPin = 9;

//袖引っ張る用変数
Servo pullservo;
//肩たたく用変数
Servo tapservo;

void setup() {
  Serial.begin(9600);

  tapservo.attach(tapPin);
  pullservo.attach(pullPin);

  blePeripheral.setLocalName("LED");
  blePeripheral.setAdvertisedServiceUuid(ledService.uuid());

  blePeripheral.addAttribute(ledService);
  blePeripheral.addAttribute(switchCharacteristic);

  switchCharacteristic.setValue(0);

  blePeripheral.begin();

  Serial.println("BLE LED Peripheral");
}

//袖を引っ張るオブジェクト
void pull(){
  int pos;
          for(pos = 0; pos <= 90; pos += 1) // goes from 0 degrees to 180 degrees
          {                                  // in steps of 1 degree
            pullservo.write(pos);              // tell servo to go to position in variable 'pos'
            delay(5);                       // waits 15ms for the servo to reach the position
          }
          delay(100);
          for(pos = 90; pos>=0; pos-=1)     // goes from 180 degrees to 0 degrees
          {
            pullservo.write(pos);              // tell servo to go to position in variable 'pos'
            delay(5);                       // waits 15ms for the servo to reach the position
          }
          for(pos = 0; pos <= 90; pos += 1) // goes from 0 degrees to 180 degrees
          {                                  // in steps of 1 degree
            pullservo.write(pos);              // tell servo to go to position in variable 'pos'
            delay(5);                       // waits 15ms for the servo to reach the position
          }
          delay(100);
          for(pos = 90; pos>=0; pos-=1)     // goes from 180 degrees to 0 degrees
          {
            pullservo.write(pos);              // tell servo to go to position in variable 'pos'
            delay(5);                       // waits 15ms for the servo to reach the position
          }
          delay(100);
}

//肩をたたくオブジェクト
void tap(){
 tapservo.write(45);
 delay(200);
 tapservo.write(0);
 delay(200);
 tapservo.write(45);
 delay(200);
 tapservo.write(0);
}

void loop() {
  // 接続確認用
  BLECentral central = blePeripheral.central();

  //接続成功なら
  if (central) {
    Serial.print("Connected to central: ");
    Serial.println(central.address());

    while (central.connected()) {
      //switchCharacteristicに値が代入されたら
      if (switchCharacteristic.written()) {
        //1が来たら袖を引っ張る
        if (switchCharacteristic.value() == 1 || switchCharacteristic.value() == '1') {   
          Serial.println(F("pull object on."));
          pull();
        }
        //2が来たら肩をたたく
        else if(switchCharacteristic.value() == 2 || switchCharacteristic.value() == '2'){
          Serial.println(F("tap object on."));
          tap();
          }
        else {
          Serial.println(F("Please input 1 or 2."));
        }
      }
    }

    Serial.print(F("Disconnected from central: "));
    Serial.println(central.address());
  }
}
