package entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(value = "Device", description = "设备对象Device")
public class Device {
    @ApiModelProperty(value = "ID", name = "id", example = "1")
    private int id;
    @ApiModelProperty(value = "设备名称", name = "name", example = "温度传感器")
    private String name;
    @ApiModelProperty(value = "更新时间", example = "yyyy-mm-dd hh:mm:ss")
    private Date time;
    @ApiModelProperty(value = "数据类型", name = "dataType", example = "1/2/3")
    private int dataType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }


    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    @Override
    public String toString() {
        return "Device{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", time=" + time +
                ", dataType=" + dataType +
                '}';
    }
}
