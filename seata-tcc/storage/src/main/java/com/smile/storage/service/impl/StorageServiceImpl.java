package com.smile.storage.service.impl;

import com.smile.storage.service.StorageService;
import com.smile.storage.tcc.StorageTccAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @ClassName StorageServiceImpl
 * @Author smile
 * @date 2023.07.30 08:33
 */
@Service
public class StorageServiceImpl implements StorageService {

    @Autowired
    private StorageTccAction storageTccAction;

    @Override
    public void decreaseStorage(Long productId, Integer count) {
        storageTccAction.prepareDecreaseStorage(null, productId, count);
    }
}
