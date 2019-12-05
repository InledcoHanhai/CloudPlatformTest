package com.liruya.cloudplatformtest;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CloudTestTask extends AsyncTask<Test, String, Void> {
    private TaskImpl mTaskImpl;

    public CloudTestTask(TaskImpl taskImpl) {
        mTaskImpl = taskImpl;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (mTaskImpl != null) {
            mTaskImpl.onStart();
        }
    }

    @Override
    protected Void doInBackground(Test... tests) {
        if (tests == null || tests.length == 0) {
            return null;
        }
//        Test test = tests[0];
        for (Test test : tests) {
            if (test == null) {
                continue;
            }
            // ping
            for (String adr : test.getAddressList()) {
                String result = ping(adr);
                publishProgress(result);
            }

            long time = System.currentTimeMillis();
            long duration;

            // get email verifycode
            Result res = test.getVerifyCode(test.getEmail());
            if (res.isSuccess()) {
                duration = System.currentTimeMillis() - time;
                publishProgress("\nSend verifycode: Success!\t\t" + duration + "ms\n");
            } else {
                publishProgress("\nSend verifycode: Failed!\n\t****" + res.getResult() + "\n");
            }
            // login
            time = System.currentTimeMillis();
            res = test.login(test.getLoginEmail(), test.getLoginPassword());
            if (res.isSuccess()) {
                duration = System.currentTimeMillis() - time;
                publishProgress("\nLogin: Success!\t\t" + duration + "ms\n");

                // get user info
                String userid = test.getUseridFromLoginResponse(res.getResult());
                String token = test.getTokenFromLoginResponse(res.getResult());
                time = System.currentTimeMillis();
                res = test.getUserInfo(userid, token);
                if (res.isSuccess()) {
                    duration = System.currentTimeMillis() - time;
                    publishProgress("\nGet user info: Success!\t\t" + duration + "ms\n");
                } else {
                    publishProgress("\nGet user info: Failed\n\t****" + res.getResult() + "\n");
                }
            } else {
                publishProgress("\nLogin: Failed!\n\t****" + res.getResult() + "\n");
            }

            time = System.currentTimeMillis();
            res = test.getDeviceInfo();
            if (res.isSuccess()) {
                duration = System.currentTimeMillis() - time;
                publishProgress("\nGet device info: Success!\t\t" + duration + "ms\n");
            } else {
                publishProgress("\nGet device info: Failed!\n\t****" + res.getResult() + "\n");
            }

            publishProgress("\n****************************************\n\n");
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        if (mTaskImpl != null && values != null && values.length > 0) {
            mTaskImpl.onProgress(values[0]);
        }
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (mTaskImpl != null) {
            mTaskImpl.onComplete();
        }
    }

    private String ping(final String address) {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process process = runtime.exec("ping -c 4 " + address);
            InputStream is = process.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            br.close();
            is.close();
            process.destroy();
            return sb.toString();
        }
        catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
}
