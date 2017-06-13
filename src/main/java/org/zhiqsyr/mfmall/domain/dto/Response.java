package org.zhiqsyr.mfmall.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * RESTful 接口统一应答
 * 
 * @author dongbingze at 2016年7月12日
 */
@Data
public class Response {

	private HttpStatus httpStatus;	// org.springframework.http.HttpStatus
	private Object data;			// 业务数据
	private String message;			// 处理结果信息

	@JsonIgnore		// 不会返回 httpStatus.name()，而是转换返回 httpStatus.value()
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	/** HTTP Status Code */
	public int getStatus() {
		return httpStatus.value();
	}

}
