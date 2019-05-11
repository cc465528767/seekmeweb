package com.example.seekmeweb.Controller;


import com.example.seekmeweb.Bean.Trace;
import com.example.seekmeweb.Result;
import com.example.seekmeweb.Service.TraceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/trace")
public class TraceController {
    @Autowired
    private TraceService traceService;


    @ResponseBody
    @RequestMapping(value = "/add")       //增操作
    public String add(HttpServletRequest httpServletRequest){
        Trace trace = new Trace();
        trace.setUserId(httpServletRequest.getParameter("userid"));
        trace.setStartTime(httpServletRequest.getParameter("starttime"));
        trace.setRealTime(httpServletRequest.getParameter("realtime"));
        trace.setDate(httpServletRequest.getParameter("date"));
        trace.setTracePic(httpServletRequest.getParameter("picurl"));

        traceService.save(trace);

        return Result.create(0,"添加记录成功",trace);
    }

    @ResponseBody
    @PostMapping(value = "/search/mine")
    public String searchByUserid(String userid){
        List<Trace> recordList = traceService.findByUserId(userid);
        if(recordList.size()==0)
        {
            return Result.create(0,"查找失败",recordList);
        }

        return Result.create(0,"查找成功",recordList);
    }


    @ResponseBody
    @RequestMapping(value = "/b")
    String test(){
        return "hello test b";
    }
}
