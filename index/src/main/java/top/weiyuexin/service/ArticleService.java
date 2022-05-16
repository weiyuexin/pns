package top.weiyuexin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import top.weiyuexin.entity.Article;

import java.util.List;

//文章服务
public interface ArticleService extends IService<Article> {
    //自定义方法

    /**
     * 分页查询
     * @param currentPage
     * @param pageSize
     * @param article
     * @return
     */
    IPage<Article> getPage(Integer currentPage,Integer pageSize,Article article);
    /**
     * 查询热门文章
     * @param num
     * @return
     */
    List<Article> getTopArticle(Integer num);

    /**
     * 根据标签查询文章
     * @param currentPage
     * @param pageSize
     * @param type
     * @param article
     * @return
     */
    IPage<Article> getPageByType(Integer currentPage,Integer pageSize,String type,Article article);
}
