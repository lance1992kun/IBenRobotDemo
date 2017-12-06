package com.samton.ibenrobotdemo.data;

/**
 * <pre>
 *     author : syk
 *     e-mail : shenyukun1024@gmail.com
 *     time   : 2017/10/31
 *     desc   : 上传地图实体类
 *     version: 1.0
 * </pre>
 */

public class UploadMapBean {


    /**
     * _token_iben : null
     * rs : 1
     * msg : 地图【第二1个地图】创建成功，此地图包含2个位置点
     */

    private Object _token_iben;
    private int rs;
    private String msg;
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public void set_token_iben(Object _token_iben) {
        this._token_iben = _token_iben;
    }

    public void setRs(int rs) {
        this.rs = rs;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object get_token_iben() {
        return _token_iben;
    }

    public int getRs() {
        return rs;
    }

    public String getMsg() {
        return msg;
    }

    public class Data{
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
