package com.smile.storage.service;

/**
 * @author 12780
 */
public interface StorageService {
    /**
     * 扣减库存
     *
     * @param productId
     * @param count
     */
    void decrease(Long productId, Integer count);
}
