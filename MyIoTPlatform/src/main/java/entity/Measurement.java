package entity;

import javax.xml.crypto.Data;

public class Measurement {
    private String deviceId;
    private Data timing;
    private String value;

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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Measurement{" +
                "deviceId='" + deviceId + '\'' +
                ", timing=" + timing +
                ", value='" + value + '\'' +
                '}';
    }
}
