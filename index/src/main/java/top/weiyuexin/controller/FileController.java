package top.weiyuexin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import top.weiyuexin.entity.vo.UploadMsg;
import top.weiyuexin.service.FileServer;

import javax.servlet.http.HttpSession;

@Controller
public class FileController {
    //注入服务层
    @Autowired
    private FileServer fileServer;

    /**
     * 上传到腾讯云OSS
     * @param file
     * @param session
     * @return
     */
    @PostMapping(value = "/upload")
    @ResponseBody
    public Object Upload(@RequestParam(value = "file") MultipartFile file, HttpSession session){
        //判断文件是否为空
        if(file == null){
            return new UploadMsg(0,"文件为空",null);
        }
        UploadMsg uploadMsg = new UploadMsg(0,"","");
        //调用UploadServer提供的方法上传文件
        uploadMsg = fileServer.upload(file);
        return uploadMsg;
    }


}
