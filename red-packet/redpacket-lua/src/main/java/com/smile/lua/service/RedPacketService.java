package com.smile.lua.service;

import com.smile.lua.domain.RedPacket;
import com.smile.lua.domain.UserRedPacket;
import com.smile.lua.mapper.RedPacketMapper;
import com.smile.lua.mapper.UserRedPacketMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description
 * @ClassName RedisRedPacketService
 * @Author smile
 * @date 2022.09.24 09:13
 */
@Service
public class RedPacketService {


    private static final String USER_RED_PACKET_PREFIX = "red_packet_list_";
    /**
     * 每次取出1000条
     */
    private static final int FETCH_SIZE = 1000;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private RedPacketMapper redPacketMapper;

    @Resource
    private UserRedPacketMapper userRedPacketMapper;

    @Async
    public void saveUserRedPacket(Long redPacketId, Double unitAmount) {
        long start = System.currentTimeMillis();
        BoundListOperations ops = redisTemplate.boundListOps(USER_RED_PACKET_PREFIX + redPacketId);
        Long SIZE = ops.size();
        Long times = SIZE % FETCH_SIZE == 0 ? SIZE / FETCH_SIZE : (SIZE / FETCH_SIZE + 1);

        int count = 0;
        ArrayList<UserRedPacket> userRedPackets = new ArrayList<>(FETCH_SIZE);
        for (int i = 0; i < times; i++) {
            List<String> userIds;
            if (i == 0) {
                userIds = ops.range(i * FETCH_SIZE, (i + 1) * FETCH_SIZE);
            } else {
                userIds = ops.range(i * FETCH_SIZE + 1, (i + 1) * FETCH_SIZE);
            }
            userRedPackets.clear();
            if (!CollectionUtils.isEmpty(userIds)) {
                userIds.stream().forEach(userTime -> {
                    String[] values = StringUtils.split(userTime, "-");

                    UserRedPacket userRedPacket = new UserRedPacket();
                    userRedPacket.setRedPacketId(redPacketId);
                    userRedPacket.setUserId(Long.valueOf(values[0]));
                    userRedPacket.setCreateTime(new Date(Long.parseLong(values[1])));
                    userRedPacket.setAmount(unitAmount);
                    userRedPacket.setNote("抢红包" + redPacketId);
                    userRedPackets.add(userRedPacket);
                });
            }
            count = count + userRedPacketMapper.batchInsert(userRedPackets);
        }
        redisTemplate.delete(USER_RED_PACKET_PREFIX + redPacketId);
        long end = System.currentTimeMillis();
        System.out.println("保存数据结束，耗时" + (end - start) + "毫秒，共" + count + "条记录被保存");
    }

    /**
     * 加载数据
     */
    @PostConstruct
    public void cacheRedPacket() {
        List<RedPacket> allRedPacket = redPacketMapper.getAllRedPacket();
        if (!CollectionUtils.isEmpty(allRedPacket)) {
            allRedPacket.stream().forEach(redPacket -> {
                String key = "red_packet_" + redPacket.getId();
                redisTemplate.opsForHash().put(key, "stock", redPacket.getStock().toString());
                redisTemplate.opsForHash().put(key, "unitAmount", redPacket.getUnitAmount().toString());
            });
        }
    }
}
