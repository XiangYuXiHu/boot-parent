package com.smile.mapper;

import com.smile.domain.UserRedPacket;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author smile
 */
@Mapper
public interface UserRedPacketMapper {
    /**
     * 记录抢红包信息
     *
     * @param userRedPacket
     * @return
     */
    int grabRedPacket(UserRedPacket userRedPacket);
}
