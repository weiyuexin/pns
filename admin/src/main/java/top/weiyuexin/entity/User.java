package top.weiyuexin.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;
//lombok
@Data
public class User {
    private Integer id;

    private String username;

    private String password;

    private String email;

    private String sex;

    private Integer points;

    private String phone;

    private String signature;

    private Date time;

    @TableField(exist = false)
    private String date;

    private String address;

    private String photo;

}