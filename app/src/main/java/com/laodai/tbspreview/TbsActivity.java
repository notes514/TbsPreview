package com.laodai.tbspreview;

import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.Response;
import com.tencent.smtt.sdk.TbsReaderView;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TbsActivity extends AppCompatActivity implements TbsReaderView.ReaderCallback {
    @BindView(R.id.rl_tbs)
    RelativeLayout rlTbs;
    private static final String TAG = "TbsActivity";
    //tbs
    private TbsReaderView mTbsReaderView;
    //docUrl
    private String docUrl = "http:\\/\\/zyweike.cdn.bcebos.com\\/zyweike\\/ep1\\/2018\\/04\\/02\\/周末加班统计.xlsx";
    private String download = Environment.getExternalStorageDirectory() + "/download/test/document/";
    private String tbsReaderTemp = Environment.getExternalStorageState() + "TbsReaderTemp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tbs);
        ButterKnife.bind(this);
        //初始化TBSView
        mTbsReaderView = new TbsReaderView(this, this);
        rlTbs.addView(mTbsReaderView, new RelativeLayout.LayoutParams(-1, -1));
        initDoc();
    }

    private void initDoc() {
        int i = docUrl.lastIndexOf("/");
        String docName = docUrl.substring(i);
        Log.d(TAG, "substring--->" + docName);

        String[] split = docUrl.split("\\/");
        String s = split[split.length - 4] + split[split.length - 3] + split[split.length - 2] + split[split.length - 1];
        Log.d(TAG, "截取带时间--->" + s);
        //判断是否在本地下载（如果是下载就直接打开）
        File docFile = new File(download, docName);
        if (docFile.exists()) {
            Log.d(TAG, "本地存在");
            displayFile(docFile.toString(), docName);
        } else {
            OkGo.<File>get(docUrl)
                    .tag(this)
                    .execute(new FileCallback(download, docName) {
                        @Override
                        public void onSuccess(Response<File> response) {
                            Log.d(TAG, "onSuccess: " + response.body());
                            displayFile(download + response.body().getName(), response.body().getName());
                        }
                    });
        }
    }

    /**
     * 显示文件
     *
     * @param filePath 文件路径
     * @param fileName 文件名
     */
    private void displayFile(String filePath, String fileName) {
        String bsReaderTemp = tbsReaderTemp;
        File bsReaderTempFile = new File(bsReaderTemp);
        if (!bsReaderTempFile.exists()) {
            Log.d(TAG,"准备创建--->TbsReaderTemp");
            boolean mkdir = bsReaderTempFile.mkdir();
            if (!mkdir) Log.d(TAG,"创建--->TbsReaderTemp--->失败");
        }
        //创建Bundle对象
        Bundle bundle = new Bundle();

        Log.d(TAG,"filePath：" + filePath);
        Log.d(TAG,"tempPath：" + tbsReaderTemp);
        bundle.putString("filePath", filePath);
        bundle.putString("tempPath", tbsReaderTemp);
        boolean result = mTbsReaderView.preOpen(getFileType(fileName), false);
        Log.d(TAG,"查看文档：" + result);
        if (result) mTbsReaderView.openFile(bundle);
    }

    /**
     * 获取文件类型
     *
     * @param fileName 文件名
     * @return String
     */
    private String getFileType(String fileName) {
        String str = "";
        if (TextUtils.isEmpty(fileName)) {
            Log.d(TAG,"paramStr ---> null");
            return str;
        }
        Log.d(TAG,"paramStr：" + fileName);
        int i = fileName.lastIndexOf('.');
        if (i <= -1) {
            Log.d(TAG, "i <= -1");
            return str;
        }
        str = fileName.substring(i + 1);
        Log.d("print", "paramString.substring(i + 1)------>" + str);
        return str;
    }

    @Override
    public void onCallBackAction(Integer integer, Object o, Object o1) {

    }
}
