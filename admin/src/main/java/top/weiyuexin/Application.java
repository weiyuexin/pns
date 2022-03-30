package top.weiyuexin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//SpringBoot项目启动入口类
@SpringBootApplication  //SpringBoot核心注解，主要用于开启spring自动配置
@MapperScan(basePackages = "top.weiyuexin.Mapper")  //开启扫描Mapper接口的包及子目录
public class Application {

    //自己写的代码必须写在Application类同级或下一级目录
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
