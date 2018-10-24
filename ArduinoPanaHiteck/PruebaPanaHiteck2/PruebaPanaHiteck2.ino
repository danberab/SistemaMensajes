
String msg="";
#include <LiquidCrystal.h>            // incluimos la libreria LCD
LiquidCrystal lcd(12, 11, 5, 4, 3, 2);// inicializamos los pines 

void setup() {
  // Declaramos que utilizaremos el pin 13 como salida
//  pinMode(13, OUTPUT);
  //Iniciamos la comunicación con el puerto serie
  lcd.begin(16, 2);                     //Cofiguramos el tamaño de la LCD
  Serial.begin(9600);
}

void loop() {
  //En caso que haya información en el Serial Port, se entra en esta estructura
  if (Serial.available() > 0) {
      lcd.clear();
    //Se lee la información entrante en el Serial Port
      char input = Serial.read();
      msg += input;
//    String msg = "hola verito";
//    if (input == 'a') {
      //Si el valor de input es 1, se enciende el led
//      digitalWrite(13, HIGH);
      lcd.cursor();                       
      lcd.setCursor(0, 0);
      input = Serial.read();         
      lcd.print(msg);           
//      delay(1000);                          
//      lcd.clear();        
      
//    }
//    else
//    {
      //Si el valor de input es diferente de 1, se apaga el LED
//      digitalWrite(13, LOW);
//    }
  }
}


