package com.samton.IBenRobotSDK.data;

/**
 * <pre>
 *     author : syk
 *     e-mail : shenyukun1024@gmail.com
 *     time   : 2017/09/12
 *     desc   : 激活机器人实体类
 *     version: 1.0
 * </pre>
 */

public final class ActiveBean {

    /**
     * rs : -1
     * _token_iben : null
     * data : {"errorCode":"2402","errorMsg":"该标识的机器人已经激活！"}
     */

    private int rs;
    private String _token_iben;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * errorCode : 2402
         * errorMsg : 该标识的机器人已经激活！
         */

        private String errorCode;
        private String errorMsg;

        public String getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(String errorCode) {
            this.errorCode = errorCode;
        }

        public String getErrorMsg() {
            return errorMsg;
        }

        public void setErrorMsg(String errorMsg) {
            this.errorMsg = errorMsg;
        }
    }
}
