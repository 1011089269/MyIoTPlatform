package controller;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.mongodbService.StatusService;
import service.mqttService.Client;

/**
 * @author d'f'g
 * @program: MyIoTPlatform
 * @description:
 * @date 2022-06-14 17:42:36
 */
@Controller
@RequestMapping("mqtt")
public class MqttController {
    String publish = "idea";
    @Autowired
    private Client client;

    //deviceId
    @PostMapping("/sendID")
            @ResponseBody
            public boolean sendID(String sign, String deviceId) throws MqttException {
        //1为开，2为关
        String msg = "{\"Cmd\":"+sign+",\"Id\":"+deviceId+"}";
        System.out.println("Cmd:"+0+ "deviceId:"+deviceId);
        client.publish(publish,msg);
        return true;
    }

    //led
    @PostMapping("/cmd")
    @ResponseBody
    public boolean setCmd(String sign) throws MqttException {
        //12为LED，34为温湿度，56为继电器，8为手动模式
        String msg = sign;
        System.out.println("Cmd:"+sign);
        client.publish(publish,msg);
        return true;
    }

    //自动模式
    @PostMapping("/auto")
    @ResponseBody
    public void setAuto(String sign, String temperature) throws MqttException {
        //7为开
        String msg = "{\"temp\":"+temperature+",\"Cmd\":"+sign+"}";
        System.out.println("auto:"+sign +" temp"+temperature);
        client.publish(publish,msg);
    }
}
