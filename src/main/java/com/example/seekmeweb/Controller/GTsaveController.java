package com.example.seekmeweb.Controller;


import com.example.seekmeweb.Result;
import com.example.seekmeweb.Service.GTsaveService;
import com.gexin.rp.sdk.base.IBatch;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.gexin.rp.sdk.template.style.Style0;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping({"/gtrequest"})
public class GTsaveController {
    @Autowired
    private GTsaveService gTsaveService;
    private static final String appKey = "yKSMrWeoSL8NF9B4GAzWH6";
    private static final String masterSecret = "eqvB8ZAiK87HVlvMuaZv33";
    private static final String appId=  "dlC8dcAu2C7xYEC92otrn8";
    private static final String host = "http://sdk.open.api.igexin.com/apiex.htm";



    @ResponseBody
    @PostMapping(value = "/sendSos")
    public String pushToOne(HttpServletRequest httpServletRequest)  throws IOException {
        String seekeruserid = httpServletRequest.getParameter("userid");
        String clientList = httpServletRequest.getParameter("clientList");
        String content = httpServletRequest.getParameter("content");

        if ( seekeruserid == null ||  clientList== null || content== null
               ) {
            System.out.println(seekeruserid+"  "+clientList+"  "+content+"  "+"  ");
            return Result.create(0, "推送消息有误", 0);
        }
        else
        {
            IGtPush push = new IGtPush(host, appKey, masterSecret);
            IBatch batch = push.getBatch();

            try {
//                org.json.JSONArray jsonArray = JSONArray.fromObject(jsonMessage);
                JSONArray jsonArray = new JSONArray(clientList);
                for (int i = 0; i < jsonArray.length(); i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String cid = jsonObject.getString("clientId");
                    System.out.println("content = "+ content +"  cid = "+ cid);
                    constructClientTransMsg(cid,content,batch);
                }

                //构建客户a的透传消息a
//                constructClientTransMsg(,content,batch);

            } catch (Exception e) {
                e.printStackTrace();
            }
            batch.submit();

            return Result.create(0, "添加推送成功",1);

        }
    }


    @ResponseBody
    @PostMapping(value = "/back")
    public String feedBack(HttpServletRequest httpServletRequest)  throws IOException{
        String seekercid = httpServletRequest.getParameter("seekercid");
        String content = httpServletRequest.getParameter("content");

        IGtPush push = new IGtPush(host, appKey, masterSecret);
        IBatch batch = push.getBatch();

        try {
        constructClientTransMsg(seekercid,content,batch);

            } catch (Exception e) {
            e.printStackTrace();
        }
        batch.submit();
        System.out.println("content = "+ content +"  cid = "+ seekercid);
        return Result.create(0, "反馈成功",1);
    }




    @ResponseBody
    @PostMapping(value = "/sendNotification")
    public String pushNotification(HttpServletRequest httpServletRequest)  throws IOException {
        String senderid = httpServletRequest.getParameter("senderid");
        String clientList = httpServletRequest.getParameter("clientList");
        String content = httpServletRequest.getParameter("content");

        if ( senderid == null ||  clientList== null || content== null
                ) {
            System.out.println(senderid+"  "+clientList+"  "+content+"  "+"  ");
            return Result.create(0, "通知消息有误", 0);
        }
        else
        {
            JSONObject jsonObject = new JSONObject(content);
            String noticeTitle = jsonObject.getString("noticeTitle");
            String noticeContent = jsonObject.getString("noticeContent");


            IGtPush push = new IGtPush(host, appKey, masterSecret);
            try {

                System.out.println(content);
                JSONArray jsonArray = new JSONArray(clientList);
                for (int i = 0; i < jsonArray.length(); i++){
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    String cid = jsonObject1.getString("clientId");
                    System.out.println("content = "+ content +"  cid = "+ cid);

                    NotificationTemplate template = notificationTemplateDemo(noticeTitle,noticeContent);
                    ListMessage message = new ListMessage();
                    message.setData(template);
                    // 设置消息离线，并设置离线时间
                    message.setOffline(true);
                    // 离线有效时间，单位为毫秒，可选
                    message.setOfflineExpireTime(24 * 1000 * 3600);
                    // 配置推送目标
                    List targets = new ArrayList();
                    Target target1 = new Target();
                    target1.setAppId(appId);
                    target1.setClientId(cid);
                    targets.add(target1);
                    // taskId用于在推送时去查找对应的message
                    String taskId = push.getContentId(message);
                    IPushResult ret = push.pushMessageToList(taskId, targets);

                }


            } catch (Exception e) {
                e.printStackTrace();
            }

            return Result.create(0, "添加通知成功",1);

        }
    }


    private static void constructClientTransMsg(String cid, String msg ,IBatch batch) throws Exception {

        SingleMessage message = new SingleMessage();
        TransmissionTemplate template = new TransmissionTemplate();
        template.setAppId(appId);
        template.setAppkey(appKey);
        template.setTransmissionContent(msg);
        template.setTransmissionType(1); // 这个Type为int型，填写1则自动启动app

        message.setData(template);
        message.setOffline(true);
        message.setOfflineExpireTime(1 * 1000);

        // 设置推送目标，填入appid和clientId
        Target target = new Target();
        target.setAppId(appId);
        target.setClientId(cid);
        batch.add(message, target);
    }


    public static NotificationTemplate notificationTemplateDemo(final String title,final String content) {
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

        // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
        template.setTransmissionType(2);
        return template;
    }

}



//TODO 先不动
//            IGtPush push = new IGtPush(host, appKey, masterSecret);
//            // 定义"点击链接打开通知模板"，并设置标题、内容、链接
//            LinkTemplate template = new LinkTemplate();
//            template.setAppId(appId);
//            template.setAppkey(appKey);
//            template.setTitle(title);
//            template.setText(content);
//            template.setUrl("http://getui.com");
//
//            List<String> appIds = new ArrayList<String>();
//            appIds.add(appId);
//
//            // 定义"AppMessage"类型消息对象，设置消息内容模板、发送的目标App列表、是否支持离线发送、以及离线消息有效期(单位毫秒)
//            AppMessage message = new AppMessage();
//            message.setData(template);
//            message.setAppIdList(appIds);
//            message.setOffline(false);
////            message.setOfflineExpireTime(1000 * 600);
//
//            IPushResult ret = push.pushMessageToApp(message);
//            System.out.println(ret.getResponse().toString());
//
//            SaveMe saveMe = new SaveMe();
//            saveMe.setCid(httpServletRequest.getParameter("clientid"));
//            saveMe.setContent(httpServletRequest.getParameter("contet"));
//            saveMe.setCreateTime(httpServletRequest.getParameter("createtime"));
//
//
//            gTsaveService.save(saveMe);