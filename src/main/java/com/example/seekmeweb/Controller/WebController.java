package com.example.seekmeweb.Controller;

import com.example.seekmeweb.Bean.Web;
import com.example.seekmeweb.Result;
import com.example.seekmeweb.Service.WebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping({"/web"})
public class WebController {
    @Autowired
    private WebService webService;

    private WebController() {
    }

    @RequestMapping({"/test"})
    @ResponseBody
    private String test() {
        return "hello web test!";
    }

    @RequestMapping({"/insert_web_user"})
    @ResponseBody
    private String articleInsert(HttpServletRequest httpServletRequest) {
        String username = httpServletRequest.getParameter("username");
        String password = httpServletRequest.getParameter("password");

        Web exam = this.webService.findByUsername(username);
        if (exam != null) {
            return Result.create(0, "already", null);
        } else {
            try {
                Web web1 = new Web();
                web1.setUsername(username);
                web1.setPassword(password);
                webService.save(web1);
                return Result.create(1, "success", null);
            } catch (Exception e) {
                return Result.create(0, "false", null);
            }
        }
    }

    @RequestMapping({"/webuser_all"})
    @ResponseBody
    public List<Web> departmentList() {
        return webService.findAll();
    }

    @RequestMapping({"/check"})
    @ResponseBody
    private String check_master(HttpServletRequest httpServletRequest) {
        String username = httpServletRequest.getParameter("username");
        String password = httpServletRequest.getParameter("password");
        Web exam = this.webService.findByUsername(username);
        if (exam != null) {
            if (exam.getPassword().equals(password)) {
                return Result.create(1, "yes", exam);
            } else return Result.create(-1, "no", null);
        }
        return Result.create(0, "null", null);
    }

    @RequestMapping({"/update_username"})
    @ResponseBody
    private String update_username(HttpServletRequest httpServletRequest) {
        String username = httpServletRequest.getParameter("username");
        String password = httpServletRequest.getParameter("password");
        String newusername = httpServletRequest.getParameter("newusername");
        Web exam = this.webService.findByUsername(username);
        if (exam != null) {
            if (exam.getPassword().equals(password)) {
                try{
                    exam.setUsername(newusername);
                    this.webService.save(exam);
                    return Result.create(1, "update success", exam);
                }catch (Exception e){
                    return Result.create(-1, "update false", exam);
                }
            }else return Result.create(-1, "password error", null);
        }else if(exam == null) {
            return Result.create(0, "no user", null);
        }else return Result.create(-1, "unknow error", null);

    }

    @RequestMapping({"/update_password"})
    @ResponseBody
    private String update_password(HttpServletRequest httpServletRequest) {
        String username = httpServletRequest.getParameter("username");
        String password = httpServletRequest.getParameter("password");
        String newpassword = httpServletRequest.getParameter("newpassword");
        Web exam = this.webService.findByUsername(username);
        if (exam != null) {
            if (exam.getPassword().equals(password)) {
                try{
                    exam.setPassword(newpassword);
                    this.webService.save(exam);
                    return Result.create(1, "update success", exam);
                }catch (Exception e){
                    return Result.create(-1, "update false", exam);
                }
            }else return Result.create(-1, "password error", null);
        }else if(exam == null) {
            return Result.create(0, "no user", null);
        }else return Result.create(-1, "unknow error", null);
    }

    @RequestMapping({"/delete_user"})
    @ResponseBody
    private String deleteuser(HttpServletRequest httpServletRequest) {
        String username = httpServletRequest.getParameter("username");
        Web exam = this.webService.findByUsername(username);
        if(exam!=null){
            webService.delete(exam);
            return Result.create(1, "删除成功", null);
        }else return Result.create(0, "删除失败", null);
    }

    @RequestMapping({"/find_user"})
    @ResponseBody
    private String find_user(HttpServletRequest httpServletRequest) {
        String username = httpServletRequest.getParameter("username");
        Web exam = this.webService.findByUsername(username);
        if(exam!=null)
            return Result.create(1, "got it", exam);
        else return Result.create(1,"null",null);
    }
}
