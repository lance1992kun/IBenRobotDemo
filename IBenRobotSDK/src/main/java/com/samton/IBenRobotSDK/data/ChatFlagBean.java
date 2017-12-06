package com.samton.IBenRobotSDK.data;

/**
 * <pre>
 *     author : syk
 *     e-mail : shenyukun1024@gmail.com
 *     time   : 2017/09/07
 *     desc   : 获取机器人聊天开关接口实体类
 *     version: 1.0
 * </pre>
 */

public final class ChatFlagBean {

    /**
     * rs : -1
     * _token_iben : null
     * accout: 082e259be6b64cee914e4c06767ac6ea_201
     * arousalWord : null
     */

    private int rs;
    private String _token_iben;
    private String arousalWord;
    private String accout;

    public String getAccout() {
        return accout;
    }

    public void setAccout(String accout) {
        this.accout = accout;
    }

    public int getRs() {
        return rs;
    }

    public void setRs(int rs) {
        this.rs = rs;
    }

    public String get_token_iben() {
        return _token_iben;
    }

    public void set_token_iben(String _token_iben) {
        this._token_iben = _token_iben;
    }

    public String getArousalWord() {
        return arousalWord;
    }

    public void setArousalWord(String arousalWord) {
        this.arousalWord = arousalWord;
    }
}
