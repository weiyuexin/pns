package top.weiyuexin.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;
@Data
public class Resource {
    private Integer id;

    private Integer authorId;

    @TableField(exist = false)
    private String authorName;

    private String type;

    private String title;

    private String content;

    private String link;

    private Integer star;

    private Integer readNum;

    private Integer commentNum;

    private Date time;

    @TableField(exist = false)
    private String date;

    private String icon;

    private String ipAddr;

}