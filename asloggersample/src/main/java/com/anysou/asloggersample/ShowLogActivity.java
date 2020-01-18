package com.anysou.asloggersample;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.anysou.aslogger.ASLogFileUtils;

public class ShowLogActivity extends AppCompatActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showlog);

        mTextView = (TextView) findViewById(R.id.tv_log);
        String log = ASLogFileUtils.readLog();  // 读取ASLog
        mTextView.setText(log);

    }
}
