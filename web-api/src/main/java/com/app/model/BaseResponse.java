package com.app.model;
import io.swagger.annotations.ApiModelProperty;

public class BaseResponse {
    
    public enum MessageTypeEnum {SUCCESS, ERROR, WARNING, NO_ACCESS, NOTICE};
    protected MessageTypeEnum  msgType;
    protected String  msg;

    @ApiModelProperty(value = "Can be either SUCCESS, ERROR, WARNING, NOTICE or NO_ACCESS", example = "SUCCESS", required = true)
    public MessageTypeEnum getMsgType() {return msgType;}
    public void setMsgType(MessageTypeEnum msgType) {this.msgType = msgType;}

    @ApiModelProperty(value = "Overall message for the operation", example = "Operation was success", required = true)
    public String getMsg() {return msg;}
    public void setMsg(String message) {this.msg = message;}
    
    
    public void setSuccesMessage(String msg){
        this.msgType = MessageTypeEnum.SUCCESS;
        this.msg = msg;
    }

    public void setErrorMessage(String msg){
        this.msgType = MessageTypeEnum.ERROR;
        this.msg = msg;
    }

    public void setWarningMessage(String msg){
        this.msgType = MessageTypeEnum.WARNING;
        this.msg = msg;
    }
    
    public void setNoticeMessage(String msg){
        this.msgType = MessageTypeEnum.NOTICE;
        this.msg = msg;
    }

    public void setTypeAndMessage(MessageTypeEnum msgType, String msg){
        this.msgType = msgType;
        this.msg = msg;
    }
}
