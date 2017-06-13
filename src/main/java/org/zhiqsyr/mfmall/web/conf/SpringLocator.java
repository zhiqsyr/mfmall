package org.zhiqsyr.mfmall.web.conf;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

/**
 * Spring 定位器
 *
 */
@Configuration
public class SpringLocator implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    /**
     * 获得 Spring 中指定名称的 bean
     *
     * @param name
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        return (T) applicationContext.getBean(name);
    }

    /**
     * 获得 Spring 中指定 class 类型的 bean
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        SpringLocator.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
    
}
