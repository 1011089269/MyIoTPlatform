package entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.redis.core.index.Indexed;

import javax.xml.crypto.Data;
@Document(collection = "status")
public class Status {
    @Id
    @Indexed
    private String id;
    @Indexed
    private String deviceId;
    private String time;
    private int status;//状态0/1

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Status{" +
                "id='" + id + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", time='" + time + '\'' +
                ", status=" + status +
                '}';
    }
}
