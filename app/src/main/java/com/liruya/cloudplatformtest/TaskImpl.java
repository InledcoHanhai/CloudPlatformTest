package com.liruya.cloudplatformtest;

public interface TaskImpl {
    void onStart();

    void onProgress(String progress);

    void onComplete();
}
