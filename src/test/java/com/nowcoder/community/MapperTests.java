package com.nowcoder.community;

import com.nowcoder.community.dao.DiscussPostMapper;
import com.nowcoder.community.dao.UserMapper;
import com.nowcoder.community.entity.DiscussPost;
import com.nowcoder.community.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Author WH
 * @create 2021/11/3 21:54
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class MapperTests {

    @Autowired(required=false)
    private UserMapper userMapper;

    @Autowired(required=false)
    private DiscussPostMapper discussPostMapper;
    @Test
    public void testSelectUser(){
        User user = userMapper.selectById(101);
        System.out.println(user);

        user=userMapper.selectByName("liubei");
        System.out.println(user);

        user=userMapper.selectByEmail("nowcoder101@sina.com");
        System.out.println(user);
    }

    @Test
    public void testInsertUser(){
        User user=new User();
        user.setUsername("test");
        user.setPassword("123456");

        int row=userMapper.insertUser(user);
        System.out.println(row);
        System.out.println(user.getId());
    }

    @Test
    public void testUpdateUser(){

        int row=userMapper.updateStatus(150, 1);
        System.out.println(row);

        row=userMapper.updateHeader(150, "sdf");
        System.out.println(row);
        row=userMapper.updatePassword(150, "456");
        System.out.println(row);
    }
    @Test
    public void testSelectPosts(){
        List<DiscussPost> list = discussPostMapper.selectDiscussPosts(149, 0, 10);
        for(DiscussPost post:list){
            System.out.println(post);
        }
        int rows=discussPostMapper.selectDiscussPostRows(149);
        System.out.println(rows);
    }
}
