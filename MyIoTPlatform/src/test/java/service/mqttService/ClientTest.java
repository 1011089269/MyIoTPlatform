package service.mqttService;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {

    @Test
    void connect() throws MqttException {
        Client client = new Client();
        client.connect();
    }

    @Test
    void subscribeAndPublish() throws MqttException,InterruptedException {
        Client client = new Client();
        client.connect();
        String topic = "test";
        //订阅
        client.subscribe(topic,0);
        //发布
        String msg = "{\"temperature\":28, \"humidity\":26}";
        client.publish(topic,msg);
        Thread.sleep(5000);
        //测试接收
//        while(true)
//            Thread.sleep(1000);
    }
}