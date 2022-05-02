package top.weiyuexin.entity.vo;

import lombok.Data;

@Data
public class R {
    private Boolean flag;
    private Object data;
    private String msg;
    public R(){

    }
    public R(Boolean flag){
        this.flag = flag;
    }
    public R(Boolean flag, Object data){
        this.flag = flag;
        this.data = data;
    }
    public R(Boolean flag, String msg){
        this.flag = flag;
        this.msg = msg;
    }
    public R(Boolean flag, Object data, String msg){
        this.flag = flag;
        this.data = data;
        this.msg = msg;
    }
    //抛出异常时使用
    public R(String msg){
        this.msg = msg;
    }
}
