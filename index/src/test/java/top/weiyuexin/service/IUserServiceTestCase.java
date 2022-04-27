package top.weiyuexin.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class IUserServiceTestCase {
    @Autowired
    private IUserService userService;

    @Test
    void testGetById(){
        userService.getById(29);
    }
}
