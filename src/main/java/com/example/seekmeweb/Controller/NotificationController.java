package com.example.seekmeweb.Controller;


import com.example.seekmeweb.Bean.Notification;
import com.example.seekmeweb.Result;
import com.example.seekmeweb.Service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;


    @ResponseBody
    @RequestMapping(value = "/add")       //增操作
    public String add(HttpServletRequest httpServletRequest){

        Notification notification =new Notification();

        notification.setSendername(httpServletRequest.getParameter("sendername"));
        notification.setNotiday(httpServletRequest.getParameter("notiday"));
        notification.setNotitime(httpServletRequest.getParameter("notitime"));
        notification.setSendday(httpServletRequest.getParameter("sendday"));
        notification.setSendtime(httpServletRequest.getParameter("sendtime"));
        notification.setTitle(httpServletRequest.getParameter("title"));
        notification.setContent(httpServletRequest.getParameter("content"));

        notificationService.save(notification);
        return Result.create(0,"添加记录成功",notification);
    }

    @ResponseBody
    @RequestMapping(value = "/getall")
    public String getAll(){
        List<Notification> recordList = notificationService.findAll();
        if(recordList.size()==0)
        {
            return Result.create(0,"查找失败",recordList);
        }

        return Result.create(0,"查找成功",recordList);
    }


    @ResponseBody
    @RequestMapping(value = "/b")
    String test(){
        return "hello notification";
    }

}
