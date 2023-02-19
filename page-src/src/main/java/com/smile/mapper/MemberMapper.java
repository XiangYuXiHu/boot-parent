package com.smile.mapper;

import com.smile.entity.Page;
import com.smile.entity.Member;

import java.util.List;

/**
 * @Description
 * @ClassName UserMapper
 * @Author smile
 * @date 2023.02.19 07:29
 */
public interface MemberMapper {

    /**
     * 分页查询
     *
     * @param page
     * @return
     */
    List<Member> findPage(Page page);
}
