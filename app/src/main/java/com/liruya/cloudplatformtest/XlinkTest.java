package com.liruya.cloudplatformtest;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.RequestBody;

public class XlinkTest extends Test {
    private final String CORPORATION_ID = "100fa8b8584bdc00";
    private final String ACCESS_TOKEN = "QUM3NUNGRTcxMEM1NzY5MzQ0MDBENTY1OTcxQUZGRDExQkUyMEJGN0ZDMUE5RTlFNjJCRkZFMjE4MzIxODRBRA==";

    //    private final int userid = 422497125;

    public XlinkTest(List<String> addressList, String email, String loginEmail, String loginPassword) {
        super(addressList, email, loginEmail, loginPassword);
    }

    @Override
    public Result getVerifyCode(String email) {
        String url = "https://api2.xlink.cn/v2/user_register/email/verifycode";
        Map<String, String> map = new HashMap();
        map.put("corp_id", CORPORATION_ID);
        map.put("email", email);
        map.put("local_lang", "en-us");
        JSONObject jsonObject = new JSONObject(map);
        Log.e("TAG", "getVerifyCode: " + jsonObject.toString());
        RequestBody requestBody = RequestBody.create(jsonObject.toString(), JSON);
        return OKHttpManager.getInstance().blockPost(url, mHeaders, requestBody);
    }

    @Override
    public Result login(String email, String password) {
        String url = "https://api2.xlink.cn/v2/user_auth";
        Map<String, String> map = new HashMap();
        map.put("corp_id", CORPORATION_ID);
        map.put("email", email);
        map.put("password", password);
        JSONObject jsonObject = new JSONObject(map);
        RequestBody requestBody = RequestBody.create(jsonObject.toString(), JSON);
        return OKHttpManager.getInstance().blockPost(url, mHeaders, requestBody);
    }

    @Override
    public Result getUserInfo(String userid, String token) {
        String url = "https://api2.xlink.cn/v2/user/" + userid;
        Headers.Builder builder = new Headers.Builder();
        builder.add("Content-Type", "application/json");
        builder.add("Access-Token", token);
        Headers headers = builder.build();
        return OKHttpManager.getInstance().blockGet(url, headers);
    }

    @Override
    public String getUseridFromLoginResponse(String response) {
        if (response != null) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.has("user_id")) {
                    return jsonObject.getString("user_id");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public String getTokenFromLoginResponse(String response) {
        if (response != null) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.has("access_token")) {
                    return jsonObject.getString("access_token");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public Result getDeviceInfo() {
        String url = "https://api2.xlink.cn/v2/product/160fa2b95aac03e9160fa2b95aac5e01/v_device/1818127741";
        Headers.Builder builder = new Headers.Builder();
        builder.add("Content-Type", "application/json");
        builder.add("Access-Token", ACCESS_TOKEN);
        Headers headers = builder.build();
        return OKHttpManager.getInstance().blockGet(url, headers);
    }
}
