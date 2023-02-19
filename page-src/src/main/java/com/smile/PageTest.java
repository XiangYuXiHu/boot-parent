package com.smile;

import com.smile.entity.Member;
import com.smile.entity.Page;
import com.smile.mapper.MemberMapper;
import com.smile.util.SqlSessionFactoryUtil;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @Description
 * @ClassName main
 * @Author smile
 * @date 2023.02.19 07:46
 */
public class PageTest {

    public static Logger logger = LoggerFactory.getLogger(PageTest.class);

    public static void main(String[] args) {
        SqlSession sqlSession = SqlSessionFactoryUtil.getSqlSession();
        Page page = new Page();
        page.setPageStart(2);
        MemberMapper memberMapper = sqlSession.getMapper(MemberMapper.class);
        List<Member> result = memberMapper.findPage(page);
        System.out.println("总条数:" + page.getTotalSize());
        System.out.println("页:" + result);
    }
}
