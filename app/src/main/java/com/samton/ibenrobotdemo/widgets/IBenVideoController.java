package com.samton.ibenrobotdemo.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.samton.ibenrobotdemo.R;
import com.xiao.nicevideoplayer.INiceVideoPlayer;
import com.xiao.nicevideoplayer.NiceUtil;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.NiceVideoPlayerController;

/**
 * <pre>
 *   author  : syk
 *   e-mail  : shenyukun1024@gmail.com
 *   time    : 2018/02/02 21:28
 *   desc    : 视频播放控制器
 *   version : 1.0
 * </pre>
 */

public class IBenVideoController extends NiceVideoPlayerController
        implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    /**
     * 上下文对象
     */
    private Context mContext = null;
    /**
     * 底图
     */
    private ImageView mBgImg = null;
    /**
     * 播放按钮
     */
    private ImageView mPlayBtn = null;
    /**
     * 当前播放进度
     */
    private TextView mCurrentPosition = null;
    /**
     * 进度条
     */
    private SeekBar mSeekBar = null;
    /**
     * 视频总长度
     */
    private TextView mLength = null;
    /**
     * 中间播放按钮
     */
    private ImageView mCenterPlayBtn = null;

    /**
     * 构造函数
     *
     * @param context 上下文对象
     */
    public IBenVideoController(Context context) {
        super(context);
        // 初始化上下文对象
        mContext = context;
        // 初始化界面
        initView();
        // 初始化数据
        initData();
    }

    /**
     * 初始化界面
     */
    private void initView() {
        // 初始化控制器视图文件
        LayoutInflater.from(mContext).inflate(R.layout.layout_video_player, this, true);
        // 底图
        mBgImg = (ImageView) findViewById(R.id.mBgImg);
        // 播放按钮
        mPlayBtn = (ImageView) findViewById(R.id.mPlayBtn);
        // 当前播放进度
        mCurrentPosition = (TextView) findViewById(R.id.mCurrentPosition);
        // 进度条
        mSeekBar = (SeekBar) findViewById(R.id.mSeekBar);
        // 视频总长度
        mLength = (TextView) findViewById(R.id.mLength);
        // 中间播放按钮
        mCenterPlayBtn = (ImageView) findViewById(R.id.mCenterPlayBtn);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        // 点击监听事件
        mPlayBtn.setOnClickListener(this);
        mCenterPlayBtn.setOnClickListener(this);
    }

    /**
     * 点击事件
     *
     * @param view 某个View
     */
    @Override
    public void onClick(View view) {
        if (mNiceVideoPlayer.isIdle()) {
            mNiceVideoPlayer.start();
        } else if (mNiceVideoPlayer.isPlaying() || mNiceVideoPlayer.isBufferingPlaying()) {
            mNiceVideoPlayer.pause();
        } else if (mNiceVideoPlayer.isPaused() || mNiceVideoPlayer.isBufferingPaused()) {
            mNiceVideoPlayer.restart();
        }
    }

    /**
     * 进度条变更监听
     *
     * @param seekBar  哪个进度条
     * @param progress 进度
     * @param fromUser 是否是用户手动拖动的
     */
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (mNiceVideoPlayer.isBufferingPaused() || mNiceVideoPlayer.isPaused()) {
            mNiceVideoPlayer.restart();
        }
        long position = (long) (mNiceVideoPlayer.getDuration() * seekBar.getProgress() / 100f);
        mNiceVideoPlayer.seekTo(position);
    }

    /**
     * 设置播放的视频的标题
     *
     * @param title 视频标题
     */
    @Override
    public void setTitle(String title) {

    }

    /**
     * 视频底图
     *
     * @param resId 视频底图资源
     */
    @Override
    public void setImage(@DrawableRes int resId) {
        mBgImg.setImageResource(resId);
    }

    /**
     * 视频底图
     *
     * @param bitmap 视频底图资源
     */
    public void setImage(@NonNull Bitmap bitmap) {
        mBgImg.setImageBitmap(bitmap);
    }

    /**
     * 视频底图ImageView控件，提供给外部用图片加载工具来加载网络图片
     *
     * @return 底图ImageView
     */
    @Override
    public ImageView imageView() {
        return mBgImg;
    }

    /**
     * 设置总时长
     *
     * @param length 视频总时长
     */
    @Override
    public void setLenght(long length) {
        mLength.setText(NiceUtil.formatTime(length));
    }

    @Override
    public void setNiceVideoPlayer(INiceVideoPlayer niceVideoPlayer) {
        super.setNiceVideoPlayer(niceVideoPlayer);
    }

    /**
     * 当播放器的播放状态发生变化，在此方法中国你更新不同的播放状态的UI
     *
     * @param playState 播放状态：
     */
    @Override
    protected void onPlayStateChanged(int playState) {
        switch (playState) {
            case NiceVideoPlayer.STATE_IDLE:
                break;
            case NiceVideoPlayer.STATE_PREPARING:
                mBgImg.setVisibility(View.GONE);
                mCenterPlayBtn.setVisibility(View.GONE);
                break;
            case NiceVideoPlayer.STATE_PREPARED:
                startUpdateProgressTimer();
                break;
            case NiceVideoPlayer.STATE_PLAYING:
                mCenterPlayBtn.setVisibility(View.GONE);
                mPlayBtn.setImageResource(R.mipmap.icon_player_pause_small);
                break;
            case NiceVideoPlayer.STATE_PAUSED:
                mCenterPlayBtn.setVisibility(View.VISIBLE);
                mCenterPlayBtn.setImageResource(R.mipmap.icon_player_play_big);
                mPlayBtn.setImageResource(R.mipmap.icon_player_play_small);
                break;
            case NiceVideoPlayer.STATE_BUFFERING_PLAYING:
                mCenterPlayBtn.setVisibility(View.GONE);
                mPlayBtn.setImageResource(R.mipmap.icon_player_pause_small);
                break;
            case NiceVideoPlayer.STATE_BUFFERING_PAUSED:
                mCenterPlayBtn.setVisibility(View.VISIBLE);
                mCenterPlayBtn.setImageResource(R.mipmap.icon_player_play_big);
                mPlayBtn.setImageResource(R.mipmap.icon_player_play_small);
                break;
            case NiceVideoPlayer.STATE_ERROR:
                cancelUpdateProgressTimer();
                break;
            case NiceVideoPlayer.STATE_COMPLETED:
                cancelUpdateProgressTimer();
                break;
            default:
                break;
        }
    }

    /**
     * 当播放器的播放模式发生变化，在此方法中更新不同模式下的控制器界面。
     *
     * @param playMode 播放器的模式：
     */
    @Override
    protected void onPlayModeChanged(int playMode) {

    }

    /**
     * 重置控制器，将控制器恢复到初始状态。
     */
    @Override
    protected void reset() {
        cancelUpdateProgressTimer();

        mSeekBar.setProgress(0);
        mSeekBar.setSecondaryProgress(0);

        mCenterPlayBtn.setVisibility(View.VISIBLE);
        mBgImg.setVisibility(View.VISIBLE);

        mLength.setVisibility(View.VISIBLE);
    }

    /**
     * 更新进度，包括进度条进度，展示的当前播放位置时长，总时长等。
     */
    @Override
    protected void updateProgress() {
        long position = mNiceVideoPlayer.getCurrentPosition();
        long duration = mNiceVideoPlayer.getDuration();
        int bufferPercentage = mNiceVideoPlayer.getBufferPercentage();
        mSeekBar.setSecondaryProgress(bufferPercentage);
        int progress = (int) (100f * position / duration);
        mSeekBar.setProgress(progress);
        mCurrentPosition.setText(NiceUtil.formatTime(position));
    }

    /**
     * 手势左右滑动改变播放位置时，显示控制器中间的播放位置变化视图，
     * 在手势滑动ACTION_MOVE的过程中，会不断调用此方法。
     *
     * @param duration            视频总时长ms
     * @param newPositionProgress 新的位置进度，取值0到100。
     */
    @Override
    protected void showChangePosition(long duration, int newPositionProgress) {

    }

    /**
     * 手势左右滑动改变播放位置后，手势up或者cancel时，隐藏控制器中间的播放位置变化视图，
     * 在手势ACTION_UP或ACTION_CANCEL时调用。
     */
    @Override
    protected void hideChangePosition() {

    }

    /**
     * 手势在右侧上下滑动改变音量时，显示控制器中间的音量变化视图，
     * 在手势滑动ACTION_MOVE的过程中，会不断调用此方法。
     *
     * @param newVolumeProgress 新的音量进度，取值1到100。
     */
    @Override
    protected void showChangeVolume(int newVolumeProgress) {

    }

    /**
     * 手势在左侧上下滑动改变音量后，手势up或者cancel时，隐藏控制器中间的音量变化视图，
     * 在手势ACTION_UP或ACTION_CANCEL时调用。
     */
    @Override
    protected void hideChangeVolume() {

    }

    /**
     * 手势在左侧上下滑动改变亮度时，显示控制器中间的亮度变化视图，
     * 在手势滑动ACTION_MOVE的过程中，会不断调用此方法。
     *
     * @param newBrightnessProgress 新的亮度进度，取值1到100。
     */
    @Override
    protected void showChangeBrightness(int newBrightnessProgress) {

    }

    /**
     * 手势在左侧上下滑动改变亮度后，手势up或者cancel时，隐藏控制器中间的亮度变化视图，
     * 在手势ACTION_UP或ACTION_CANCEL时调用。
     */
    @Override
    protected void hideChangeBrightness() {

    }
}
