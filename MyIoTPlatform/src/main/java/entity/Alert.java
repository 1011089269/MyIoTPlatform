package entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.redis.core.index.Indexed;

import javax.xml.crypto.Data;
@Document(collection = "alert")
public class Alert {
    @Id
    @Indexed
    private String id;
    @Indexed
    private String deviceId;
    private String time;
    private String news;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
    }

    @Override
    public String toString() {
        return "Alert{" +
                "id='" + id + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", time='" + time + '\'' +
                ", news='" + news + '\'' +
                '}';
    }
}
