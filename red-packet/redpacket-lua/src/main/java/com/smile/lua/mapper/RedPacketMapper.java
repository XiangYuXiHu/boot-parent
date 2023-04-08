package com.smile.lua.mapper;


import com.smile.lua.domain.RedPacket;

import java.util.List;

/**
 * @author smile
 */
public interface RedPacketMapper {

    /**
     * 获取红包信息
     *
     * @return
     */
    List<RedPacket> getAllRedPacket();
}
