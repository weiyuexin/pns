package top.weiyuexin.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserDaoTestCase {
    @Autowired
    private UserMapper userDao;

    @Test
    void testGetById(){
        System.out.println(userDao.selectById(29));
    }
}
