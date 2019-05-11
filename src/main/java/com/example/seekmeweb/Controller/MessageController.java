package com.example.seekmeweb.Controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.example.seekmeweb.Bean.ClientCard;
import com.example.seekmeweb.Bean.Message;
import com.example.seekmeweb.Bean.PushHistory;
import com.example.seekmeweb.Bean.User;
import com.example.seekmeweb.Push.PushDispatcher;
import com.example.seekmeweb.Result;
import com.example.seekmeweb.Service.ClientIdService;
import com.example.seekmeweb.Service.MessageService;
import com.example.seekmeweb.Service.PushHistoryService;
import com.example.seekmeweb.Service.UserService;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.style.Style0;
import com.mysql.fabric.xmlrpc.base.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;
    @Autowired
    private ClientIdService clientIdService;
    @Autowired
    private PushHistoryService pushHistoryService;

    private static final String appKey = "yKSMrWeoSL8NF9B4GAzWH6";
    private static final String masterSecret = "eqvB8ZAiK87HVlvMuaZv33";
    private static final String appId=  "dlC8dcAu2C7xYEC92otrn8";
    private static final String host = "http://sdk.open.api.igexin.com/apiex.htm";
    @RequestMapping(value = "/search")
    @ResponseBody
    private String record(HttpServletRequest httpServletRequest){

        String sentid =httpServletRequest.getParameter("sentid");
        String receiveid = httpServletRequest.getParameter("receiveid");
        int type=Integer.parseInt(httpServletRequest.getParameter("type"));
        List<Message> messageList=new ArrayList<Message>();
        messageList=messageService.getRecord(sentid,receiveid,type);
        if(messageService.getRecord(sentid,receiveid,type)!=null){
            if (messageList.size() == 0) {
                return Result.create(0, "查找失败", null);
            }
            else return Result.create(1,"查找成功",messageList);
        }else return Result.create(0, "查找失败", null);

    }
    @RequestMapping(value = "/saveandpush")
    @ResponseBody
    private String messave(HttpServletRequest httpServletRequest){
        User receiveuser =userService.findByUserId(httpServletRequest.getParameter("receiveid"));
        User sentuser=userService.findByUserId(httpServletRequest.getParameter("sentid"));
        if(receiveuser==null){
            return Result.create(0,"联系人不存在",null);
        }

        Message message=new Message();

        message.setAttach(httpServletRequest.getParameter("attach"));
        message.setSentid(httpServletRequest.getParameter("sentid"));
        message.setReceiveid(httpServletRequest.getParameter("receiveid"));
        message.setId(httpServletRequest.getParameter("id"));

        message.setCreateTime(new Date());
        message.setContent(httpServletRequest.getParameter("content"));

        message.setType(Integer.parseInt(httpServletRequest.getParameter("type")));
        message.setUpdatetime(new Date());
        messageService.save(message);
        pushNewMessage(receiveuser,sentuser,message);
        return Result.create(1,"存",message);



    }
    public void pushNewMessage(User receiveuser,User sentuser, Message message) {
        ClientCard clientCard=clientIdService.findByUserId(message.getReceiveid());
        if (receiveuser == null || message == null)
            return;
        IGtPush push = new IGtPush(host, appKey, masterSecret);
        PushDispatcher dispatcher = new PushDispatcher();
        String entity=Result.create(message);
        System.out.println(entity+111111);
        PushHistory pushHistory=new PushHistory();
        pushHistory.setEntity(entity);
        pushHistory.setEntityType(message.getType());
        pushHistory.setCreateTime(new Date());
        pushHistory.setReceiver(receiveuser);
        System.out.println(pushHistory.getReceiverId()+"*****");
        pushHistory.setSender(sentuser);
        pushHistory.setReceiverPushId(clientCard.getClientId());
        pushHistory.setUpdatetime(new Date());
        pushHistory.setId(message.getId());
        pushHistoryService.save(pushHistory);
        dispatcher.add(clientCard.getClientId(),entity);
        NotificationTemplate template = notificationTemplateDemo(sentuser.getName(),message.getContent());
        ListMessage listmessage = new ListMessage();
        listmessage.setData(template);
        listmessage.setOfflineExpireTime(24 * 1000 * 3600);
        // 配置推送目标
        List targets = new ArrayList();
        Target target1 = new Target();
        target1.setAppId(appId);
        target1.setClientId(clientCard.getClientId());
        targets.add(target1);
        // taskId用于在推送时去查找对应的message
        String taskId = push.getContentId(listmessage);
        IPushResult ret = push.pushMessageToList(taskId, targets);



    }
    public static NotificationTemplate notificationTemplateDemo(final String title, final String content) {
        NotificationTemplate template = new NotificationTemplate();
        // 设置APPID与APPKEY
        template.setAppId(appId);
        template.setAppkey(appKey);

        Style0 style = new Style0();
        // 设置通知栏标题与内容
        style.setTitle(title);
        style.setText(content);

        // 配置通知栏图标
        style.setLogo("notification_icon.png");
        // 设置通知是否响铃，震动，或者可清除
        style.setRing(true);
        style.setVibrate(true);
        style.setClearable(true);
        template.setStyle(style);
         template.setTransmissionType(1);
        // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
        //template.setTransmissionType(2);
        return template;
    }
}
