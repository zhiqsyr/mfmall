package org.zhiqsyr.mfmall.web.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 系统全局配置信息
 * 
 * @author dongbz 
 * @since  2016-08-04
 */
@Data
@Component
@ConfigurationProperties(prefix = "global")
public class GlobalProperties {

	private String projectName;

	private String ignoreUris;		// 忽略安全校验的 uri

}
