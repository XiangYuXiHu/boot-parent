package com.smile.storage.mapper;

import com.smile.storage.domain.Storage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author smile
 */
@Mapper
public interface StorageMapper {


    /**
     * 根据id，查询单条记录
     *
     * @param productId
     * @return
     */
    Storage findOneByProductId(@Param("productId") Long productId);

    /**
     * 冻结库存
     *
     * @param productId
     * @param residue
     * @param frozen
     */
    void updateFrozen(@Param("productId") Long productId, @Param("residue") Integer residue, @Param("frozen") Integer frozen);

    /**
     * 提交时，把冻结量修改到已售出
     *
     * @param productId
     * @param count
     */
    void updateFrozenToUsed(@Param("productId") Long productId, @Param("count") Integer count);

    /**
     * 回滚时，把冻结量修改到可用库存
     *
     * @param productId
     * @param count
     */
    void updateFrozenToResidue(@Param("productId") Long productId, @Param("count") Integer count);
}
