/*
 * Project RestTest
 * Description:
 * Author: Raggi
 * Date:
 */
 int led1 = D7; // Instead of writing D0 over and over again, we'll write led1
 int led2 = D6; // Instead of writing D7 over and over again, we'll write led2
 bool on = false;
 void setup() {
   // Subscribe to the integration response event
   Particle.subscribe("hook-response/part1", myHandler, MY_DEVICES);
   pinMode(led1, OUTPUT);
   pinMode(led2, OUTPUT);
 }

 void myHandler(const char *event, const char *data) {
   if (on) {
     digitalWrite(led1, LOW);
     digitalWrite(led2, HIGH);
   } else {
     digitalWrite(led1, HIGH);
     digitalWrite(led2, LOW);
   }
   on = !on;
 }

// loop() runs over and over again, as quickly as it can execute.
void loop() {
  // Get some data
  String data = "Svalt";
  // Trigger the integration
  Particle.publish("part1", data, PRIVATE);
  // Wait 60 seconds
  delay(60000*10);
}
