package com.anysou.asloggersample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.anysou.aslogger.ASLogger;
import com.github.pedrovgs.lynx.LynxActivity;
import com.github.pedrovgs.lynx.LynxConfig;

public class MainActivity extends AppCompatActivity {
    private EditText logEdit, titleEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titleEdit = (EditText) findViewById(R.id.et_title);
        logEdit = (EditText) findViewById(R.id.et_log);

    }

    public void WriteASLog(View view) {
        String title = titleEdit.getText().toString().trim();
        String log = logEdit.getText().toString().trim();
        ASLogger.i(title,log);
        ASLogger.w(title,log);
        ASLogger.e(title,log);
    }

    public void ShowLog(View view) {
        startActivity(new Intent(this, ShowLogActivity.class)); //内部调用Activity
    }

    public void CallLogCAT(View view) {
        LynxConfig lynxConfig = new LynxConfig();
        lynxConfig.setMaxNumberOfTracesToShow(4000)  //LynxView中显示的最大跟踪数
                .setTextSizeInPx(12)       //用于在LynxView中呈现字体大小PX
                .setSamplingRate(200)      //从应用程序日志中读取的采样率
                .setFilter("myTag");       //设置过滤
        Intent lynxActivityIntent = LynxActivity.getIntent(this, lynxConfig);
        startActivity(lynxActivityIntent);
    }

    public void ShowLogByListView(View view) {
        startActivity(new Intent(this,ShowLogByListViewAcitvity.class));
    }
}
