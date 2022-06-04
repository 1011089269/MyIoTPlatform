package entity;

import java.util.UUID;

/**
 * @author d'f'g
 * @program: MyIoTPlatform
 * @description:
 * @date 2022-06-04 23:42:44
 */
public class Token {
    private int type;
    private int id;
    private String uuid;
    private String value;

    public Token(int type, int id) {
        this.type = type;
        this.id = id;
        uuid = UUID.randomUUID().toString();
        value = String.valueOf(id * 10 + type);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
