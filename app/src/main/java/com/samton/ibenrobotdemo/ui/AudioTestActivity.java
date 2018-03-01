package com.samton.ibenrobotdemo.ui;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.samton.ibenrobotdemo.R;
import com.samton.ibenrobotdemo.interfaces.IPlayerCallBack;
import com.samton.ibenrobotdemo.widgets.IBenAudioPlayer;

/**
 * <pre>
 *   author  : syk
 *   e-mail  : shenyukun1024@gmail.com
 *   time    : 2018/02/26 09:33
 *   desc    : 音频测试界面
 *   version : 1.0
 * </pre>
 */

public class AudioTestActivity extends AppCompatActivity
        implements IPlayerCallBack {

    private IBenAudioPlayer mAudioPlayer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
        initView();
        initData();
    }

    private void initView() {
        mAudioPlayer = (IBenAudioPlayer) findViewById(R.id.mAudioPlayer);
    }

    private void initData() {
        mAudioPlayer.setCallBack(this);
        String filePath = Environment.getExternalStorageDirectory()
                .getAbsoluteFile() + "/IBenService/牛奶咖啡 - 越长大越孤单.mp3";
        mAudioPlayer.setData("牛奶咖啡 - 越长大越孤单", filePath);
    }

    /**
     * 播放完成
     */
    @Override
    public void onFinish() {
        String filePath = Environment.getExternalStorageDirectory()
                .getAbsoluteFile() + "/IBenService/牛奶咖啡 - 越长大越孤单.mp3";
        mAudioPlayer.setData("牛奶咖啡 - 越长大越孤单", filePath);
    }
}
