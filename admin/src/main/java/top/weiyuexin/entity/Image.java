package top.weiyuexin.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

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
    @TableField(exist = false)
    private String authorName;
}
