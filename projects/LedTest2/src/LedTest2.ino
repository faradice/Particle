/*
 * Project LedTest2
 * Description:
 * Author: Raggi from test
 * Date:
 */

int led1 = D7; // Instead of writing D0 over and over again, we'll write led1
int led2 = D6; // Instead of writing D7 over and over again, we'll write led2

void setup() {
  pinMode(led1, OUTPUT);
  pinMode(led2, OUTPUT);
}

void loop() {
  digitalWrite(led1, LOW);
  digitalWrite(led2, HIGH);
  delay(1000);

  digitalWrite(led1, HIGH);
  digitalWrite(led2, LOW);

  delay(1000);
}
