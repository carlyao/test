package com.upchina.vo;

import com.upchina.util.serializer.JsonMapper;

public class BusinessResult {

    private boolean success;

    private String businessStatus;

    private String businessOrderId;

    private String detail;

    private String request;

    private String response;

    private String businessCode;

    private String resultCode;

    private String resultMsg;

    /**
     * @return the success
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * @param success the success to set
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * @return the businessStatus
     */
    public String getBusinessStatus() {
        return businessStatus;
    }

    /**
     * @param businessStatus the businessStatus to set
     */
    public void setBusinessStatus(String businessStatus) {
        this.businessStatus = businessStatus;
    }

    /**
     * @return the businessOrderId
     */
    public String getBusinessOrderId() {
        return businessOrderId;
    }

    /**
     * @param businessOrderId the businessOrderId to set
     */
    public void setBusinessOrderId(String businessOrderId) {
        this.businessOrderId = businessOrderId;
    }

    /**
     * @return the detail
     */
    public String getDetail() {
        return detail;
    }

    /**
     * @param detail the detail to set
     */
    public void setDetail(String detail) {
        this.detail = detail;
    }

    /**
     * @return the request
     */
    public String getRequest() {
        return request;
    }

    /**
     * @param request the request to set
     */
    public void setRequest(String request) {
        this.request = request;
    }

    /**
     * @return the response
     */
    public String getResponse() {
        return response;
    }

    /**
     * @param response the response to set
     */
    public void setResponse(String response) {
        this.response = response;
    }

    /**
     * @return the businessCode
     */
    public String getBusinessCode() {
        return businessCode;
    }

    /**
     * @param businessCode the businessCode to set
     */
    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    @Override
    public String toString() {
        return JsonMapper.nonDefaultMapper().toFormatedJson(this);
    }

}
