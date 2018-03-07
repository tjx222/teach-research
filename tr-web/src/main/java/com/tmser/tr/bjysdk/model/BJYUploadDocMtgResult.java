package com.tmser.tr.bjysdk.model;

/**
 * Created by yfwang on 2017/11/7.
 *
 * 参加码是一种快速进入房间的形式，合作方把参加码发给用户，他们就可以通过参加码和昵称直接进入房间。
 参加码为6位，由字母和数字组成。
 *
 */
public class BJYUploadDocMtgResult extends BJYMtgResult{

    /**
     *文档ID
     */
    private int fid;
    /**
     *上传的文档名
     */
    private String name;
    /**
     *只有绑定到教室才会返回绑定ID
     */
    private int bind_id;

    /**
     * 业务数据
     */
    private String resId;


    public String getResId() {
        return resId;
    }

    public void setResId(String resId) {
        this.resId = resId;
    }

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBind_id() {
        return bind_id;
    }

    public void setBind_id(int bind_id) {
        this.bind_id = bind_id;
    }

}
