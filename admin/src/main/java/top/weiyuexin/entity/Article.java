package top.weiyuexin.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;
@Data
public class Article {
    private Integer id;

    private Integer authorId;
    /**
     * 该字段不是数据库字段
     */
    @TableField(exist = false)
    private String authorName;

    private String type;

    private String title;

    private Date time;

    @TableField(exist = false)
    private String date;

    private Integer star;

    private Integer commentNum;

    private Integer readNum;

    private Integer isShow;

    private String content;

    private String ipAddr;

}