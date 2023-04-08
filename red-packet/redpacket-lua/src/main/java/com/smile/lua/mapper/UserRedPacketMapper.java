package com.smile.lua.mapper;

import com.smile.lua.domain.UserRedPacket;

import java.util.List;

/**
 * @author smile
 */
public interface UserRedPacketMapper {
    /**
     * 记录抢红包信息
     *
     * @param redPackets
     * @return
     */
    int batchInsert(List<UserRedPacket> redPackets);
}
