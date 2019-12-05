package com.liruya.cloudplatformtest;

import java.util.List;

import okhttp3.Headers;
import okhttp3.MediaType;

public abstract class Test extends TestParam implements TestImpl {
    protected final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    protected final Headers mHeaders;

    public Test(List<String> addressList, String email, String loginEmail, String loginPassword) {
        super(addressList, email, loginEmail, loginPassword);

        Headers.Builder builder = new Headers.Builder();
        builder.add("Content-Type", "application/json");
        mHeaders = builder.build();
    }
}
