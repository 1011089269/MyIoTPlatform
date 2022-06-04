package entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author 86189
 * @program: IntelliJ IDEA
 * @description:
 * @date 2022-04-19 16:09:57
 */
@ApiModel(value = "user对象", description = "用户对象user")
public class User {
    private int id;
    @ApiModelProperty(value = "用户名", name = "name", example = "张三")
    private String name;
    @ApiModelProperty(value = "密码", example = "123456")
    private String password;
    @ApiModelProperty(value = "邮箱", name = "email", example = "zhangsan@163.com")
    private String email;
    @ApiModelProperty(value = "年龄", name = "age", example = "11")
    private int age;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", age='" + age + '\'' +
                '}';
    }
}
