package org.zhiqsyr.mfmall.web.conf;

import com.google.common.collect.ImmutableList;
import org.zhiqsyr.mfmall.web.aop.interceptor.SslInterceptor;
import org.zhiqsyr.mfmall.web.aop.filter.CorsFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConf extends WebMvcConfigurerAdapter {

	/** 允许跨域访问 */
	@Bean
	public FilterRegistrationBean registerCorsFilter() {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(new CorsFilter());
		registration.setUrlPatterns(ImmutableList.of("/*"));
		registration.setOrder(1);
		return registration;
	}

	/** 注册拦截器：处理安全链接 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new SslInterceptor());
	}

}
