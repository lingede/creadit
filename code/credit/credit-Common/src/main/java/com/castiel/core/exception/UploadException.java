package com.castiel.core.exception;

import com.castiel.core.support.HttpCode;

@SuppressWarnings("serial")
public class UploadException extends BaseException {
	public UploadException() {
	}

	public UploadException(String message) {
		super(message);
	}

	public UploadException(String message, Exception e) {
		super(message, e);
	}

	protected HttpCode getHttpCode() {
		return HttpCode.BAD_REQUEST;
	}
}

