package entity;

import javax.xml.crypto.Data;

public class Status {
    private String deviceId;
    private Data timing;
    private int status;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Data getTiming() {
        return timing;
    }

    public void setTiming(Data timing) {
        this.timing = timing;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Measurement{" +
                "deviceId='" + deviceId + '\'' +
                ", timing=" + timing +
                ", status=" + status +
                '}';
    }
}
