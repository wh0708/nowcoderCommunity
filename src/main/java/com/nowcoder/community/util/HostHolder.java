package com.nowcoder.community.util;

import com.nowcoder.community.entity.User;
import org.springframework.stereotype.Component;

/**
 * @Author WH
 * @create 2021/11/11 20:57
 * 持有用户信息
 * 用于代替session对象
 */
@Component
public class HostHolder {

    private ThreadLocal<User> users=new ThreadLocal<>();

    public void setUsers(User user){
        users.set(user);
    }

    public User getUser(){
        return users.get();
    }

    public void clear(){
        users.remove();
    }

}

