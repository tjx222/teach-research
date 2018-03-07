package com.tmser.tr.bjysdk.model;

/**
 * Created by yfwang on 2017/11/7.
 *
 * 参加码是一种快速进入房间的形式，合作方把参加码发给用户，他们就可以通过参加码和昵称直接进入房间。
 参加码为6位，由字母和数字组成。
 *
 */
public class BJYBindDocMtgResult extends BJYMtgResult{

    /**
     *文档ID
     */
    private int fid;
    /**
     *教室号
     */
    private String room_id;

    /**
     * 绑定ID
     */
    private int bind_id;

    /**
     * 业务字段
     */
    private String redId;

    public String getRedId() {
        return redId;
    }

    public void setRedId(String redId) {
        this.redId = redId;
    }

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public int getBind_id() {
        return bind_id;
    }

    public void setBind_id(int bind_id) {
        this.bind_id = bind_id;
    }
}
