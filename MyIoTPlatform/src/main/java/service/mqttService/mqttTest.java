package service.mqttService;

import org.eclipse.paho.client.mqttv3.MqttException;
import service.mqttService.Client;

public class mqttTest {
    public static void main(String[] args) throws MqttException,InterruptedException {
        Client client = new Client();
        client.connect();
        String topic = "test";
        //订阅
        client.subscribe(topic,0);
        //发布
        String msg = "{\"temperature\":28, \"humidity\":26}";
        client.publish(topic,msg);
        Thread.sleep(5000);
    }
}
