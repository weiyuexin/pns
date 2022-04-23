package top.weiyuexin.service;

import org.springframework.web.multipart.MultipartFile;
import top.weiyuexin.entity.vo.UploadMsg;


public interface FileServer {
    /*
    文件上传接口
     */
    UploadMsg upload(MultipartFile file);
}
