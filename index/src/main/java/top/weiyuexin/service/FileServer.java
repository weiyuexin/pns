package top.weiyuexin.service;

import org.springframework.web.multipart.MultipartFile;
import top.weiyuexin.entity.vo.R;
import top.weiyuexin.entity.vo.UploadMsg;


public interface FileServer {
    /**
     * 图片上传接口
     * @param file
     * @return
     */
    R upload(MultipartFile file);
    /**
     * 文件上传接口
     * @param file
     * @return
     */
    R uploadFile(MultipartFile file);
}
