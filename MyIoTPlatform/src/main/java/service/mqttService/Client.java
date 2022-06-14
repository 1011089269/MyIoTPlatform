package service.mqttService;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import entity.*;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import org.springframework.stereotype.Service;


import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
@Service
public class Client {
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    //mqtt协议
    private static final String HOST = "tcp://124.221.244.222:1883";//服务器IP:端口
    private static final String CLIENT_ID = "IDEA_client09";//客户端唯一标识
    private static final String USERNAME = "idea";
    private static final String PASSWORD = "13579";
    private static final String WILL_TOPIC = "esp";
    private MqttClient mqttClient;

    public void connect() throws MqttException {
        //新建客户端实例
        // MemoryPersistence设置客户端实例的保存形式，默认为以内存保存，此处以内存保存
        mqttClient = new MqttClient(HOST, CLIENT_ID, new MemoryPersistence());//设置连接时的参数
        MqttConnectOptions options = new MqttConnectOptions();
        //是否情况session，如果设置为false表示服务器会保留客户端的连接记录，
        // 该选项设置为true表示客户端每次连接的服务器都以新的身份连接
        options.setCleanSession(true);
        options.setUserName(USERNAME);
        options.setPassword(PASSWORD.toCharArray());//连接超时时间
        options.setConnectionTimeout(100);//心跳间隔时间
        options.setKeepAliveInterval(180);//掉线自动重连
        options.setAutomaticReconnect(true);
        //遗嘱消息:当连接断开时发送的死亡预告，此客户端连接断开后，//服务器会把此消息推送给订阅了此主题的客户机
        options.setWill(WILL_TOPIC, "offline".getBytes(), 0, true);
        mqttClient.setCallback(new MqttCallback() {
            //在断开连接时调用override
            public void connectionLost(Throwable cause) {
                System.out.println("connection lost,reconnecting ");
            }

            //收到已经订阅的发布override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                System.out.println("topic: " + topic + ", message: " + message);
                String jsonStr = new String(message.getPayload());
                // mqtt.fx中使用GBK加解码，若与其联用时想要避免中文乱码需使用以下方法
                jsonStr = new String(message.getPayload(), Charset.forName("GBK"));
                System.out.println("json string: " + jsonStr);
                try {
                //gson
                Gson gson = new Gson();
                Device device = new Device();
                JsonObject jsonObject = (JsonObject) new JsonParser().parse(jsonStr).getAsJsonObject();
                String deviceId = jsonObject.get("deviceId").getAsString();
                String data = jsonObject.get("data").getAsString();
                int dataType = jsonObject.get("dataType").getAsInt();
                System.out.println("deviceId:"+deviceId+" data"+data+" dataType"+dataType);
                mongo m = new mongo();
                if(dataType == 1){
                    Measurement measurement = new Measurement();
                    measurement.setDeviceId(deviceId);
                    measurement.setTime(df.format(new Date()));
                    measurement.setValue(data);
                    System.out.println(measurement);
                    m.insertMeasurement(measurement, "measurement");
                }else if(dataType == 2){
                    Alert alert = new Alert();
                    alert.setDeviceId(deviceId);
                    alert.setTime(df.format(new Date()));
                    alert.setNews(data);
                    System.out.println(alert);
                    m.insertAlert(alert, "alert");
//                    alertService.insert(alert);
                }
                else if(dataType == 3){
                    Status status = new Status();
                    status.setDeviceId(deviceId);
                    status.setTime(df.format(new Date()));
                    status.setStatus(Integer.parseInt(data));
                    System.out.println(status);
                    m.insertStatus(status, "status");
//                    statusService.insert(status);
                }

                } catch (Exception e) {
                    System.out.println(e.toString());
                }
            }

            //接收已经发布的QoS 1 或QoS 2消息的传递令牌时调用0verride
            public void deliveryComplete(IMqttDeliveryToken token) {
                System.out.println(" delivery complete");
            }
        });
        mqttClient.connect(options);
        System.out.println("connect success");
    }

    public void subscribe(String topic, int qos) throws MqttException {
        mqttClient.subscribe(topic, qos);
    }
    public void publish(String topic, String message) throws MqttException{
        MqttTopic mqttTopic = mqttClient.getTopic(topic);
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setPayload(message.getBytes());
        // mqtt.fx中使用GBK加解码，若与其联用时想要避免中文乱码需使用以下方法
        mqttMessage.setPayload(message.getBytes(Charset.forName("GBK")));
        MqttDeliveryToken token = mqttTopic.publish(mqttMessage);
        token.waitForCompletion();
    }
}


