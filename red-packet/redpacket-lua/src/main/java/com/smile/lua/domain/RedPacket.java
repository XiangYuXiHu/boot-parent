package com.smile.lua.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description
 * @ClassName RedPacket
 * @Author smile
 * @date 2022.09.17 16:35
 */
public class RedPacket implements Serializable {

    /**
     * 红包编号
     */
    private Long id;
    /***
     * 发红包金额
     */
    private Double amount;
    /**
     * 红包总数
     */
    private Integer total;
    /**
     * 单个红包金额
     */
    private Double unitAmount;
    /**
     * 红包剩余个数
     */
    private Integer stock;
    /**
     * 版本
     */
    private Integer version;
    /**
     * 备注
     */
    private String note;
    /**
     * 发红包日期
     */
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Double getUnitAmount() {
        return unitAmount;
    }

    public void setUnitAmount(Double unitAmount) {
        this.unitAmount = unitAmount;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
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
        return "RedPacket{" +
                "id=" + id +
                ", amount=" + amount +
                ", total=" + total +
                ", unitAmount=" + unitAmount +
                ", stock=" + stock +
                ", version=" + version +
                ", note='" + note + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
