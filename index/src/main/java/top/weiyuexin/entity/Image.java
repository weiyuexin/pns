package top.weiyuexin.entity;

import lombok.Data;

import java.util.Date;

/**
 * 图片实体类
 */
@Data
public class Image {
    private Integer id;
    private String originName;
    private String url;
    private String time;
    private Integer authorId;
}
