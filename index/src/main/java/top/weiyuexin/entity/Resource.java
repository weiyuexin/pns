package top.weiyuexin.entity;

import lombok.Data;

import java.util.Date;
@Data
public class Resource {
    private Integer id;

    private Integer authorId;

    private String type;

    private String title;

    private String content;

    private String link;

    private Integer star;

    private Integer readNum;

    private Integer commentNum;

    private Date time;

    private String icon;

    private String ipAddr;


}