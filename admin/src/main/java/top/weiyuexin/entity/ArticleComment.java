package top.weiyuexin.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;
@Data
public class ArticleComment {
    private Integer id;

    private Integer articleId;

    @TableField(exist = false)
    private String authorName;

    private String content;

    private Integer authorId;

    private Date time;

    @TableField(exist = false)
    private String date;

    private Integer star;

}