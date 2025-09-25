package com.fit.ws.core.exceptions;

public enum ErrorCode {
	USER_EXISTED(302,"Người dùng đã tồn tại"),
	USER_NOT_FOUND(302,"Không tìm thấy người dùng"),
	CANT_CREATE_DATA(400,"Không thể tạo"),
	DATA_NOT_FOUND(302,"Không có dữ liệu"),
	INVALID_TOKEN(401,"Token không hợp lệ");
	
	private int code;
	private String message;
	
	private ErrorCode(int code,String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
