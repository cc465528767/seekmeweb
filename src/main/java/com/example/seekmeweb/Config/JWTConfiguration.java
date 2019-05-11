package com.example.seekmeweb.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class JWTConfiguration extends WebMvcConfigurerAdapter
{
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JwtTokenInterceptor()).addPathPatterns("/**").excludePathPatterns(
                "/user/search/check","/testUploadFile","/gtrequest/**",
                "/user/search/phone","/user/change/*",
                "/web/*","/user/web/*","user/noticeadd","user/getnotice",
                "/department/*","/user/*","/message/*","/client/test2");
    }
}



