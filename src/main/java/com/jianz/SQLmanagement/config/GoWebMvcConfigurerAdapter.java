package com.jianz.SQLmanagement.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


 /**
  * @author Jianz
  * @Description 对上传的图片静态资源可以直接访问的Configure
  * @Return
  * @Date 2022/6/11 11:02
  */
@Configuration
public class GoWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //配置静态资源处理
        registry.addResourceHandler("/**")
                .addResourceLocations("resources/", "static/", "public/",
                "META-INF/resources/")
                .addResourceLocations("classpath:resources/", "classpath:static/",
                "classpath:public/", "classpath:META-INF/resources/");

        registry.addResourceHandler("/image/**")
                /**
                   换个项目就需要修改这个保存路径
                 */
                .addResourceLocations("file:///D:/Java/IdeaProjects/SQLmanagement/productImg/");
    }
}
