package com.nowcoder.community.dao;

import com.nowcoder.community.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Author WH
 * @create 2021/11/3 21:39
 * 只需要创建一个接口，MyBaits会自动创建接口实现类
 */
@Mapper
public interface UserMapper {
    User selectById(int id);

    User selectByName(String username);

    User selectByEmail(String email);

    int insertUser(User user);

    int updateStatus(int id, int status);

    int updateHeader(int id,String headerUrl);

    int updatePassword(int id,String password);

}
