package com.samton.ibenrobotdemo.ui;

import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.samton.ibenrobotdemo.R;
import com.samton.ibenrobotdemo.widgets.IBenVideoController;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;

/**
 * <pre>
 *   author  : syk
 *   e-mail  : shenyukun1024@gmail.com
 *   time    : 2018/02/02 21:03
 *   desc    : 视频测试界面
 *   version : 1.0
 * </pre>
 */

public class VideoTestActivity extends AppCompatActivity {

    /**
     * 播放器控件
     */
    private NiceVideoPlayer mVideoPlayer = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        initView();
    }

    private void initView() {
        mVideoPlayer = (NiceVideoPlayer) findViewById(R.id.mVideoPlayer);
        // 设置播放引擎为本地(Android原生播放器)
        mVideoPlayer.setPlayerType(NiceVideoPlayer.TYPE_NATIVE);
        // 视频地址
        String videoUrl = Environment.getExternalStorageDirectory()
                .getAbsoluteFile() + "/IBenService/Test.mp4";
        long duration = Long.valueOf(getRingDuring(videoUrl));
        // 设置视频路径
        mVideoPlayer.setUp(videoUrl, null);
        // 视频控制器
        IBenVideoController controller = new IBenVideoController(this);
        // 设置总时长
        controller.setLenght(duration);
        controller.setImage(ThumbnailUtils.createVideoThumbnail(
                videoUrl, MediaStore.Images.Thumbnails.MINI_KIND));
        // 设置控制器
        mVideoPlayer.setController(controller);
    }

    @Override
    protected void onStop() {
        super.onStop();
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
    }

    @Override
    public void onBackPressed() {
        if (NiceVideoPlayerManager.instance().onBackPressd()) {
            return;
        }
        super.onBackPressed();
    }

    public String getRingDuring(String path) {
        String duration = null;
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();

        try {
            mmr.setDataSource(path);
            duration = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        } catch (Throwable ex) {
            ex.getStackTrace();
        } finally {
            mmr.release();
        }
        return duration;
    }
}
