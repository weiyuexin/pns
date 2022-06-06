package top.weiyuexin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import top.weiyuexin.entity.Article;
import top.weiyuexin.entity.Image;
import top.weiyuexin.entity.User;
import top.weiyuexin.entity.vo.R;
import top.weiyuexin.entity.vo.W;
import top.weiyuexin.service.ImageService;
import top.weiyuexin.service.UserService;
import top.weiyuexin.utils.OutHtml;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
public class ImageController {
    @Autowired
    private ImageService imageService;
    @Autowired
    private UserService userService;

    /**
     * 分页查询图片
     *
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("/images")
    @ResponseBody
    public W getPage(@RequestParam("page") Integer page,
                     @RequestParam("limit") Integer limit,
                     Image image) {
        if (!image.getAuthorName().equals("")) {
            //查询要查询的作者id
            System.out.println("author:" + image.getAuthorName());
            User user1 = userService.getByUserName(image.getAuthorName());
            if (user1 != null) {
                image.setAuthorId(user1.getId());
            } else {
                image.setAuthorId(-1);
            }
        }
        IPage<Image> Ipage = imageService.getPage(page, limit, image);
        //如果当前页码值大于当前页码值，那么重新执行查询操作，使用最大页码值作为当前页码值
        if (page > Ipage.getPages()) {
            Ipage = imageService.getPage(page, limit, image);
        }
        List<Image> images = Ipage.getRecords();
        for (int i = 0; i < images.size(); i++) {
            //根据作者id查询作者
            User user = userService.getById(images.get(i).getAuthorId());
            if (user != null) {
                images.get(i).setAuthorName(user.getUsername());
            } else {
                images.get(i).setAuthorName("佚名");
            }
        }
        Ipage.setRecords(images);
        return new W(0, (int) Ipage.getTotal(), Ipage.getRecords());
    }

    /**
     * 删除图片
     *
     * @param id
     * @param session
     * @return
     */
    @DeleteMapping("/image/del/{id}")
    @ResponseBody
    public R deleteImage(@PathVariable("id") Integer id, HttpSession session) {
        R r = new R();
        //判断用户是否登录
        if (session.getAttribute("user") != null) {
            r.setFlag(imageService.removeById(id));
            r.setMsg("图片删除成功");
        } else {
            r.setData("当前登录状态丢失，请重新登录后再试!");
        }
        return r;
    }
}
