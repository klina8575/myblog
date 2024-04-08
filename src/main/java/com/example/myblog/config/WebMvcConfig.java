package com.example.myblog.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    //String uploadPath = "file:///C:/blog/post/"
    @Value("${uploadPath}")
    String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //images 폴더의 모든 하위 경로
        /*
         localhost/images
         localhost/images/test
         localhost/images/test/hello
        */

        //localhost/images로 시작하는 모든 경로의 파일들은 uploadPath에서 찾아온다.
        //uploadPath에 이미지가 있어야 한다.
        registry.addResourceHandler("/images/**")
                .addResourceLocations(uploadPath);
    }

    @Bean
    MappingJackson2JsonView jsonView() { //데이터를 json 객체로 리턴해준다.
        return new MappingJackson2JsonView();
    }
}
