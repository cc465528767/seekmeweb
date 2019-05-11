package com.example.seekmeweb.Controller;


import com.example.seekmeweb.Bean.Sos;
import com.example.seekmeweb.Bean.Trace;
import com.example.seekmeweb.Result;
import com.example.seekmeweb.Service.SosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/sos")
public class SosController {
    @Autowired
    private SosService sosService;

    @ResponseBody
    @PostMapping(value = "/search/seek")
    public String searchBySeekerId(@RequestParam String userid){
        List<Sos> recordList = sosService.findSeekerIdList(userid);
        if(recordList.size()==0)
        {
            return Result.create(0,"查找失败",recordList);
        }

        return Result.create(0,"查找成功",recordList);
    }

    @ResponseBody
    @PostMapping(value = "/search/help")
    public String searchByUserId(@RequestParam String userid){
        List<Sos> recordList = sosService.findByHelperId(userid);
        if(recordList.size()==0)
        {
            return Result.create(0,"查找失败",recordList);
        }

        return Result.create(0,"查找成功",recordList);
    }


    @ResponseBody
    @PostMapping(value = "/insert/firststep")
    public String insertSosSend1(HttpServletRequest httpServletRequest){
       Sos sos = new Sos();

       sos.setSeekerId(httpServletRequest.getParameter("seekerid"));
       sos.setSeekerName(httpServletRequest.getParameter("seekername"));
       sos.setSosId(httpServletRequest.getParameter("sosid"));
       sos.setCreateTime(httpServletRequest.getParameter("createtime"));
       sos.setDate(httpServletRequest.getParameter("date"));
       sos.setSosLatitude(httpServletRequest.getParameter("lat"));
       sos.setSosLongitude(httpServletRequest.getParameter("lng"));
       sos.setSosPic(httpServletRequest.getParameter("picurl"));

       sosService.save(sos);
       return Result.create(0,"sos记录添加成功",sos);
    }

    @ResponseBody
    @PostMapping(value = "/insert/secondstep")
    public String insertSosSend2(HttpServletRequest httpServletRequest){
        Sos sos = new Sos();
        String sosid1 = httpServletRequest.getParameter("sosid");

        sos = sosService.findBySosid(sosid1);
        if (sos==null){
            return Result.create(0,"已经有人进行救援",0);
        }
        else {
            sos.setHelperName(httpServletRequest.getParameter("helpername"));
            sos.setHelperId(httpServletRequest.getParameter("helperid"));
            sosService.save(sos);
            return Result.create(0,"救援成功",1);
        }
    }


    @ResponseBody
    @RequestMapping(value = "/b")
    String test(){
        return "hello sos b";
    }
}
