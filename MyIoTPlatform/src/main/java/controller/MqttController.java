package controller;

import controller.interceptor.Authority;
import entity.Device;
import entity.Token;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.Result;
import service.mqttService.Client;

@Controller
@RequestMapping("mqtt")
public class MqttController {
    String publish = "idea";
    @Autowired
    public Client client;

    //led
    @PostMapping("/led")
    @ResponseBody
    public void setLED(String sign) throws MqttException {
        //1为开，2为关
        String msg = "{\"Cmd\":"+sign+"}";
        System.out.println("LED:"+sign);
        client.publish(publish,msg);
    }

    //继电器
    @PostMapping("/relay")
    @ResponseBody
    public void setRelay(String sign) throws MqttException {
            //3为开，4为关
        String msg = "{\"Cmd\":"+sign+"}";
        System.out.println("Relay:"+sign);
        client.publish(publish,msg);
    }

    //温湿度
    @PostMapping("/dht")
    @ResponseBody
    public void setDht(String sign) throws MqttException {
        //5为开，6为关
        String msg = "{\"Cmd\":"+sign+"}";
        System.out.println("Dht:"+sign);
        client.publish(publish,msg);
    }

    //自动模式
    @PostMapping("/auto")
    @ResponseBody
    public void setAuto(String sign, String temperature) throws MqttException {
        //7为开，8为关
        String msg = "{\"Cmd\":"+sign+" \"temp\":"+temperature+"}";
        System.out.println("auto:"+sign +" temp"+temperature);
        client.publish(publish,msg);
    }
}
