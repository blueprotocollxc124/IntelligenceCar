package org.linkworld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.util.unit.DataSize;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication
@EnableSwagger2
public class IntelligenceCarApplication {

    public static void main(String[] args) {
        SpringApplication.run(IntelligenceCarApplication.class, args);
    }


    /**
     * 设置文件上传大小
     * @return
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //单个文件最大
        factory.setMaxFileSize(DataSize.ofMegabytes(30)); //MB
        //factory.setMaxFileSize(DataSize.ofKilobytes(80)); //KB
        //factory.setMaxFileSize(DataSize.ofGigabytes(80)); //Gb
        /// 设置总上传数据总大小
        factory.setMaxRequestSize(DataSize.ofMegabytes(50));
        return factory.createMultipartConfig();
    }

}
