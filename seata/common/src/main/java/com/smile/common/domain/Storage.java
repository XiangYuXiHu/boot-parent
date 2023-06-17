package com.smile.common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 12780
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Storage {

    private Long id;

    /**
     * 产品id
     */
    private Long productId;

    /**
     * 总库存
     */
    private Integer total;

    /**
     * 已用库存
     */
    private Integer used;

    /**
     * 剩余库存
     */
    private Integer residue;

    @Override
    public String toString() {
        return "Storage{" +
                "id=" + id +
                ", productId=" + productId +
                ", total=" + total +
                ", used=" + used +
                ", residue=" + residue +
                '}';
    }
}
