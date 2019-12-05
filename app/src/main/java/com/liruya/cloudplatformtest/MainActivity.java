package com.liruya.cloudplatformtest;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private final String REGEX_EMAIL    = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";

    private TextInputLayout main_til;
    private TextInputEditText main_email;
    private Button main_btn_test;
    private ScrollView main_scrollview;
    private TextView main_result;
    private Button main_copy;

    private ProgressDialog mProgressDialog;
    private TaskImpl mTaskImpl;

    private TextWatcher mTextWatcher;

    private Test mXlinkTest;
    private Test mAliTest;
    private CloudTestTask mTestTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
        initEvent();
    }

    private void initView() {
        main_til = findViewById(R.id.main_til);
        main_email = findViewById(R.id.main_email);
        main_btn_test = findViewById(R.id.main_btn_test);
        main_scrollview = findViewById(R.id.main_scrollview);
        main_result = findViewById(R.id.main_result);
        main_copy = findViewById(R.id.main_copy);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setCancelable(false);
    }

    private void initData() {
        List<String> xlinkUrls = new ArrayList<>();
        xlinkUrls.add("api2.xlink.cn");
        xlinkUrls.add("mqtt.xlink.cn");
        mXlinkTest = new XlinkTest(xlinkUrls, null, "1046074984@qq.com", "12345678");
    }

    private void initEvent() {
        mTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                main_til.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        main_email.addTextChangedListener(mTextWatcher);

        mTaskImpl = new TaskImpl() {
            @Override
            public void onStart() {
                main_btn_test.setEnabled(false);
                main_copy.setEnabled(false);
                mProgressDialog.show();
            }

            @Override
            public void onProgress(String progress) {
                main_result.append(progress);
                main_scrollview.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        main_scrollview.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                }, 100);
            }

            @Override
            public void onComplete() {
                main_btn_test.setEnabled(true);
                main_copy.setEnabled(true);
                mProgressDialog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Check Email")
                       .setMessage("Please check the email whether reveived verifycode or not.")
                       .show();
            }
        };

        main_btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkEmailText()) {
                    main_result.setText(null);
                    mXlinkTest.setEmail(getEmailText());
                    mTestTask = new CloudTestTask(mTaskImpl);
                    mTestTask.execute(mXlinkTest, mXlinkTest);
                }
            }
        });

        main_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence text = main_result.getText();
                if (TextUtils.isEmpty(text)) {
                    return;
                }
                ClipboardManager manager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText(null, text);
                manager.setPrimaryClip(clipData);
                Toast.makeText(MainActivity.this, "Test result has been copied.", Toast.LENGTH_LONG)
                     .show();
            }
        });
    }

    private boolean isMatch(final String regex, final CharSequence input) {
        return input != null && input.length() > 0 && Pattern.matches(regex, input);
    }

    private boolean isEmail(final CharSequence input) {
        return isMatch(REGEX_EMAIL, input);
    }

    private String getEmailText() {
        return main_email.getText().toString();
    }

    private boolean checkEmailText() {
        String email = getEmailText();
        boolean result = isEmail(email);
        if (!result) {
            main_til.setError("Invalid email.");
        }
        return result;
    }
}
