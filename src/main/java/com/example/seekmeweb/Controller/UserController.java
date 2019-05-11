package com.example.seekmeweb.Controller;


import com.example.seekmeweb.Bean.Notification;
import com.example.seekmeweb.Bean.User;
import com.example.seekmeweb.Result;
import com.example.seekmeweb.Service.DepartmentService;
import com.example.seekmeweb.Service.NotificationService;
import com.example.seekmeweb.Service.TokenJPA;
import com.example.seekmeweb.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private TokenJPA tokenJPA;
    @Autowired
    private NotificationService notificationService;
    public UserController() {
    }

    //通过手机1来查询
    @RequestMapping(value = "/search/check")
    @ResponseBody
    private String login(HttpServletRequest httpServletRequest){
        String phone1 =httpServletRequest.getParameter("phone");
        String password = httpServletRequest.getParameter("password");
        System.out.println("传值为"+phone1);
         User exam =  userService.findByPhone1(phone1);
         if (exam!=null){
             if(exam.getPassword().equals(password))
             return Result.create(1,"查找成功",exam);

         }
         return Result.create(0,"查找失败",null);
    }
    //token验证
    @ResponseBody
    @PostMapping(value = "/search/checktoken")
    private String logint(HttpServletRequest httpServletRequest)

    {
        String token=httpServletRequest.getParameter("token");
        User exam=userService.findByToken(token);
        if (exam!=null){

                return Result.create(1,"查找成功",exam);

        }
        return Result.create(0,"查找失败",null);
    }

    @ResponseBody
    @PostMapping(value = "/search/member")
    public String getMyMember(HttpServletRequest httpServletRequest){
        int belong =Integer.parseInt(httpServletRequest.getParameter("belong"));
        int leaf = Integer.parseInt(httpServletRequest.getParameter("leaf"));
        List<User> myMemberList = new ArrayList<User>();
        if(leaf == 2){
            myMemberList = userService.getBelongAndLeaf1(belong);
        }
        if(leaf == 3) {
            myMemberList = userService.getBelongMember(belong);
        }

        if (myMemberList.size() == 0) {
            return Result.create(0, "查找失败", myMemberList);
        }
        return Result.create(0,"查找成功",myMemberList);
    }


/*-----------------TODO: 丘岳诗代码部分-----------------*/
    //通过电话查询个人信息 实际工程中不使用
    @RequestMapping({"/search/phone"})
    @ResponseBody
    private String getPersonalInformation(HttpServletRequest httpServletRequest) {
        String phone1 = httpServletRequest.getParameter("phone");
        User exam = this.userService.findByPhone1(phone1);
        if (exam!=null){

                return Result.create(1,"查找成功",exam);

        }
        return Result.create(0,"查找失败",null);

    }

    //修改密码
    @RequestMapping({"/change/password"})
    @ResponseBody
    private String changePassword(HttpServletRequest httpServletRequest) {
        String phone = httpServletRequest.getParameter("phone");
        String oldpsd = httpServletRequest.getParameter("oldpsd");
        String newpsd = httpServletRequest.getParameter("newpsd");
        User exam = this.userService.findByPhone1(phone);
        if (exam != null) {
            if (exam.getPassword().equals(oldpsd)) {
                exam.setPassword(newpsd);
                this.userService.save(exam);
                return Result.create(1, "修改成功", null);
            } else if (!exam.getPassword().equals(oldpsd)) {
                return Result.create(0, "原密码错误", null);
            } else return Result.create(0, "其他错误1", null);
        }
        return Result.create(0, "其他错误2", null);
    }

    //修改邮箱
    @RequestMapping({"/change/email"})
    @ResponseBody
    private String changeEmail(HttpServletRequest httpServletRequest) {
        String phone = httpServletRequest.getParameter("phone");
        String email = httpServletRequest.getParameter("email");
        User exam = this.userService.findByPhone1(phone);
        if (exam != null) {
            exam.setMail(email);
            this.userService.save(exam);
            return Result.create(1, "修改成功", null);
        }
        return Result.create(0, "其他错误", null);
    }

    //修改紧急联系人
    @RequestMapping({"/change/emergency"})
    @ResponseBody
    private String changeemergency(HttpServletRequest httpServletRequest) {
        String phone = httpServletRequest.getParameter("phone");
        String ephone = httpServletRequest.getParameter("ephone");

        User exam = this.userService.findByPhone1(phone);
        if (exam != null) {
            exam.setEmergency_contact1(ephone);
            this.userService.save(exam);
            return Result.create(1, "修改成功", null);
        } else return Result.create(0, "其他错误", null);
    }

    //修改紧急联系人
    @RequestMapping({"/change/emergency1"})
    @ResponseBody
    private String changeemergency1(HttpServletRequest httpServletRequest) {
        String phone = httpServletRequest.getParameter("phone");
        String ephone = httpServletRequest.getParameter("ephone");

        User exam = this.userService.findByPhone1(phone);
        if (exam != null) {
            exam.setEmergency_contact2(ephone);
            this.userService.save(exam);
            return Result.create(1, "修改成功", null);
        } else return Result.create(0, "其他错误", null);
    }

    //------------------   以下为对web端的后台支持 ----------------------
    //web 端登陆
    @RequestMapping({"/web/check"})
    @ResponseBody
    private String check_master(HttpServletRequest httpServletRequest) {
        String phone = httpServletRequest.getParameter("phone");
        String psd = httpServletRequest.getParameter("psd");
        String s = phone + psd;
        User exam = this.userService.findByPhone1(phone);
        if (exam != null) {
            if (exam.getPassword().equals(psd)) {
                return Result.create(1, "yes", exam);
            } else return Result.create(-1, "no", null);
        }
        return Result.create(0, "null", null);
    }

    //web 端插入单个职员
    @RequestMapping({"/web/insert_user"})
    @ResponseBody
    private String insertUser(HttpServletRequest httpServletRequest) {
        String name = httpServletRequest.getParameter("name");
//        String psd = httpServletRequest.getParameter("psd");
        String phone1 = httpServletRequest.getParameter("phone1");
        String phone2 = httpServletRequest.getParameter("phone2");

        String email = httpServletRequest.getParameter("email");
        String jobnumber = httpServletRequest.getParameter("jobnumber");
        int belong = Integer.parseInt(httpServletRequest.getParameter("belong"));
        int gender = httpServletRequest.getParameter("gender").equals("男") ? 1 : 0;
        String job = httpServletRequest.getParameter("job");
        String leaf1 = httpServletRequest.getParameter("leaf");
        int leaf = -1;
        if (leaf1.equals("运维人员")) leaf = 1;
        else if (leaf1.equals("运维组长")) leaf = 2;
        else if (leaf1.equals("职能人员")) leaf = 3;
        else if (leaf1.equals("其他")) leaf = 4;
        else leaf = 0;

        User exam = this.userService.findByPhone1(phone1);

        if (exam != null) {
            return Result.create(0, "already", exam);
        } else {
            try {
                User user = new User();
                user.setName(name);
                user.setPassword("123");
                user.setPhone1(phone1);
                user.setPhone2(phone2);
                user.setMail(email);
                user.setJobnumber(jobnumber);
                user.setBelong(belong);
                user.setGender(gender);
                user.setJob(job);
                user.setUserId(jobnumber);
                user.setLeaf(leaf);
                user.setNotificationList(null);
                user.setToken(null);
//                user.setEmergency_contact1(null);
//                user.setEmergency_contact2(null);
                userService.save(user);
                return Result.create(1, "success", exam);
            } catch (Exception e) {
                return Result.create(-1, "false", exam);
            }
        }
    }
    //web 端获取所有职员信息
    @RequestMapping({"/web/all_user"})
    @ResponseBody
    private List<User> all_user(){
        return userService.findAll();
    }

    @ResponseBody
    @RequestMapping(value = "/liu")
    String missLiu(){
        return "Hello Miss Liu. ";
    }
    //添加通知
    @RequestMapping({"/noticeadd"})
    @ResponseBody
    private String addnotic(@RequestParam String userId, @RequestParam String notificationId){
        User user=userService.findByUserId(userId);
        int id=Integer.valueOf(notificationId);
        Notification notification=notificationService.findOne(id);

        List<Notification> notificationList=user.getNotificationList();
        notificationList.add(notification);
        user.setNotificationList(notificationList);
        userService.save(user);
        return Result.create(1,"添加成功",null);

    }
    @RequestMapping({"/getnotice"})
    @ResponseBody
    private String getnotic(@RequestParam String userId)
    {
        User user=userService.findByUserId(userId);
        System.out.println(user);
        List<Notification> notificationList=user.getNotificationList();
        System.out.println(notificationList);

        if(notificationList!=null&&notificationList.size()!=0) {

            return Result.create(1, "成功得到通知", notificationList);
        }else return Result.create(0, "未得到通知", null);
    }
    //修改用户信息
    @RequestMapping({"/update/user"})
    @ResponseBody
    private String updateuser(HttpServletRequest httpServletRequest) {
        String name = httpServletRequest.getParameter("name");
        int gender =Integer.parseInt(httpServletRequest.getParameter("gender"));
        String job=httpServletRequest.getParameter("job");
        String jobnumber=httpServletRequest.getParameter("jobnumber");
        int leaf=Integer.parseInt(httpServletRequest.getParameter("leaf"));
        int belong=Integer.parseInt(httpServletRequest.getParameter("belong"));
        String phone1=httpServletRequest.getParameter("phone1");
        String phone2=httpServletRequest.getParameter("phone2");
        String mail=httpServletRequest.getParameter("mail");
        String password=httpServletRequest.getParameter("password");

        User exam = this.userService.findByPhone1(phone1);
        if (exam != null) {
            exam.setName(name);
            exam.setGender(gender);
            exam.setJob(job);
            exam.setJobnumber(jobnumber);
            exam.setLeaf(leaf);
            exam.setBelong(belong);
            exam.setPhone1(phone1);
            exam.setPhone2(phone2);
            exam.setMail(mail);
            exam.setPassword(password);
            exam.setToken(null);
            this.userService.save(exam);
            return Result.create(1, "修改成功", null);
        } else return Result.create(0, "其他错误", null);
    }

    //删除用户信息
    @RequestMapping({"/delete/user"})
    @ResponseBody
    private String deleteuser(HttpServletRequest httpServletRequest) {
        String phone1 = httpServletRequest.getParameter("phone1");
        User exam = this.userService.findByPhone1(phone1);
        if(exam!=null){
            userService.delete(exam);
            return Result.create(1, "删除成功", null);
        }else return Result.create(0, "删除失败", null);
    }
    @RequestMapping({"/testid"})
    @ResponseBody
    private String testuser(HttpServletRequest httpServletRequest) {
        String phone1 = httpServletRequest.getParameter("userid");
        User exam = this.userService.findByUserId(phone1);
        if(exam!=null){

            return Result.create(1, "成功", exam);
        }else return Result.create(0, "失败", null);
    }


}
