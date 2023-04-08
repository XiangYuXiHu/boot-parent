package com.smile.lua.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description
 * @ClassName UserRedPacket
 * @Author smile
 * @date 2022.09.17 16:37
 */
public class UserRedPacket implements Serializable {

    /***
     * 用户红包id
     */
    private Long id;
    /**
     * 红包id
     */
    private Long redPacketId;
    /**
     * 抢红包的用户id
     */
    private Long userId;
    /**
     * 抢红包的金额
     */
    private Double amount;
    /**
     * 备注
     */
    private String note;
    /**
     * 抢红包的时间
     */
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRedPacketId() {
        return redPacketId;
    }

    public void setRedPacketId(Long redPacketId) {
        this.redPacketId = redPacketId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "UserRedPacket{" +
                "id=" + id +
                ", redPacketId=" + redPacketId +
                ", userId=" + userId +
                ", amount=" + amount +
                ", note='" + note + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
