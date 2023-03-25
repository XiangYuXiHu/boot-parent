package com.smile.source.code;

import com.smile.source.code.bean.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
class SourceCodeApplicationTests {

    @Resource
    private UserMapper userMapper;

    @Test
    public void contextLoads() {
        System.out.println(userMapper.toString());
    }

}
