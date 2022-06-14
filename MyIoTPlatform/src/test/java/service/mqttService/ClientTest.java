package service.mqttService;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import entity.Device;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {

    @Test
    void connect() throws MqttException {
        String s = "{\"Res\":5}";
        Gson gson = new Gson();
        JsonObject data = (JsonObject) new JsonParser().parse(s).getAsJsonObject();
        String Res = data.get("Res").getAsString();
        System.out.println(Res);
    }

    @Test
    void subscribeAndPublish() throws MqttException,InterruptedException {
        Client client = new Client();
        client.connect();
        String publish = "idea";
        String subscribe = "esp";
        //订阅
        client.subscribe(subscribe,0);
        //发布
        String msg = "{\"Cmd\":2}";
        client.publish(publish,msg);
        Thread.sleep(5000);
        //测试接收
        while(true)
            Thread.sleep(1000);
    }
}