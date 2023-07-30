package com.smile.storage.tcc;

import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

/**
 * @author smile
 */
@LocalTCC
public interface StorageTccAction {

    /**
     * try
     *
     * @param businessActionContext
     * @param productId
     * @param count
     * @return
     */
    @TwoPhaseBusinessAction(name = "storageTccAction")
    boolean prepareDecreaseStorage(BusinessActionContext businessActionContext,
                                   @BusinessActionContextParameter(paramName = "productId") Long productId,
                                   @BusinessActionContextParameter(paramName = "count") Integer count);

    /**
     * commit
     *
     * @param businessActionContext
     * @return
     */
    boolean commit(BusinessActionContext businessActionContext);

    /**
     * rollback
     *
     * @param businessActionContext
     * @return
     */
    boolean rollback(BusinessActionContext businessActionContext);
}
