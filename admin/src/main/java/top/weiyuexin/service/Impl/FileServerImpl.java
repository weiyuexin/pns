package top.weiyuexin.service.Impl;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.weiyuexin.entity.Image;
import top.weiyuexin.entity.User;
import top.weiyuexin.entity.vo.R;
import top.weiyuexin.service.FileServer;
import top.weiyuexin.service.ImageService;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class FileServerImpl implements FileServer {
    @Value("${spring.tencent.secretId}")
    private String secretId;
    @Value("${spring.tencent.secretKey}")
    private String secretKey;
    @Value("${spring.tencent.bucket}")
    private String bucket;
    @Value("${spring.tencent.bucketName}")
    private String bucketName;
    @Value("${spring.tencent.path}")
    private String path;
    @Value("${spring.tencent.qianzui}")
    private String qianzui;
    @Value("${spring.tencent.qianzui-file}")
    private String qianzui_file;

    @Autowired
    private ImageService imageService;

    /**
     * 上传图片到腾讯云cos实现方法
     * @param file
     * @return
     */
    @Override
    public R upload(MultipartFile file,HttpSession session) {
        String oldFileName = file.getOriginalFilename();
        String eName = oldFileName.substring(oldFileName.lastIndexOf("."));
        String newFileName = UUID.randomUUID()+eName;
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month=cal.get(Calendar.MONTH)+1;
        int day=cal.get(Calendar.DATE);
        // 1 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        // 2 设置bucket的区域, COS地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region(bucket));
        // 3 生成cos客户端
        COSClient cosclient = new COSClient(cred, clientConfig);
        // bucket的命名规则为{name}-{appid} ，此处填写的存储桶名称必须为此格式
        String bucketName = this.bucketName;

        // 简单文件上传, 最大支持 5 GB, 适用于小文件上传, 建议 20 M 以下的文件使用该接口
        // 大文件上传请参照 API 文档高级 API 上传
        File localFile = null;
        try {
            localFile = File.createTempFile("temp",null);
            file.transferTo(localFile);
            // 指定要上传到 COS 上的路径
            String key = "/"+this.qianzui+"/"+year+"/"+month+"/"+day+"/"+year+month+day+newFileName;
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
            PutObjectResult putObjectResult = cosclient.putObject(putObjectRequest);

            //将图片信息保存到数据库
            Image image = new Image();
            image.setOriginName(oldFileName);
            image.setUrl(this.path + putObjectRequest.getKey());
            image.setTime(year+"年"+month+"月"+day+"日");
            User user = (User) session.getAttribute("user");
            if(user!=null){
                image.setAuthorId(user.getId());
            }
            imageService.save(image);

            return new R(true,this.path + putObjectRequest.getKey(),"图片上传成功！");
        } catch (IOException e) {
            return new R(false,"图片上传失败!");
        }finally {
            // 关闭客户端(关闭后台线程)
            cosclient.shutdown();
        }
    }

    /**
     * 上传文件到腾讯云cos实现方法
     * @param file
     * @return
     */
    @Override
    public R uploadFile(MultipartFile file) {
        String oldFileName = file.getOriginalFilename();
        String eName = oldFileName.substring(oldFileName.lastIndexOf("."));
        String newFileName = UUID.randomUUID()+eName;
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month=cal.get(Calendar.MONTH)+1;
        int day=cal.get(Calendar.DATE);
        // 1 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        // 2 设置bucket的区域, COS地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region(bucket));
        // 3 生成cos客户端
        COSClient cosclient = new COSClient(cred, clientConfig);
        // bucket的命名规则为{name}-{appid} ，此处填写的存储桶名称必须为此格式
        String bucketName = this.bucketName;

        // 简单文件上传, 最大支持 5 GB, 适用于小文件上传, 建议 20 M 以下的文件使用该接口
        // 大文件上传请参照 API 文档高级 API 上传
        File localFile = null;
        try {
            localFile = File.createTempFile("temp",null);
            file.transferTo(localFile);
            // 指定要上传到 COS 上的路径
            String key = "/"+this.qianzui_file+"/"+year+"/"+month+"/"+day+"/"+year+month+day+newFileName;
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
            PutObjectResult putObjectResult = cosclient.putObject(putObjectRequest);

            Map<String,String> fileMessage = new HashMap<>();
            fileMessage.put("filename",oldFileName);
            fileMessage.put("url",this.path + putObjectRequest.getKey());
            return new R(true,fileMessage,"图片上传成功！");
        } catch (IOException e) {
            return new R(false,"图片上传失败!");
        }finally {
            // 关闭客户端(关闭后台线程)
            cosclient.shutdown();
        }
    }
}
