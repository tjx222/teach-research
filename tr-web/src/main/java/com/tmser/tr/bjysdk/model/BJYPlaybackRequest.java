package com.tmser.tr.bjysdk.model;

/**
 * Created by yfwang on 2017/11/16.
 */
public class BJYPlaybackRequest {
    /**
     *房间ID
     */
    private String room_id;
    /**
     *云端录制视频ID
     */
    private int video_id=-1;
    /**
     *视频状态（20:开始生成回放 30:转码失败 100:转码成功
     */
    private int status=-1;
    /**
     *封面url，（转码成功回调才有）
     */
    private String preface_url;
    /**
     *总视频大小（源文件+所有转码后文件）单位：字节（转码成功回调才有）
     */
    private int total_transcode_size=-1;
    /**
     *视频大小，单位是字节 （转码成功回调才有）
     */
    private int total_size=-1;
    /**
     *视频时长，单位为秒（转码成功回调才有）
     */
    private int length=-1;
    /**
     *视频文件md5值（转码成功回调才有）
     */
    private String file_md5;
    /**
     *当前已转出的清晰度，多种清晰度以英文逗号分隔 low/std/high/super/1080p（转码成功回调才有）
     */
    private String now_definition;
    /**
     *原始视频清晰度 low/std/high/super/1080p（转码成功回调才有）
     */
    private String origin_definition;
    /**
     *标记一次请求的唯一ID
     */
    private String qid;
    /**
     *unixstamp时间戳，秒数
     */
    private int timestamp;
    /**
     *签名字段
     */
    private String sign;

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public int getVideo_id() {
        return video_id;
    }

    public void setVideo_id(int video_id) {
        this.video_id = video_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPreface_url() {
        return preface_url;
    }

    public void setPreface_url(String preface_url) {
        this.preface_url = preface_url;
    }

    public int getTotal_transcode_size() {
        return total_transcode_size;
    }

    public void setTotal_transcode_size(int total_transcode_size) {
        this.total_transcode_size = total_transcode_size;
    }

    public int getTotal_size() {
        return total_size;
    }

    public void setTotal_size(int total_size) {
        this.total_size = total_size;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getFile_md5() {
        return file_md5;
    }

    public void setFile_md5(String file_md5) {
        this.file_md5 = file_md5;
    }

    public String getNow_definition() {
        return now_definition;
    }

    public void setNow_definition(String now_definition) {
        this.now_definition = now_definition;
    }

    public String getOrigin_definition() {
        return origin_definition;
    }

    public void setOrigin_definition(String origin_definition) {
        this.origin_definition = origin_definition;
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
