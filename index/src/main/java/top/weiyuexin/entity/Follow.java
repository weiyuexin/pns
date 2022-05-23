package top.weiyuexin.entity;

import lombok.Data;

import java.util.Date;
@Data
public class Follow {
    private Integer id;
    private Integer followerId;
    private Integer followedId;
    private Date time;
}
