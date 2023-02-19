package com.smile.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.SynchronousQueue;


/**
 * @Description
 * @ClassName SqlSessionFactoryUtil
 * @Author smile
 * @date 2023.02.19 07:41
 */
public class SqlSessionFactoryUtil {

    private static volatile SqlSessionFactory sqlSessionFactory = null;
    private static final Class CLASS_LOCK = SqlSessionFactory.class;
    private static Logger logger = LoggerFactory.getLogger(SqlSessionFactoryUtil.class);

    public static SqlSession getSqlSession() {
        if (sqlSessionFactory == null) {
            synchronized (CLASS_LOCK) {
                if (sqlSessionFactory == null) {
                    try {
                        InputStream resourceAsStream = Resources.getResourceAsStream("mybatis-config.xml");
                        sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return sqlSessionFactory.openSession();
    }
}
