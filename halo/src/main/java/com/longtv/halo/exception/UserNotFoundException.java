package com.longtv.halo.exception;

public class UserNotFoundException extends BusinessException {
    public UserNotFoundException(String userId) {
        super("USER_NOT_FOUND", "User không tồn tại: " + userId);
    }
}
