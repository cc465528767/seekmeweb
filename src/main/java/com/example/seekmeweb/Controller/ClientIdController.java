package com.example.seekmeweb.Controller;


import com.example.seekmeweb.Bean.ClientCard;
import com.example.seekmeweb.Result;
import com.example.seekmeweb.Service.ClientIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientIdController {

    @Autowired
    private ClientIdService clientIdService;


    @ResponseBody
    @RequestMapping(value = "/update")       //增操作
    public String saveClientId(HttpServletRequest httpServletRequest){

        String userid = httpServletRequest.getParameter("userid");
        String clientid = httpServletRequest.getParameter("clientid");
        if (clientIdService.findByUserId(userid)==null){
            ClientCard clientCard = new ClientCard();
            clientCard.setClientId(clientid);
            clientCard.setUserId(userid);
            clientIdService.save(clientCard);
        }
        else{
            ClientCard clientCard = clientIdService.findByUserId(userid);
            clientCard.setClientId(clientid);
            clientIdService.save(clientCard);
        }
        return Result.create(0,"client记录更新成功",clientid);

    }


    @ResponseBody
    @PostMapping(value = "/clean")
    public String CleanLatLng(String userid) {
        ClientCard clientCard = clientIdService.findByUserId(userid);
        if (clientCard.getClientId()!= null){
            clientCard.setClientId(null);
            clientIdService.save(clientCard);
            return Result.create(0,"清零成功",clientCard);
        }
        else

            return Result.create(0,"网络出现问题",clientCard);
    }

    @ResponseBody
    @PostMapping(value = "/member")
    public String getMyMember(HttpServletRequest httpServletRequest){
        String myid = httpServletRequest.getParameter("myid");
        List<ClientCard> myMemberList = new ArrayList<ClientCard>();
        myMemberList = clientIdService.getList(myid);

        if (myMemberList.size() == 0) {
            return Result.create(0, "查找失败", myMemberList);
        }
        return Result.create(0,"查找成功",myMemberList);
    }

    @ResponseBody
    @RequestMapping(value = "/test")       //增操作
    public String test(){
        return "hello client b";
    }
    @ResponseBody
    @RequestMapping(value = "/test2")       //增操作
    public String test2(HttpServletRequest httpServletRequest){

        return Result.create(0, "查找失败",clientIdService.findByUserId(httpServletRequest.getParameter("userid")));
    }
}
