package top.weiyuexin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.weiyuexin.mapper.UserMapper;

@SpringBootTest
class IndexApplicationTests {

    @Autowired
    private UserMapper userMapper;
    @Test
    void contextLoads() {
        System.out.println(userMapper.selectByPrimaryKey(26));
    }

}
