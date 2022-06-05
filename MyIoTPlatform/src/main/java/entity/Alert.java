package entity;

import javax.xml.crypto.Data;

public class Alert {
    private String deviceId;
    private Data timing;
    private String news;

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

    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
    }

    @Override
    public String toString() {
        return "Alert{" +
                "deviceId='" + deviceId + '\'' +
                ", timing=" + timing +
                ", news='" + news + '\'' +
                '}';
    }
}
