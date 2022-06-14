package controller.interceptor;

import entity.Token;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static entity.Token.Type.USER;

/**
 * 对于需要权限验证的操作，使用@Authority，跳过拦截器的权限验证
 * 如：管理员权限@Authority(role = {Token.Type.ADMIN})
 * 用户和管理员权限@Authority(role = {Token.Type.USER, Token.Type.ADMIN})
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Authority {
    Token.Type role()[] default {USER};
}
