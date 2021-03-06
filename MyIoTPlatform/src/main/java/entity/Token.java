package entity;

import java.util.UUID;

/**
 * @author d'f'g
 * @program: MyIoTPlatform
 * @description:
 * @date 2022-06-04 23:42:44
 */
public class Token {
    public enum Type {  // 定义权限类型
        DEVICE("设备"),
        USER("普通用户"),
        ADMIN("管理员"),
        DEVELOP("开发者");

        private String name;

        Type(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private Type type;
    private int id;
    private String uuid;
    private String value;

    public Token(String value) {
        String[] parameters = value.split("-");
        if (parameters.length != 3) {
//            throw new IllegalArgumentException("invalid token value");
            id = 0;
            type = null;
            uuid = "";
            this.value = String.valueOf(id) + "-" + type + "-" + uuid;
            return;
        }
        id = Integer.parseInt(parameters[0]);
        type = Type.valueOf(parameters[1]);
        uuid = parameters[2];
        this.value = String.valueOf(id) + "-" + type + "-" + uuid;
    }

    public Token(Type type, int id) {
        this.type = type;
        this.id = id;
        uuid = UUID.randomUUID().toString().replace("-", "");
        value = String.valueOf(id) + "-" + type + "-" + uuid;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
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
