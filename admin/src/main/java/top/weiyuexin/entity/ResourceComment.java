package top.weiyuexin.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;
@Data
public class ResourceComment {
    private Integer id;

    private Integer resourceId;

    private String content;

    private Integer authorId;

    @TableField(exist = false)
    private String authorName;

    private Date time;

    @TableField(exist = false)
    private String date;

    private Integer star;

    private String ipAddr;

}