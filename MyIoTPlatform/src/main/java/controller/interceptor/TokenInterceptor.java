package controller.interceptor;

import entity.Token;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import service.Result;
import service.TokenManager;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author d'f'g
 * @program: MyIoTPlatform
 * @description: token拦截器
 * @date 2022-06-05 15:53:34
 */
@Component
public class TokenInterceptor implements HandlerInterceptor {
    private final TokenManager tokenManager;

    public TokenInterceptor(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 判断是否有@FreeToken注解，如果有则跳过拦截器
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        if (method.getAnnotation(FreeToken.class) != null) {
            return true;
        }

        // 操作需要的权限
        Token.Type role[] = method.getAnnotation(Authority.class).role();

        // 检查token
//        String tokenValue = request.getHeader("tokenValue");
        Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        Cookie cookie = null;
        if (cookieMap.containsKey("tokenValue")) {
            cookie = cookieMap.get("tokenValue");
        } else {    //cookie中没有Token
            showInterceptionInfo(response, -1, "请先登录");
            return false;
        }
        String tokenValue = cookie.getValue();
        if (StringUtils.isNotEmpty(tokenValue) && !tokenValue.equals("null")) {
            Token token = new Token(tokenValue);
            if (tokenManager.checkToken(token)) {
                for (int i = 0; i < role.length; i++) {
                    if (token.getType() == role[i]) {
                        return true;
                    }
                }
            } else {
                showInterceptionInfo(response, -1, "登录信息失效，请重新登录");
                return false;
            }
        } else {
            showInterceptionInfo(response, -1, "请先登录");
            return false;
        }
//        // 获取token
//        String tokenvalue = null;
//        Cookie[] cookieArr = request.getCookies();
//        if (cookieArr != null && cookieArr.length > 0) {
//            for (Cookie cookie : cookieArr) {
//                if ("tokenValue".equals(cookie.getName())) {
//                    tokenvalue = cookie.getValue();
//                    break;
//                }
//            }
//        }
//        if (tokenvalue == null) {
//            showInterceptionInfo(response, -1, "请先登录");
//            return false;
//        }

//        // 判断是否存在该token
//        Token token = new Token(tokenvalue);
//        if (!tokenManager.checkToken(token)) {
//            showInterceptionInfo(response, -1, "请先登录");
//            return false;
//        }

//        // 判断是否有操作权限
//        if (token.getType() == role) {
//            return true;
//        }

        showInterceptionInfo(response, -2, "您当前没有使用该功能的权限");
        return false;
    }

    public void showInterceptionInfo(HttpServletResponse response, String msg) {
        showInterceptionInfo(response, 0, msg);
    }

    public void showInterceptionInfo(HttpServletResponse response, int status, String msg) {
        showInterceptionInfo(response, status, msg, null);
    }

    public void showInterceptionInfo(HttpServletResponse response, int status, String msg, Object data) {
        Result result = new Result();
        result.setStatus(status);
        result.setMsg(msg);
        result.setData(data);
        PrintWriter out = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Type",
                    "application/json;charset=UTF-8");
            out = response.getWriter();
            out.write(JSONObject.fromObject(result).toString());
        } catch (IOException ie) {
            ie.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
