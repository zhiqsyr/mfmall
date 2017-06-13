package org.zhiqsyr.mfmall.domain.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * 业务逻辑异常
 *
 */
@Data
public class BusinessException extends RuntimeException {

    private HttpStatus httpStatus;	// 返回的 httpStatus

    /**
     * @param httpStatus	org.springframework.http.HttpStatus
     * @param message		错误说明信息，例如 spaceId must not be null
     */
    public BusinessException(HttpStatus httpStatus, String message) {
    	super(message);

    	this.httpStatus = httpStatus;
    }

    public BusinessException(HttpStatus httpStatus) {
        this(httpStatus, httpStatus.name());
    }

    /**
     * httpStatus = HttpStatus.INTERNAL_SERVER_ERROR
     *
     * @param message
     */
    public BusinessException(String message) {
        this(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

}
