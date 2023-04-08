package com.smile.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description
 * @ClassName UserRedPacket
 * @Author smile
 * @date 2023.04.08 15:55
 */
public class UserRedPacket implements Serializable {

    /**
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
     * 抢红包金额
     */
    private double amount;

    /**
     * 抢红包的时间
     */
    private Date createTime;

    /**
     * 备注
     */
    private String note;

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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "UserRedPacket{" +
                "id=" + id +
                ", redPacketId=" + redPacketId +
                ", userId=" + userId +
                ", amount=" + amount +
                ", createTime=" + createTime +
                ", note='" + note + '\'' +
                '}';
    }
}
