package cn.codeartist.xdiamond.server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 资源配置
 *
 * @author 艾江南
 * @date 2019/3/20
 */
@PropertySource({"database.properties", "configuration.properties"})
@Configuration
public class ResourceConfig {
}
