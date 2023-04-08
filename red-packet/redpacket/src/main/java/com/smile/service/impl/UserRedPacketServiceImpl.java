package com.smile.service.impl;

import com.smile.domain.RedPacket;
import com.smile.domain.UserRedPacket;
import com.smile.mapper.RedPacketMapper;
import com.smile.mapper.UserRedPacketMapper;
import com.smile.service.UserRedPacketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description
 * @ClassName UserRedPacketServiceImpl
 * @Author smile
 * @date 2022.09.17 17:16
 */
@Service
public class UserRedPacketServiceImpl implements UserRedPacketService {

    @Autowired
    private UserRedPacketMapper userRedPacketMapper;
    @Autowired
    private RedPacketMapper redPacketMapper;

    private static final int FAILED = 0;

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int grabRedPacket(Long redPacketId, Long userId) {
        RedPacket redPacket = redPacketMapper.getRedPacket(redPacketId);
        Integer leftRedPacket = redPacket.getStock();
        if (leftRedPacket > 0) {
            Integer success = redPacketMapper.decreaseRedPacket(redPacketId);
            if (success > 0) {
                /**
                 * 生成抢红包信息
                 */
                UserRedPacket userRedPacket = new UserRedPacket();
                userRedPacket.setRedPacketId(redPacketId);
                userRedPacket.setUserId(userId);
                userRedPacket.setAmount(redPacket.getUnitAmount());
                int result = userRedPacketMapper.grabRedPacket(userRedPacket);
                return result;
            }
        }
        return 0;
    }

    /**
     * cas version
     *
     * @param redPacketId
     * @param userId
     * @return
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int grabRedPacketByVersion(Long redPacketId, Long userId) {
        long start = System.currentTimeMillis();
        while (true) {
            long end = System.currentTimeMillis();
            if ((end - start) > 100) {
                return FAILED;
            }
            RedPacket redPacket = redPacketMapper.getRedPacket(redPacketId);
            Integer stock = redPacket.getStock();
            Integer version = redPacket.getVersion();
            if (stock > 0) {
                Integer success = redPacketMapper.decreaseRedPacketByVersion(redPacketId, version);
                if (success == 0) {
                    continue;
                }
                UserRedPacket userRedPacket = new UserRedPacket();
                userRedPacket.setRedPacketId(redPacketId);
                userRedPacket.setUserId(userId);
                userRedPacket.setAmount(redPacket.getUnitAmount());
                int result = userRedPacketMapper.grabRedPacket(userRedPacket);
                return result;
            } else {
                return FAILED;
            }
        }
    }


}
