package cn.codeartist.xdiamond.client.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertiesPropertySource;

import java.util.Properties;

/**
 * @author 艾江南
 * @date 2019/4/14
 */
public class DiamondPropertySourcesBean implements InitializingBean, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Autowired
    private DiamondClientBean diamondClientBean;

    @Override
    public void afterPropertiesSet() {
        Environment environment = applicationContext.getEnvironment();
        if (environment instanceof ConfigurableEnvironment) {
            Properties properties = diamondClientBean.getProperties();
            ConfigurableEnvironment configurableEnvironment = (ConfigurableEnvironment) environment;
            PropertiesPropertySource propertySource = new PropertiesPropertySource("xdiamond", properties);
            configurableEnvironment.getPropertySources().addLast(propertySource);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
