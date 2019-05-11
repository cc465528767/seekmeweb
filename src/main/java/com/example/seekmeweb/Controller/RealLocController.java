package com.example.seekmeweb.Controller;


import com.example.seekmeweb.Bean.RealLoc;
import com.example.seekmeweb.Result;
import com.example.seekmeweb.Service.RealLocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/realloc")
public class RealLocController {
    @Autowired
    private RealLocService realLocService;

    @ResponseBody
    @PostMapping(value = "/search")
    public String searchBelongList(String jobnum,String belong,String leaf) {
        int b = Integer.parseInt(belong);
        List<RealLoc> realLocList = realLocService.findBelongList(jobnum,b);
        if(realLocList.size()==0)
        {
            return Result.create(0,"查找失败",realLocList);
        }

        return Result.create(0,"查找成功",realLocList);
    }



    @ResponseBody
    @PostMapping(value = "/cleanlatlng")
    public String CleanLatLng(String userId) {
        RealLoc loc = realLocService.findByUserId(userId);
        if (loc != null){
            RealLoc newLoc = new RealLoc();
            newLoc.setId(loc.getId());
            newLoc.setName(loc.getName());
            newLoc.setBelong(loc.getBelong());
            newLoc.setPhone1(loc.getPhone1());
            newLoc.setUserId(loc.getUserId());
            newLoc.setJobnum(loc.getJobnum());
            newLoc.setLat(0);
            newLoc.setLng(0);

            realLocService.save(newLoc);
            return Result.create(0,"经纬度清零成功",loc);
        }
        else

            return Result.create(0,"网络出现问题",loc);
    }

/* `belong` int(11) NOT NULL,
  `client_id` varchar(255) COLLATE utf8_croatian_ci DEFAULT NULL,
  `jobnum` varchar(255) COLLATE utf8_croatian_ci NOT NULL,
  `name` varchar(255) COLLATE utf8_croatian_ci NOT NULL,
  `phone1` varchar(255) COLLATE utf8_croatian_ci NOT NULL,

  `user_id` varchar(255) COLLATE utf8_croatian_ci NOT NULL,*/
    @ResponseBody
    @PostMapping(value = "/update/latlng")
    public String saveLatLng(@RequestParam String userId,@RequestParam String lat,@RequestParam String lng,@RequestParam String belong,@RequestParam String jobnum,@RequestParam String name,@RequestParam String phone1,
                             @RequestParam String clientId ) {

        System.out.println(userId+" "+lat+" "+lng);
        RealLoc loc = realLocService.findByUserId(userId);
        Double mLat = Double.parseDouble(lat);
        Double mLng = Double.parseDouble(lng);
        System.out.println(loc+" "+mLat+" "+lng);
        if (loc != null && lat != null && lng != null){
            RealLoc newLoc = new RealLoc();
            newLoc.setId(loc.getId());
            newLoc.setName(loc.getName());
            newLoc.setBelong(loc.getBelong());
            newLoc.setPhone1(loc.getPhone1());
            newLoc.setUserId(loc.getUserId());
            newLoc.setJobnum(loc.getJobnum());
            newLoc.setClientId(clientId);
            newLoc.setLat(mLat);
            newLoc.setLng(mLng);
            realLocService.save(newLoc);
            return Result.create(0,"经纬度更新成功",loc);
        }
        else if(lat != null && lng != null && loc== null){
            int i=Integer.parseInt(belong);
            RealLoc newLoc = new RealLoc();
            newLoc.setUserId(userId);
            newLoc.setLat(mLat);
            newLoc.setLng(mLng);
            newLoc.setName(name);
            newLoc.setBelong(i);
            newLoc.setPhone1(phone1);
            newLoc.setClientId(clientId);
            newLoc.setJobnum(jobnum);
            realLocService.save(newLoc);
            return Result.create(0,"新建用户经纬度更新成功",loc);

        }
        else
            return Result.create(0,"无此用户",loc);
    }







    @ResponseBody
    @RequestMapping(value = "/b")
    String test(){
        return "hello realloc b";
    }
}
