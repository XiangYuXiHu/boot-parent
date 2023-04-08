package com.smile.service;

/**
 * @author smile
 */
public interface UserRedPacketService {
    /**
     * 保存抢红包的信息
     *
     * @param redPacketId
     * @param userId
     * @return
     */
    int grabRedPacket(Long redPacketId, Long userId);

    /**
     * cas
     *
     * @param redPacketId
     * @param userId
     * @return
     */
    int grabRedPacketByVersion(Long redPacketId, Long userId);
}
