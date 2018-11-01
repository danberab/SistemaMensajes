#include <DHT.h>
#include <LiquidCrystal.h> // incluimos la libreria LCD
#include "DHT.h" // incluimos la libreria DHT para sensor de humedad
 
#define DHTPIN 7     // Indicamos el pin donde conectaremos la patilla data de nuestro sensor
#define DHTTYPE DHT11   // DHT 11 

DHT dht(DHTPIN, DHTTYPE);  //Indica el pin con el que trabajamos y el tipo de sensor
int maxh=0, minh=100, maxt=0, mint=100;  //Variables para ir comprobando maximos y minimos



// inicializamos los pines 
LiquidCrystal lcd(12, 11, 5, 4, 3, 2);

//se inicializan las variables
/*como java envia carater por caracter, lo que realizael IDE es concatenar 
cada carater para mostrar lo que envio Java desde la interfaz*/
String msg="";
//variable que se envia desde la interfaz
char input;
//Entrada analogica de LM35
int Ana1 = A0; 
int Temp = 0;
char Grados = 'C';

const long A = 1000;     //Resistencia en oscuridad en KO
const int B = 15;        //Resistencia a la luz (10 Lux) en KO
const int Rc = 10;       //Resistencia calibracion en KO
const int LDRPin = A1;   //Pin del LDR

int V;
int ilum;
boolean hora =false, mensaje= false;

void setup() {
  //Iniciamos la comunicación con el puerto serie
  lcd.begin(16, 2);                
  pinMode(10,OUTPUT);    
  //Cofiguramos el tamaño de la LCD
  Serial.begin(9600);
  // Prepara la libreria para el sensor dht11
  dht.begin();


}

void loop() {
  //En caso que haya información en el Serial Port, se entra en esta estructura
  lcd.clear();
  
  if (Serial.available()) {
      input = Serial.read();
  
      if(hora){
        msg += input;
      }else if(mensaje){
        msg += input;
      }
      else{
        if(input =='5'){
          hora=true;
        }else if (input == '2'){ // Temperatura
          lcd.clear();
          Temp = analogRead(Ana1); 
          Temp = (5.0 * Temp * 100.0)/1024.0;
          
          lcd.setCursor(1,0); 
          lcd.print("Temp: ");
          lcd.print(Temp);
          lcd.print(" C");
          delay(1000); 
          msg="";
        }
        else if (input == '3'){ // Luminosidad
         V = analogRead(LDRPin);         
         ilum = ((long)V*A*10)/((long)B*Rc*(1024-V));    //usar si LDR entre A0 y Vcc (como en el esquema anterior)
         lcd.clear();
         lcd.print("Luminosidad: ");
         lcd.print(ilum);
         delay(1000);
         ilum=0;
         lcd.clear();
        }
        else if (input == '4'){ // Humedad
          int h = dht.readHumidity();  //Guarda la lectura de la humedad en la variable float h
          int t = dht.readTemperature();  //Guarda la lectura de la temperatura en la variable float t
         
          // Comprobamos si lo que devuelve el sensor es valido, si no son numeros algo esta fallando
          if (isnan(t) || isnan(h)) // funcion que comprueba si son numeros las variables indicadas 
          {
            lcd.print("Fallo al leer del sensor DHT"); //Mostramos mensaje de fallo si no son numeros
          } else {
            //Mostramos mensaje con valores actuales de humedad y temperatura, asi como maximos y minimos de cada uno de ellos
            lcd.print("Hum: "); 
            lcd.print(h);
            lcd.print("Temp: "); 
            lcd.print(t);
            lcd.print(" *C");
            //Comprobacion de maximos y minimos de humedad y temperatura
            if (maxh<h)
              maxh=h;
            if (h<minh)
              minh=h;
            if (maxt<t)
              maxt=t;
            if (t<mint)
              mint=t;
            lcd.print("Max: ");
            lcd.print(maxh);
            lcd.print(" % ");
            lcd.print("Min: ");
            lcd.print(minh);
            lcd.print("Max: ");
            lcd.print(maxt);
            lcd.print(" *C ");
            lcd.print("Min: ");
            lcd.print(mint);
            lcd.print(" *C");
          }
          delay(2000);
        }else{
          mensaje = true;
        }
      }
  }
  else if(msg!=""){
    lcd.print(msg);
    delay(1000);
    msg="";
    hora = false;
  }
}





 
