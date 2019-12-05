package com.liruya.cloudplatformtest;

public class Result {
    private boolean mSuccess;
    private String mResult;

    public Result() {
        mSuccess = false;
    }

    public Result(boolean success, String result) {
        mSuccess = success;
        mResult = result;
    }

    public boolean isSuccess() {
        return mSuccess;
    }

    public void setSuccess(boolean success) {
        mSuccess = success;
    }

    public String getResult() {
        return mResult;
    }

    public void setResult(String result) {
        mResult = result;
    }
}
