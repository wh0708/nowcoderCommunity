package com.nowcoder.community.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author WH
 * @create 2021/11/8 20:24
 */
public class CommunityUtil {

    //生成随机字符串
    public static String generateUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    //MD5加密 每次加秘结果的都一样
    //hello -> abc123def456
    //hello + 3e4a8(salt) -> abd123def456abc
    public static String md5(String key){
        if(StringUtils.isBlank(key)){
            return null;
        }
        return DigestUtils.md5DigestAsHex(key.getBytes());
    }

    //封装json工具
    public static String getJSONString(int code, String msg, Map<String,Object> map){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("code",code);
        jsonObject.put("msg",msg);
        if(map != null){
            for(String key : map.keySet()){
                jsonObject.put(key,map.get(key));
            }
        }
        return jsonObject.toJSONString();
    }

    //重载json工具
    public static String getJSONString(int code, String msg){
        return getJSONString(code,msg,null);
    }

    //重载json工具
    public static String getJSONString(int code){
        return getJSONString(code,null,null);
    }

//    public static void main(String[] args) {
//        Map<String,Object> map=new HashMap<>();
//        map.put("name", "wh");
//        map.put("age",25);
//        System.out.println(getJSONString(0, "ok", map));
//    }


}

