package com.samton.ibenrobotdemo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.iflytek.cloud.SpeechError;
import com.samton.ibenrobotdemo.R;
import com.samton.ibenvoice.IBenVoice;
import com.samton.ibenvoice.interfaces.IBenRecordCallBack;
import com.samton.ibenvoice.interfaces.IBenTTSCallBack;

/**
 * <pre>
 *   @author  : syk
 *   e-mail  : shenyukun1024@gmail.com
 *   time    : 2018/05/09 10:50
 *   desc    :
 *   version : 1.0
 * </pre>
 */
public class IBenVoiceTest extends AppCompatActivity {

    private EditText mTTSEdit;
    private TextView mShowText;

    private IBenTTSCallBack mTTSListener = new IBenTTSCallBack() {
        @Override
        public void onSpeakBegin(int i) {

        }

        @Override
        public void onCompleted(int i, SpeechError speechError) {

        }
    };

    private IBenRecordCallBack mRecordListener = new IBenRecordCallBack() {
        @Override
        public void onBeginOfSpeech(int i) {

        }

        @Override
        public void onVolumeChanged(int i, int i1, byte[] bytes) {

        }

        @Override
        public void onEndOfSpeech(int i) {

        }

        @Override
        public void onResult(int i, String s) {
            mShowText.append(s + "\n");
        }

        @Override
        public void onError(int i, String s) {

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iben_voice);
        initView();
        initData();
    }

    private void initView() {
        mTTSEdit = (EditText) findViewById(R.id.mTTSEdit);
        mShowText = (TextView) findViewById(R.id.mShowText);
        findViewById(R.id.mSpeakBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IBenVoice.getInstance().startRecognize();
            }
        });
        findViewById(R.id.mTTSBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IBenVoice.getInstance().startSpeaking(mTTSEdit.getText().toString());
            }
        });
    }

    private void initData() {
        IBenVoice.getInstance().init(this);
        IBenVoice.getInstance().setRecordListener(mRecordListener);
        IBenVoice.getInstance().setTTSListener(mTTSListener);
    }
}
