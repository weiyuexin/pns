package top.weiyuexin.entity;

import lombok.Data;

import java.util.Date;
@Data
public class ResourceComment {
    private Integer id;

    private Integer resourceId;

    private String content;

    private Integer authorId;

    private Date time;

    private Integer star;


}