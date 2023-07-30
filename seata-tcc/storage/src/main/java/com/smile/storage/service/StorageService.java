package com.smile.storage.service;

/**
 * @author smile
 */
public interface StorageService {

    /**
     * 扣库存
     *
     * @param productId
     * @param count
     * @return
     */
    void decreaseStorage(Long productId, Integer count);
}
