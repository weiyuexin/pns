package top.weiyuexin.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;

@Data
public class LoginLog {
    private Integer id;
    private Integer userId;
    @TableField(exist = false)
    private String username;
    private Date time;
    @TableField(exist = false)
    private String date;
    private String ip;
    private String address;
    private String country;
    private String province;
    private String city;
}
