package com.upchina.vo;

public class BaseOutVo {

    private String resultCode;

    private String resultMsg;

    private Object resultData;

    public Object getResultData() {
        return resultData;
    }

    public void setResultData(Object resultData) {
        this.resultData = resultData;
    }

    public void copyTo(BaseOutVo out) {
        out.resultCode = this.resultCode;
        out.resultMsg = this.resultMsg;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

}
