package top.weiyuexin.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ArticleDaoTestCase {
    @Autowired
    private ArticleMapper articleDao;

    @Test
    void testGetById(){
        System.out.println(articleDao.selectById(68));
    }
}
