package com.simle.mapper;

import com.simle.domain.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * @author 12780
 */
@Mapper
public interface AccountMapper {

    /**
     * 根据用户id查询单行
     *
     * @param userId
     * @return
     */
    Account findOneByUserId(Long userId);

    /**
     * 冻结钱
     *
     * @param userId  用户id
     * @param residue 剩余额度
     * @param frozen  冻结额度
     */
    void updateFrozen(@Param("userId") Long userId, @Param("residue") BigDecimal residue, @Param("frozen") BigDecimal frozen);

    /**
     * 将冻结的钱，转换为已使用
     *
     * @param userId
     * @param money
     */
    void updateFrozenToUsed(@Param("userId") Long userId, @Param("money") BigDecimal money);

    /**
     * 将冻结的钱，还原为库存
     *
     * @param userId
     * @param money
     */
    void updateFrozenToResidue(@Param("userId") Long userId, @Param("money") BigDecimal money);
}
