package org.zhiqsyr.mfmall.web.aop.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 允许跨域访问
 * 
 * @author dongbz 2016-08-03
 */
public class CorsFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");	// 全部允许向本服务器提交请求的URI
		httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");	// 允许提交请求的方法
		httpServletResponse.setHeader("Access-Control-Max-Age", "3600");		// 预检请求的缓存时间（秒）
		httpServletResponse.setHeader("Access-Control-Allow-Headers", "Content-Type, X-Requested-With");
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		
	}

}
