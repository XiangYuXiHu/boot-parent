package com.smile.lua.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @ClassName UserRedPacketService
 * @Author smile
 * @date 2022.09.24 09:04
 */
@Service
public class UserRedPacketService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private RedPacketService redPacketService;

    @Autowired
    private DefaultRedisScript<String> redisScript;

    public Long grabRedPacket(Long redPacketId, Long userId) {
        String args = userId + "-" + System.currentTimeMillis();
        String key = String.valueOf(redPacketId);

        Object res = redisTemplate.execute((RedisConnection connection) -> connection.eval(
                redisScript.getScriptAsString().getBytes(), ReturnType.INTEGER,
                1, key.getBytes(), args.getBytes()));
        Long result = (Long) res;
        if (result == 2) {
            String unitAmount = (String) redisTemplate.opsForHash().get("red_packet_" + redPacketId, "unitAmount");
            redPacketService.saveUserRedPacket(redPacketId, Double.valueOf(unitAmount));
        }
        return result;
    }
}
