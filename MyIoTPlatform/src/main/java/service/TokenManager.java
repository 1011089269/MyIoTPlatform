package service;

import entity.Token;
import org.springframework.stereotype.Service;

/**
 * @author d'f'g
 * @program: MyIoTPlatform
 * @description: 提供Token的相关功能
 * @date 2022-06-05 00:02:06
 */

/**
 * 提供Token的相关功能
 * 包括创建token、检查token是否有效、删除token
 */
@Service
public class TokenManager {
    private final RedisService redisService;

    public TokenManager(RedisService redisService) {
        this.redisService = redisService;
    }

    /**
     * 创建一个token关联指定用户（设备）
     * @param id   指定用户（设备）的id
     * @param type token类型
     * @return 生成的token
     */
    public Token createToken(int id, Token.Type type) {
        Token token = new Token(type, id);
        redisService.set(token.getUuid(), token.getValue(), 6000);
        return token;
    }

    /**
     * @param id   指定用户（设备）的id
     * @param type token类型
     * @return
     * @see #checkToken(Token)
     */
    public boolean checkToken(int id, Token.Type type) {
        return checkToken(new Token(type, id));
    }

    /**
     * @param tokenValue
     * @return
     * @see #checkToken(Token)
     */
    public boolean checkToken(String tokenValue) {
        try {
            return checkToken(new Token(tokenValue));
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 检查token是否有效
     * @param token 用户端发送来的token
     * @return 是否有效
     */
    public boolean checkToken(Token token) {
        return token.getValue().equals(redisService.get(token.getUuid()));
    }

    /**
     * 删除token
     * @param tokenValue 用户端发送来的token值
     * @see #deleteToken(Token)
     */
    public void deleteToken(String tokenValue) {
        deleteToken(new Token(tokenValue));
    }

    /**
     * 删除token
     * @param token 用户端发送来的token
     */
    public void deleteToken(Token token) {
        redisService.delete(token.getUuid());
    }
}
