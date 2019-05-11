package com.example.seekmeweb.Push;

import com.example.seekmeweb.Result;
import com.gexin.rp.sdk.base.IBatch;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 消息推送 工具类
 *
 */

@RestController
@RequestMapping("/push")
public class PushDispatcher {

    //采用"Java SDK 快速入门"， "第二步 获取访问凭证 "中获得的应用配置，用户可以自行替换 private static String appId = "";
    private static final String appKey = "yKSMrWeoSL8NF9B4GAzWH6";
    private static final String masterSecret = "eqvB8ZAiK87HVlvMuaZv33";
    private static final String appId=  "dlC8dcAu2C7xYEC92otrn8";
    private static final String host = "http://sdk.open.api.igexin.com/apiex.htm";

    private IGtPush pusher;

    //要收到消息的人和内容的列表
    private List<BatchBean>  beans = new ArrayList<>();


    //可能会有很多接受者
    static String CID_A = "";
    static String CID_B = "";

    public PushDispatcher(){
        //最根本的发送者
        pusher = new IGtPush(host,appKey,masterSecret);
    }

//    public static void main(String[] args) throws IOException {

    @ResponseBody
    @PostMapping(value = "/c")
    public static String PushAction(String clientId) throws IOException {
        //基本的初始化
        String msg = "msga";
        IGtPush push = new IGtPush(host, appKey, masterSecret);
        CID_A = "a57bdab9c2570c6afd00f927a915307c";

        //打包的工具类
        IBatch batch = push.getBatch();
        try {
        //构建客户a的透传消息a
            constructClientTransMsg(CID_A,msg,batch); //构建客户B的点击通知打开网页消息b constructClientLinkMsg(CID_B,"msgB",batch);
        } catch (Exception e) {
            e.printStackTrace();

        }
        batch.submit();


        return Result.create(0,"查找成功",msg);
    }

    private static void constructClientTransMsg(String cid, String msg ,IBatch batch) throws
            Exception {

        //构建消息
        //构建透传  不是通知栏显示 而是在客户端SosDialogReceiver收到
        TransmissionTemplate template = new TransmissionTemplate();
        template.setAppId(appId);
        template.setAppkey(appKey);
        template.setTransmissionContent(msg);
        template.setTransmissionType(1); // 这个Type为int型，填写1则自动启动app


        SingleMessage message = new SingleMessage();
        message.setData(template);//吧透传消息设置到单消息模板中
        message.setOffline(true);//是否允许离线发送
        message.setOfflineExpireTime(24 * 3600 * 1000); // 离线消息时常

//        message.setOfflineExpireTime(1 * 1000);24*3600 一天


        // 设置推送目标，填入appid和clientId
        Target target = new Target();
        target.setAppId(appId);
        target.setClientId(cid);
        batch.add(message, target);
    }


//
//    public void add(User receiver,){
//        if (receiver == null)
//    }


    //进行最终发送
    public boolean submit(){
        //打包的工具类
        IBatch batch = pusher.getBatch();

        //全部失败  或者没有数据   就不发送了
        boolean havaData = false;

        for (BatchBean bean : beans) {
            try {
                batch.add(bean.message,bean.target);
                havaData = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (!havaData) {
            return false;
        }

        IPushResult result = null;

        try {
            result = batch.submit();
        } catch (IOException e) {
            e.printStackTrace();

            //失败状态 尝试再发送一次
            try {
                batch.retry();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

        if (result!=null)
        {
            try {
                Logger.getLogger("PushDispatcher")
                        .log(Level.INFO,(String)result.getResponse().get("result"));
                return true;
            }
            catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        Logger.getLogger("PushDispatcher")
                .log(Level.WARNING,"推送服务器响应异常");
        return false;



    }

    public boolean add(String pushId,String message) {
        // 基础检查，必须有接收者的设备的Id
        if(pushId.isEmpty()||message.isEmpty())
           return false;
        BatchBean bean = buildMessage(pushId, message);
        beans.add(bean);
        return true;
    }
    private BatchBean buildMessage(String clientId, String text) {
        // 透传消息，不是通知栏显示，而是在MessageReceiver收到
        TransmissionTemplate template = new TransmissionTemplate();
        template.setAppId(appId);
        template.setAppkey(appKey);
        template.setTransmissionContent(text);
        template.setTransmissionType(0); //这个Type为int型，填写1则自动启动app

        SingleMessage message = new SingleMessage();
        message.setData(template); // 把透传消息设置到单消息模版中
        message.setOffline(true); // 是否运行离线发送
        message.setOfflineExpireTime(24 * 3600 * 1000); // 离线消息时常

        // 设置推送目标，填入appid和clientId
        Target target = new Target();
        target.setAppId(appId);
        target.setClientId(clientId);

        // 返回一个封装
        return new BatchBean(message, target);
    }
    private static class BatchBean{
        SingleMessage message;
        Target target;

        BatchBean(SingleMessage message, Target target) {
            this.message = message;
            this.target = target;
        }
    }


    @ResponseBody
    @RequestMapping(value = "/b")
    String test(){
        return "hello PushDispatcher b";
    }



}
