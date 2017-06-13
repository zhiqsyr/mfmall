package org.zhiqsyr.mfmall.web.aop.interceptor;

import org.zhiqsyr.mfmall.domain.dto.BusinessException;
import org.zhiqsyr.mfmall.utils.Constants;
import org.zhiqsyr.mfmall.utils.MD5Utils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.http.HttpStatus;
import org.springframework.util.Base64Utils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 安全链接相关拦截适配器
 *
 */
// TODO 开启安全校验，直接配置中修改 server.contextPath: /${projectName}/ssl
public class SslInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String requestUri = request.getRequestURI();

		boolean necessary = true;	// 是否需要安全校验
		for (String ignoreUri : Constants.ignoreUris) {
			if (requestUri.contains(ignoreUri)) {
				necessary = false;
				break;
			}
		}
		if (necessary && requestUri.contains("/ssl/")) {
			if (!isTokenValid(requestUri, request.getParameter("token"), request.getParameter("timeStamp"))) {
				throw new BusinessException(HttpStatus.BAD_REQUEST, "ILLEGAL_TOKEN");
			}
		}

		return super.preHandle(request, response, handler);
	}

	/** 1）链接是否过期；2）token是否有效 */
	private boolean isTokenValid(String requestUri, String clientToken, String timeStamp) {
		if (System.currentTimeMillis() > NumberUtils.toLong(timeStamp)) return false;

		String src = "sample-springboot " + requestUri + " " + timeStamp + " iclassmate";
		String serverToken = Base64Utils.encodeToString(MD5Utils.encode32(src).getBytes());
		return serverToken.equals(clientToken);
	}

}
