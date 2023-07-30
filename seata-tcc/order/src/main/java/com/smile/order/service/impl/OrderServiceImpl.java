package com.smile.order.service.impl;

import com.smile.order.client.AccountClient;
import com.smile.order.client.StorageClient;
import com.smile.order.tcc.OrderTccAction;
import com.smile.order.request.OrderRequest;
import com.smile.order.service.OrderService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Description
 * @ClassName OrderServiceImpl
 * @Author smile
 * @date 2023.06.21 23:16
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderTccAction orderTccAction;

    @Autowired
    private AccountClient accountClient;

    @Autowired
    private StorageClient storageClient;


    @GlobalTransactional
    @Override
    public void createOrder(OrderRequest orderRequest) {
        String orderNo = generateOrderNo();
        orderTccAction.prepareCreateOrder(null, orderNo,
                orderRequest.getUserId(),
                orderRequest.getProductId(),
                orderRequest.getAmount(),
                orderRequest.getMoney());
        //扣余额
        accountClient.decreaseMoney(orderRequest.getUserId(), orderRequest.getMoney());
        //扣库存
        storageClient.decreaseStorage(orderRequest.getProductId(), orderRequest.getAmount());
    }


    private String generateOrderNo() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmmssSSS"));
    }
}
