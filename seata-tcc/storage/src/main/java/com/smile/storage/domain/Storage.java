package com.smile.storage.domain;

/**
 * @Description
 * @ClassName Storage
 * @Author smile
 * @date 2023.07.30 07:54
 */
public class Storage {

    private Long id;

    /**
     * 产品id
     */
    private Long productId;

    /**
     * 剩余库存
     */
    private Integer residue;

    /**
     * tcc事务锁定条件
     */
    private Integer frozen;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getResidue() {
        return residue;
    }

    public void setResidue(Integer residue) {
        this.residue = residue;
    }

    public Integer getFrozen() {
        return frozen;
    }

    public void setFrozen(Integer frozen) {
        this.frozen = frozen;
    }
}
