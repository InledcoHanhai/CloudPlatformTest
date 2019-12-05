package com.liruya.cloudplatformtest;

import java.util.List;

public class TestParam {
    private List<String> mAddressList;
    private String mEmail;
    private String mLoginEmail;
    private String mLoginPassword;

    public TestParam(List<String> addressList, String email, String loginEmail, String loginPassword) {
        mAddressList = addressList;
        mEmail = email;
        mLoginEmail = loginEmail;
        mLoginPassword = loginPassword;
    }

    public List<String> getAddressList() {
        return mAddressList;
    }

    public void setAddressList(List<String> addressList) {
        mAddressList = addressList;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getLoginEmail() {
        return mLoginEmail;
    }

    public void setLoginEmail(String loginEmail) {
        mLoginEmail = loginEmail;
    }

    public String getLoginPassword() {
        return mLoginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        mLoginPassword = loginPassword;
    }
}
