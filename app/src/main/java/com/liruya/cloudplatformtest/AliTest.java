package com.liruya.cloudplatformtest;

import java.util.List;

public class AliTest extends Test {

//    private final String API_HOST = "http://47.95.218.255:8080";
    private final String API_HOST = "http://localhost:8010";

    public AliTest(List<String> addressList, String email, String loginEmail, String loginPassword) {
        super(addressList, email, loginEmail, loginPassword);
    }

    @Override
    public Result getVerifyCode(String email) {
        String url = API_HOST + "/LightDemo/SingSendMail/sendMailCode/{email}.do";
        url = url.replace("{email}", email);
//        Map<String, String> map = new HashMap();
//        map.put("email", email);
//        JSONObject jsonObject = new JSONObject(map);
//        Log.e("TAG", "getVerifyCode: " + jsonObject.toString());
//        RequestBody requestBody = RequestBody.create(jsonObject.toString(), JSON);
//        return OKHttpManager.getInstance().blockPost(url, mHeaders, requestBody);
        return OKHttpManager.getInstance().blockGet(url, mHeaders);
    }

    @Override
    public Result login(String email, String password) {
        String url = API_HOST + "/LightDemo/AppUsers/login/{email}/{password}.do";
        url = url.replace("{email}", email)
                 .replace("{password}", password);
//        Map<String, String> map = new HashMap();
//        map.put("email", email);
//        map.put("password", password);
//        JSONObject jsonObject = new JSONObject(map);
//        RequestBody requestBody = RequestBody.create(jsonObject.toString(), JSON);
//        return OKHttpManager.getInstance().blockPost(url, mHeaders, requestBody);
        return OKHttpManager.getInstance().blockGet(url, mHeaders);
    }

    @Override
    public Result getUserInfo(String userid, String token) {
//        String url = "https://api2.xlink.cn/v2/user/" + userid;
//        Headers.Builder builder = new Headers.Builder();
//        builder.add("Content-Type", "application/json");
//        builder.add("Access-Token", token);
//        Headers headers = builder.build();
//        return OKHttpManager.getInstance().blockGet(url, headers);
        return null;
    }

    public Result register(String email, String psw, String nickname, String code, String nation, String city) {
        String url = API_HOST + "/LightDemo/AppUsers/register/{email}/{password}/{nickname}/{nation}/{city}/{code}.do";
        url = url.replace("{email}", email)
                 .replace("{password}", psw)
                 .replace("{nickname}", nickname)
                 .replace("{nation}", nation)
                 .replace("{city}", city)
                 .replace("{code}", code);
        return OKHttpManager.getInstance().blockGet(url, mHeaders);
    }

    public Result addDevice(String email, String sku, String name, String nodeid, String nodetype, String netid, String net_type, String freqpoint, String sendmode) {
        String url = API_HOST + "/LightDemo/ControlTable/addControlTable/{eMail}/{skuNumber}/{controlName}/{nodeId}/{nodeType}/{internetId}/{internetType}/{frequencyPoint}/{sendingMode}.do";
        url = url.replace("{eMail}", email)
                 .replace("{skuNumber}", sku)
                 .replace("{controlName}", name)
                 .replace("{nodeId}", nodeid)
                 .replace("{nodeType}", nodetype)
                 .replace("{internetId}", netid)
                 .replace("{internetType}", net_type)
                 .replace("{frequencyPoint}", freqpoint)
                 .replace("{sendingMode}", sendmode);
        return OKHttpManager.getInstance().blockGet(url, mHeaders);
    }

    @Override
    public String getUseridFromLoginResponse(String response) {
        return null;
    }

    @Override
    public String getTokenFromLoginResponse(String response) {
        return null;
    }

    @Override
    public Result getDeviceInfo() {
        return null;
    }
}
