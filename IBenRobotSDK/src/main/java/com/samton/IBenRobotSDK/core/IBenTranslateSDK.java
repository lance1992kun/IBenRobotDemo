package com.samton.IBenRobotSDK.core;

import com.samton.IBenRobotSDK.interfaces.IBenTranslateCallBack;
import com.youdao.sdk.app.Language;
import com.youdao.sdk.app.LanguageUtils;
import com.youdao.sdk.ydonlinetranslate.TranslateErrorCode;
import com.youdao.sdk.ydonlinetranslate.TranslateListener;
import com.youdao.sdk.ydonlinetranslate.TranslateParameters;
import com.youdao.sdk.ydonlinetranslate.Translator;
import com.youdao.sdk.ydtranslate.Translate;

/**
 * <pre>
 *     author : syk
 *     e-mail : shenyukun1024@gmail.com
 *     time   : 2017/10/10
 *     desc   : 小笨翻译SDK
 *     version: 1.0
 * </pre>
 */

public final class IBenTranslateSDK implements TranslateListener {
    /**
     * 单例模式
     */
    private static IBenTranslateSDK instance = null;
    /**
     * 翻译回调
     */
    private IBenTranslateCallBack mCallBack = null;
    /**
     * 翻译工具
     */
    private Translator translator = null;

    /**
     * 获取翻译SDK单例
     *
     * @return 翻译SDK单例
     */
    public static IBenTranslateSDK getInstance() {
        if (instance == null) {
            synchronized (IBenTranslateSDK.class) {
                if (instance == null) {
                    instance = new IBenTranslateSDK();
                }
            }
        }
        return instance;
    }

    /**
     * 私有构造函数
     */
    private IBenTranslateSDK() {
    }

    /**
     * 初始化翻译SDK
     *
     * @param mCallBack 查询结果回调
     */
    public void init(IBenTranslateCallBack mCallBack) {
        this.mCallBack = mCallBack;
    }

    /**
     * 进行翻译
     *
     * @param input 要翻译的话
     * @param from  从那种语言
     * @param to    到那种语言
     */
    public void translate(String input, String from, String to) {
        // 源语言
        Language langFrom = LanguageUtils.getLangByName(from);
        // 翻译的语言
        Language langTo = LanguageUtils.getLangByName(to);
        // 构建翻译参数
        TranslateParameters tps = new TranslateParameters.Builder()
                .source("youdao").from(langFrom).to(langTo).timeout(3000).build();
        // 初始化翻译工具
        translator = Translator.getInstance(tps);
        // 开始翻译
        translator.lookup(input, this);
    }

    /**
     * 翻译发生错误
     *
     * @param translateErrorCode 错误代码
     */
    @Override
    public void onError(TranslateErrorCode translateErrorCode) {
        mCallBack.onError(translateErrorCode.toString());
    }

    /**
     * 翻译结果
     *
     * @param translate 翻译结果
     * @param s         输入内容
     */
    @Override
    public void onResult(Translate translate, String s) {
        // 暂取第一个
        String result = translate.getTranslations().get(0);
        mCallBack.onResult(result);
    }
}
