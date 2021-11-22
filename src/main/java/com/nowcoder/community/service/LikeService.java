package com.nowcoder.community.service;

import com.nowcoder.community.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

/**
 * @Author WH
 * @create 2021/11/21 19:57
 */
@Service
public class LikeService {

    @Autowired
    private RedisTemplate redisTemplate;

    // 点赞
    public void like(int userId, int entityType, int entityId, int entityUserId) {
//        String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType, entityId); // 生成实体对应的key
//        boolean isMember = redisTemplate.opsForSet().isMember(entityLikeKey, userId);  // 判断 userId是否在 entityLikeKey 集合里
//        if (isMember) {  // 若已存在，说明已经点过赞，将uerId从集合中移除（取消赞）
//            redisTemplate.opsForSet().remove(entityLikeKey, userId);
//        } else {   // 没点过赞，所以添加到集合中
//            redisTemplate.opsForSet().add(entityLikeKey, userId);
//        }
        redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType, entityId);   // 生成实体对应的key
                String userLikeKey = RedisKeyUtil.getUserLikeKey(entityUserId); // 实体用户Id对应的Key

                // 查询操作要放在事务外
                boolean isMember = operations.opsForSet().isMember(entityLikeKey, userId);  // 判断 userId是否在 entityLikeKey 集合里

                operations.multi(); // 启用事务
                // 查询操作不能放在里面,批量操作在发送 EXEC 命令前被放入队列缓存，并不会被实际执行，也就不存在事务内的查询要看到事务里的更新，事务外查询不能看到。

                if (isMember) {
                    operations.opsForSet().remove(entityLikeKey, userId); // 若已存在，说明已经点过赞，将uerId从集合中移除（取消赞）
                    operations.opsForValue().decrement(userLikeKey);
                } else {
                    operations.opsForSet().add(entityLikeKey, userId); // 没点过赞，所以添加到集合中
                    operations.opsForValue().increment(userLikeKey);
                }

                return operations.exec(); // 提交事务
            }
        });
    }

    // 查询某实体点赞的数量
    public long findEntityLikeCount(int entityType, int entityId) {
        String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType, entityId);
        return redisTemplate.opsForSet().size(entityLikeKey); // 统计数量
    }

    // 查询某人对某实体的点赞状态
    public int findEntityLikeStatus(int userId, int entityType, int entityId) { // 返回int可以表示多个状态
        String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType, entityId);
        return redisTemplate.opsForSet().isMember(entityLikeKey, userId) ? 1 : 0;
    }

    // 查询某个用户获得的赞
    public int findUserLikeCount(int userId) {
        String userLikeKey = RedisKeyUtil.getUserLikeKey(userId);
        Integer count = (Integer) redisTemplate.opsForValue().get(userLikeKey); // 返回的是Object类型所以要强转
        return count == null ? 0 : count.intValue();
    }
}