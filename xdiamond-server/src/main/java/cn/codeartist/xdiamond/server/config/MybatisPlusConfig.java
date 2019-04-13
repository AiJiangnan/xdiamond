package cn.codeartist.xdiamond.server.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * MyBatis-Plus配置类
 *
 * @author 艾江南
 * @date 2018/9/10
 */
@Configuration
@EnableTransactionManagement
@MapperScan("cn.codeartist.xdiamond.server.business.mapper")
public class MybatisPlusConfig {

    /**
     * 分页插件注册
     *
     * @return 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
