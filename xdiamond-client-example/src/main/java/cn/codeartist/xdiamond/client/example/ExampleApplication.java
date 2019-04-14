package cn.codeartist.xdiamond.client.example;

import cn.codeartist.xdiamond.client.spring.DiamondClientBean;
import cn.codeartist.xdiamond.client.spring.DiamondPropertySourcesBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * 客户端样例
 *
 * @author 艾江南
 * @date 2019/4/14
 */
@SpringBootApplication
public class ExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExampleApplication.class, args);
    }

    @Bean
    public DiamondClientBean diamondClientBean() {
        return new DiamondClientBean();
    }

    @Bean
    public DiamondPropertySourcesBean diamondPropertySourcesBean() {
        return new DiamondPropertySourcesBean();
    }
}
