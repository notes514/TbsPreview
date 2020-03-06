package com.laodai.tbspreview;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * <pre>
 *     author : laodai
 *     e-mail : 851559442@qq.com
 *     time   : 2020/03/06
 *     desc   : MainActivity
 *     version: 1.0
 * </pre>
 */
public class MainActivity extends AppCompatActivity {
    @BindView(R.id.btn_load)
    Button btnLoad;
    @BindView(R.id.btn_download)
    Button btnDownload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_load, R.id.btn_download})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_load:
                break;
            case R.id.btn_download:
                break;
        }
    }
}
