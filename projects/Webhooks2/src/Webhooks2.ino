/*
 * Project Webhooks2
 * Description:
 * Author:
 * Date:
 */

int sendMessage(String command);

void setup()
{
    Particle.function("sendMessage", sendMessage);
}

void loop() {
  
}

int sendMessage(String command)
{
    Particle.publish("part1", "Nice man!", 60, PRIVATE);
}
