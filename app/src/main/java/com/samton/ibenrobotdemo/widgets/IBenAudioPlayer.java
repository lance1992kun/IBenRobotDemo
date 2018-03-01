package com.samton.ibenrobotdemo.widgets;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.samton.IBenRobotSDK.utils.ToastUtils;
import com.samton.ibenrobotdemo.R;
import com.samton.ibenrobotdemo.interfaces.IPlayerCallBack;
import com.xiao.nicevideoplayer.NiceUtil;

import java.util.Timer;
import java.util.TimerTask;

/**
 * <pre>
 *   author  : syk
 *   e-mail  : shenyukun1024@gmail.com
 *   time    : 2018/02/06 09:21
 *   desc    : 小笨音频播放器
 *   version : 1.0
 * </pre>
 */

public class IBenAudioPlayer extends LinearLayout
        implements View.OnClickListener,
        MediaPlayer.OnPreparedListener,
        MediaPlayer.OnCompletionListener,
        MediaPlayer.OnErrorListener {

    /**
     * 上下文对象
     */
    private Context mContext;
    /**
     * 播放暂停按钮
     */
    private ImageView mPlayBtn;
    /**
     * 关闭按钮
     */
    private ImageView mCloseBtn;
    /**
     * 进度条
     */
    private SeekBar mSeekBar;
    /**
     * 音频名字
     */
    private TextView mAudioTitle;
    /**
     * 当前播放时间
     */
    private TextView mCurrentPosition;
    /**
     * 总时间
     */
    private TextView mLength;
    /**
     * 音频是否准备好
     */
    private boolean isPrepared = false;
    /**
     * 播放回调
     */
    private IPlayerCallBack mCallBack;
    /**
     * 视频播放对象
     */
    private MediaPlayer mediaPlayer;
    /**
     * 计时器
     */
    private Timer mUpdateProgressTimer;
    /**
     * 计时任务真实执行者
     */
    private TimerTask mUpdateProgressTimerTask;

    public IBenAudioPlayer(Context context) {
        super(context);
        mContext = context;
        // 初始化布局
        initView();
        // 初始化数据
        initData();
    }

    public IBenAudioPlayer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        // 初始化布局
        initView();
        // 初始化数据
        initData();
    }

    public IBenAudioPlayer(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        // 初始化布局
        initView();
        // 初始化数据
        initData();
    }

    /**
     * 初始化布局
     */
    private void initView() {
        // 布局文件
        LayoutInflater.from(mContext).inflate(R.layout.layout_audio_player, this, true);
        // 音频名字
        mAudioTitle = (TextView) findViewById(R.id.mAudioTitle);
        // 当前播放时间
        mCurrentPosition = (TextView) findViewById(R.id.mCurrentPosition);
        // 该文件总时长
        mLength = (TextView) findViewById(R.id.mLength);
        // 播放按钮
        mPlayBtn = (ImageView) findViewById(R.id.mPlayBtn);
        // 关闭按钮
        mCloseBtn = (ImageView) findViewById(R.id.mCloseBtn);
        // 进度条
        mSeekBar = (SeekBar) findViewById(R.id.mSeekBar);
        // 设置按键监听
        mPlayBtn.setOnClickListener(this);
        mCloseBtn.setOnClickListener(this);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        // 初始化播放器
        mediaPlayer = new MediaPlayer();
        // 音频播放错误监听
        mediaPlayer.setOnErrorListener(this);
        // 音频准备完毕后立刻开始播放
        mediaPlayer.setOnPreparedListener(this);
        // 一首播放完成后回调完毕
        mediaPlayer.setOnCompletionListener(this);
    }

    /**
     * 设置回调函数
     *
     * @param callBack 回调
     */
    public void setCallBack(IPlayerCallBack callBack) {
        mCallBack = callBack;
    }

    /**
     * 设置播放路径
     *
     * @param title    音频标题
     * @param filePath 文件路径
     */
    public void setData(String title, String filePath) {
        try {
            // 设置音频标题
            mAudioTitle.setText(title);
            // 重置标识位
            isPrepared = false;
            // 重置进度条
            mSeekBar.setProgress(0);
            mediaPlayer.reset();
            mediaPlayer.setDataSource(filePath);
            mediaPlayer.prepareAsync();
        } catch (Throwable throwable) {
            throwable.getStackTrace();
        }
    }

    /**
     * 开始更新进度条计时器
     */
    private void startUpdateProgressTimer() {
        // 确保安全先取消计时器
        cancelUpdateProgressTimer();
        if (mUpdateProgressTimer == null) {
            mUpdateProgressTimer = new Timer();
        }
        // 执行更新UI操作
        if (mUpdateProgressTimerTask == null) {
            mUpdateProgressTimerTask = new TimerTask() {
                public void run() {
                    post(new Runnable() {
                        public void run() {
                            updateProgress();
                        }
                    });
                }
            };
        }
        // 每隔1秒执行一次
        mUpdateProgressTimer.schedule(mUpdateProgressTimerTask, 0L, 1000L);
    }

    /**
     * 取消更新进度条计时器
     */
    private void cancelUpdateProgressTimer() {
        // 清空Timer
        if (mUpdateProgressTimer != null) {
            mUpdateProgressTimer.cancel();
            mUpdateProgressTimer = null;
        }
        // 清空执行者
        if (mUpdateProgressTimerTask != null) {
            mUpdateProgressTimerTask.cancel();
            mUpdateProgressTimerTask = null;
        }
    }

    /**
     * 刷新UI
     */
    private void updateProgress() {
        long position = mediaPlayer.getCurrentPosition();
        long duration = mediaPlayer.getDuration();
        // 此方法是显示缓冲进度的目前没用
        // int bufferPercentage = mediaPlayer.getBufferPercentage();
        // mSeekBar.setSecondaryProgress(bufferPercentage);
        int progress = (int) (100f * position / duration);
        mSeekBar.setProgress(progress);
        mCurrentPosition.setText(NiceUtil.formatTime(position));
    }

    /**
     * 暂停
     */
    public void pause() {
        cancelUpdateProgressTimer();
        mediaPlayer.pause();
        mPlayBtn.setImageResource(R.mipmap.icon_audio_play);
    }

    /**
     * 播放
     */
    public void play() {
        if (isPrepared) {
            startUpdateProgressTimer();
            mediaPlayer.start();
            mPlayBtn.setImageResource(R.mipmap.icon_audio_pause);
        } else {
            ToastUtils.showShort("请稍候，音频尚未准备完毕");
        }
    }

    @Override
    public void onClick(View v) {
        // 播放按钮
        if (v == mPlayBtn) {
            if (mediaPlayer.isPlaying()) {
                pause();
            } else {
                play();
            }
        }
        // 关闭按钮
        else if (v == mCloseBtn) {
            // 关闭后直接认为播放完毕
            mCallBack.onFinish();
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        // 重置标识位
        isPrepared = true;
        // 设置总时长
        mLength.setText(NiceUtil.formatTime(mp.getDuration()));
        // 准备完毕的情况下开始播放音频
        play();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        // 取消计时器
        cancelUpdateProgressTimer();
        // 回调播放完毕
        mCallBack.onFinish();
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }
}
