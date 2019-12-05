package com.liruya.cloudplatformtest;

import android.text.TextUtils;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OKHttpManager {
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private final OkHttpClient mHttpClient;
    private final Gson mGson;

    private OKHttpManager() {
        mHttpClient = new OkHttpClient();
        mGson = new Gson();
    }

    public static OKHttpManager getInstance() {
        return LazyHolder.INSTANCE;
    }

    /**
     * @param url
     * @param headers new Headers.Builder().add( key, value );
     * @param callback
     */
    public boolean get(String url, Headers headers, Callback callback) {
        if (TextUtils.isEmpty(url)) {
            return false;
        }
        Request request;
        if (headers == null) {
            request = new Request.Builder().url(url)
                                           .build();
        }
        else {
            request = new Request.Builder().url(url)
                                           .headers(headers)
                                           .build();
        }
        mHttpClient.newCall(request)
                   .enqueue(callback);
        return true;
    }

    public Result blockGet(String url, Headers headers) {
        Result result = new Result();
        if (TextUtils.isEmpty(url)) {
            return result;
        }
        Request request;
        if (headers == null) {
            request = new Request.Builder().url(url)
                                           .build();
        } else {
            request = new Request.Builder().url(url)
                                           .headers(headers)
                                           .build();
        }
        try {
            Response response = mHttpClient.newCall(request).execute();
            if (response != null) {
                result.setSuccess(response.isSuccessful());
                result.setResult(response.body().string());
            }
        } catch (IOException e) {
            e.printStackTrace();
            result.setResult(e.getMessage());
        }
        return result;
    }

    public <T> T blockGet(String url, Headers headers, Class<T> clazz) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        Request request;
        if (headers == null) {
            request = new Request.Builder().url(url)
                                           .build();
        }
        else {
            request = new Request.Builder().url(url)
                                           .headers(headers)
                                           .build();
        }
        try {
            Response response = mHttpClient.newCall(request).execute();
            if (response != null && response.isSuccessful()) {
                return mGson.fromJson(response.body().string(), clazz);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * post request
     *
     * @param url post address
     * @param headers new Headers.Builder().add( key, value );
     * @param body new  FormBody.Builder().add( key, value );
     * @param callback
     */
    public boolean post(String url, Headers headers, RequestBody body, Callback callback) {
        if (TextUtils.isEmpty(url) || body == null) {
            return false;
        }
        Request request;
        if (headers == null) {
            request = new Request.Builder().url(url)
                                           .post(body)
                                           .build();
        }
        else {
            request = new Request.Builder().url(url)
                                           .headers(headers)
                                           .post(body)
                                           .build();
        }
        mHttpClient.newCall(request)
                   .enqueue(callback);
        return true;
    }

    /**
     * @param url
     * @param headers new Headers.Builder().add( key, value );
     * @param json
     * @param callback
     */
    public boolean post(String url, Headers headers, String json, Callback callback) {
        if (TextUtils.isEmpty(url) || TextUtils.isEmpty(json)) {
            return false;
        }
        RequestBody body = RequestBody.create(JSON, json);
        Request request;
        if (headers == null) {
            request = new Request.Builder().url(url)
                                           .post(body)
                                           .build();
        }
        else {
            request = new Request.Builder().url(url)
                                           .headers(headers)
                                           .post(body)
                                           .build();
        }

        mHttpClient.newCall(request)
                   .enqueue(callback);
        return true;
    }

    public Result blockPost(String url, Headers headers, RequestBody body) {
        Result result = new Result();
        if (TextUtils.isEmpty(url) || body == null) {
            return result;
        }
        Request request;
        if (headers == null) {
            request = new Request.Builder().url(url)
                                           .post(body)
                                           .build();
        }
        else {
            request = new Request.Builder().url(url)
                                           .headers(headers)
                                           .post(body)
                                           .build();
        }
        try {
            Response response = mHttpClient.newCall(request).execute();
            if (response != null) {
                result.setSuccess(response.isSuccessful());
                result.setResult(response.body().string());
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            result.setResult(e.getMessage());
        }
        return result;
    }

    public <T> T blockPost(String url, Headers headers, RequestBody body, Class<T> clazz) {
        if (TextUtils.isEmpty(url) || body == null) {
            return null;
        }
        Request request;
        if (headers == null) {
            request = new Request.Builder().url(url)
                                           .post(body)
                                           .build();
        }
        else {
            request = new Request.Builder().url(url)
                                           .headers(headers)
                                           .post(body)
                                           .build();
        }
        try {
            Response response = mHttpClient.newCall(request).execute();
            if (response != null && response.isSuccessful()) {
                return mGson.fromJson(response.body().string(), clazz);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> T blockPost(String url, Headers headers, String json, Class<T> clazz) {
        if (TextUtils.isEmpty(url) || TextUtils.isEmpty(json)) {
            return null;
        }
        RequestBody body = RequestBody.create(JSON, json);
        Request request;
        if (headers == null) {
            request = new Request.Builder().url(url)
                                           .post(body)
                                           .build();
        }
        else {
            request = new Request.Builder().url(url)
                                           .headers(headers)
                                           .post(body)
                                           .build();
        }

        try {
            Response response = mHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                return mGson.fromJson(response.body().string(), clazz);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class LazyHolder {
        private static final OKHttpManager INSTANCE = new OKHttpManager();
    }

    public abstract static class HttpCallback<T> implements Callback {
        private Gson mGson;
        private Type mType;

        public HttpCallback() {
            mGson = new Gson();
            Type superClass = getClass().getGenericSuperclass();
            if (superClass instanceof ParameterizedType) {
                mType = ((ParameterizedType) superClass).getActualTypeArguments()[0];
            }
        }

        @Override
        public void onFailure(@NotNull okhttp3.Call call, @NotNull IOException e) {
            onError(0, e.getMessage());
        }

        @Override
        public void onResponse(@NotNull okhttp3.Call call, @NotNull Response response) throws IOException {
            if (response.isSuccessful()) {
                String json = response.body().string();
                if (json == null) {
                    onError(response.code(), null);
                    return;
                }
                if (mType != null) {
                    try {
                        T result = mGson.fromJson(json, mType);
                        if (result != null) {
                            onSuccess(result);
                        } else {
                            onError(response.code(), null);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        onError(response.code(), response.message());
                    }
                }
            } else {
                onError(response.code(), response.message());
            }
        }

        public abstract void onError(int code, String msg);

        public abstract void onSuccess(T result);
    }

    public interface DownloadCallback
    {
        void onError( String msg );

        void onProgress( long total, long current );

        void onSuccess( File file);
    }
}
