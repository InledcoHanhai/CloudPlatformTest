package com.liruya.cloudplatformtest;

public interface TestImpl {
    Result getVerifyCode(String email);

    Result login(String email, String password);

    Result getUserInfo(String userid, String token);

    String getUseridFromLoginResponse(String response);

    String getTokenFromLoginResponse(String response);

    Result getDeviceInfo();
}
