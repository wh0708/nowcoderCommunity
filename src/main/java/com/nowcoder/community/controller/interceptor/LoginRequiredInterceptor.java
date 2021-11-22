package com.nowcoder.community.controller.interceptor;

import com.nowcoder.community.annotation.LoginRequired;
import com.nowcoder.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @Author WH
 * @create 2021/11/13 15:13
 */
@Component
public class LoginRequiredInterceptor implements HandlerInterceptor {

    @Autowired
    private HostHolder hostHolder;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler instanceof HandlerMethod){//handler拦截到的是一个方法才可以继续执行，我们需要拦截方法在此应用中
            HandlerMethod  handlerMethod=(HandlerMethod) handler;
            Method method=handlerMethod.getMethod();//获取到拦截到的方法对象
            LoginRequired loginRequired=method.getAnnotation(LoginRequired.class);//查看方法中的注解
            if(loginRequired!=null&&hostHolder.getUser()==null){//有此注解并且没有登录
                response.sendRedirect(request.getContextPath()+"/login");//强制重定向到登陆页面
                return false;
            }
        }
        return true;
    }
}

