package com.smile.mapper;

import com.smile.domain.RedPacket;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author smile
 */
@Mapper
public interface RedPacketMapper {

    /**
     * 获取红包信息
     *
     * @param id
     * @return
     */
    RedPacket getRedPacket(Long id);

    /**
     * 扣减红包
     *
     * @param id
     * @return
     */
    Integer decreaseRedPacket(Long id);

    /**
     * cas version
     *
     * @param id
     * @param version
     * @return
     */
    Integer decreaseRedPacketByVersion(@Param("id") Long id, @Param("version") Integer version);
}
