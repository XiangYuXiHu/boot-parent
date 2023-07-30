package com.smile.order.tcc;

import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

import java.math.BigDecimal;

/**
 * @author smile
 */
@LocalTCC
public interface OrderTccAction {

    /**
     * try 尝试
     * <p>
     * BusinessActionContext 上下文对象，用来在两个阶段之间传递数据
     * BusinessActionContextParameter 注解的参数数据会被存入 BusinessActionContext
     * TwoPhaseBusinessAction 注解中commitMethod、rollbackMethod 属性有默认值，可以不写
     *
     * @param businessActionContext
     * @param orderNo
     * @param userId
     * @param productId
     * @param amount
     * @param money
     * @return
     */
    @TwoPhaseBusinessAction(name = "orderTccAction")
    boolean prepareCreateOrder(BusinessActionContext businessActionContext,
                               @BusinessActionContextParameter(paramName = "orderNo") String orderNo,
                               @BusinessActionContextParameter(paramName = "userId") Long userId,
                               @BusinessActionContextParameter(paramName = "productId") Long productId,
                               @BusinessActionContextParameter(paramName = "amount") Integer amount,
                               @BusinessActionContextParameter(paramName = "money") BigDecimal money);

    /**
     * 提交
     *
     * @param businessActionContext
     * @return
     */
    boolean commit(BusinessActionContext businessActionContext);

    /**
     * 撤销
     *
     * @param businessActionContext
     * @return
     */
    boolean rollback(BusinessActionContext businessActionContext);
}
