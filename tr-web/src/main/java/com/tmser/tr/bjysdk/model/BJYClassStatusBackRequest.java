package com.tmser.tr.bjysdk.model;

/**
 * Created by yfwang on 2017/11/16.
 */
public class BJYClassStatusBackRequest {

    /**
     * 直播教室id
     */
    private String room_id;
    /**
     *	操作类型 上课:start 下课:end
     */
    private String op;
    /**
     *操作时间 格式如：2017-01-11 14:16:54
     */
    private String op_time;
    /**
     *唯一的请求ID，长度24位
     */
    private String qid;
    /**
     *当前时间，unix时间戳
     */
    private int timestamp;
    /**
     *签名
     */
    private String sign;

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getOp_time() {
        return op_time;
    }

    public void setOp_time(String op_time) {
        this.op_time = op_time;
    }

    public String getQid() {
        return qid;
    }

    public void setQid(String qid) {
        this.qid = qid;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
