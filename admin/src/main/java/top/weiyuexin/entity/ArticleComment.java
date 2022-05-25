package top.weiyuexin.entity;

import lombok.Data;

import java.util.Date;
@Data
public class ArticleComment {
    private Integer id;

    private Integer articleId;

    private String content;

    private Integer authorId;

    private Date time;

    private Integer star;

}