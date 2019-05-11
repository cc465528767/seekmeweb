package com.example.seekmeweb.Controller;

import com.example.seekmeweb.Bean.TokenInfo;
import com.example.seekmeweb.Bean.User;
import com.example.seekmeweb.Config.TokenResult;
import com.example.seekmeweb.Result;
import com.example.seekmeweb.Service.TokenJPA;
import com.example.seekmeweb.Service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value = "/jwt")
public class TokenController {
    @Autowired
    private TokenJPA tokenJPA;

    @Autowired
    private UserService userInfoJPA;

    @RequestMapping(value = "/token")//, method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public String token
            (
                    @RequestParam String phone1,
                    @RequestParam String password
            ) {
        User userDbInfo;
        TokenResult token = new TokenResult();
        //appId is null
        if (phone1 == null || phone1.trim() == "") {
            token.setFlag(false);
            token.setMsg("phone is not found!");
            return Result.create(0, "查找失败", null);
        }
        //appSecret is null
        else if (password == null || password.trim() == "") {
            token.setFlag(false);
            token.setMsg("password is not found!");
            return Result.create(0, "查找失败", null);
        } else {
            //根据phone1查询用户实体
            userDbInfo = userInfoJPA.findByPhone1(phone1);
            if (userDbInfo == null) {
                token.setFlag(false);
                token.setMsg("phone1 : " + phone1 + ", is not found!");
                return Result.create(0, "查找失败", null);

            }
            //验证appSecret是否存在
            else if (!new String(userDbInfo.getPassword()).equals(password.replace(" ", "+"))) {
                token.setFlag(false);
                token.setMsg("password is not effective!");
                return Result.create(0, "查找失败", null);
            } else {
                //检测数据库是否存在该appId的token值
                TokenInfo tokenDBEntity = tokenJPA.findOne(new Specification<TokenInfo>() {
                    @Override
                    public Predicate toPredicate(Root<TokenInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                        criteriaQuery.where(criteriaBuilder.equal(root.get("phone1"), phone1));
                        return null;
                    }
                });//返回token值
                String tokenStr = null;
                //tokenDBEntity == null -> 生成newToken -> 保存数据库 -> 写入内存 -> 返回newToken
                if (tokenDBEntity == null) {
                    //生成jwt,Token
                    tokenStr = createNewToken(phone1);
                    //将token保持到数据库
                    tokenDBEntity = new TokenInfo();
                    tokenDBEntity.setPhone1(userDbInfo.getPhone1());
                    tokenDBEntity.setBuildTime(String.valueOf(System.currentTimeMillis()));
                    tokenDBEntity.setToken(tokenStr.getBytes());
                    tokenJPA.save(tokenDBEntity);
                }
                //tokenDBEntity != null -> 验证是否超时 ->
                //不超时 -> 直接返回dbToken
                //超时 -> 生成newToken -> 更新dbToken -> 更新内存Token -> 返回newToken
                else {
                    //判断数据库中token是否过期，如果没有过期不需要更新直接返回数据库中的token即可
                    //数据库中生成时间
                    long dbBuildTime = Long.valueOf(tokenDBEntity.getBuildTime());
                    //当前时间
                    long currentTime = System.currentTimeMillis();
                    //如果当前时间 - 数据库中生成时间 < 604800 证明可以正常使用
                    long second = TimeUnit.MILLISECONDS.toSeconds(currentTime - dbBuildTime);
                    if ((second > 0 && second < 604800) && (tokenDBEntity.getToken() != null) && (tokenDBEntity.getToken().length != 0)) {
                        tokenStr = new String(tokenDBEntity.getToken());
                    }
                    //超时
                    else {
                        //生成newToken
                        tokenStr = createNewToken(phone1);
                        //更新token
                        tokenDBEntity.setToken(tokenStr.getBytes());
                        //更新生成时间
                        tokenDBEntity.setBuildTime(String.valueOf(System.currentTimeMillis()));
                        //执行更新
                        tokenJPA.save(tokenDBEntity);
                    }
                }
                //设置返回token
                token.setToken(tokenStr);
            }
        }
        System.out.println("登录时的token：" + token.getToken());
        return Result.create(1, "查找成功", userDbInfo, token.getToken());
    }

    /**
     * 创建新token
     *
     * @param appId
     * @return
     */


    private String createNewToken(String appId) {
        //获取当前时间
        Date now = new Date(System.currentTimeMillis());
        //过期时间
        Date expiration = new Date(now.getTime() + 604800000);
        return Jwts
                .builder()
                .setSubject(appId)
                //.claim(YAuthConstants.Y_AUTH_ROLES, userDbInfo.getRoles())
                .setIssuedAt(now)
                .setIssuer("Online YAuth Builder")
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, "HengYuAuthv1.0.0")
                .compact();
    }
}
