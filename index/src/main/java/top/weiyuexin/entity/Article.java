package top.weiyuexin.entity;

import lombok.Data;

import java.util.Date;
@Data
public class Article {
    private Integer id;

    private Integer authorId;

    private String type;

    private String title;

    private Date time;

    private Integer star;

    private Integer commentNum;

    private Integer readNum;

    private Integer isShow;

    private String content;

}