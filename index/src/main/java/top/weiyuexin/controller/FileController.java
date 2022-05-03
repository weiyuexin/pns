package top.weiyuexin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import top.weiyuexin.entity.vo.R;
import top.weiyuexin.entity.vo.UploadMsg;
import top.weiyuexin.service.FileServer;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class FileController {
    //注入服务层
    @Autowired
    private FileServer fileServer;

    /**
     * 上传图片到腾讯云cos
     * @param file
     * @param session
     * @return
     */
    @PostMapping(value = "/upload")
    @ResponseBody
    public Object Upload(@RequestParam(value = "file") MultipartFile file, HttpSession session){
        //判断文件是否为空
        if(file == null){
            return new R(false,"图片上传失败,请重试!");
        }else {
            //发挥的类型是R
            return fileServer.upload(file);
        }
    }

    /**
     * 专门给Ueditor使用的上传服务
     * @param file
     * @return
     */
    @PostMapping("/uploadimage")
    @ResponseBody
    public Map<String, Object> uploadNewsImg(@RequestParam(value="upfile",required=false) MultipartFile file) {
        R r = new R();
        if(file == null){
            new R(false,"图片上传失败,请重试!");
        }else {
            r = fileServer.upload(file);
        }
        Map<String, Object> result = new HashMap<String,Object>();
        result.put("state", "SUCCESS");
        result.put("url", r.getData());
        result.put("original", "");
        result.put("type", 0);
        result.put("size", 0);
        result.put("title", file.getOriginalFilename());

        return result;
    }


}
