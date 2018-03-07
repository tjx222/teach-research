package com.tmser.tr.bjysdk.model;

/**
 * Created by yfwang on 2017/11/7.
 *
 * 参加码是一种快速进入房间的形式，合作方把参加码发给用户，他们就可以通过参加码和昵称直接进入房间。
 参加码为6位，由字母和数字组成。
 *
 */
public class BJYTokenMtgResult extends BJYMtgResult{

    /**
     *回放token
     */
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
