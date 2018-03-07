package com.tmser.tr.bjysdk.model;

/**
 * Created by yfwang on 2017/11/7.
 *
 * 参加码是一种快速进入房间的形式，合作方把参加码发给用户，他们就可以通过参加码和昵称直接进入房间。
 参加码为6位，由字母和数字组成。
 *
 */
public class BJYCreateMtgResult extends BJYMtgResult{

    /**
     *房间ID,14位的数字
     */
    private String room_id;
    /**
     *管理员进入房间的参加码
     */
    private String admin_code;
    /**
     *老师进入房间的参加码
     */
    private String teacher_code;
    /**
     *学生公共参加码，该参加码可以进多个学生，不互踢
     */
    private String student_code;

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public String getAdmin_code() {
        return admin_code;
    }

    public void setAdmin_code(String admin_code) {
        this.admin_code = admin_code;
    }

    public String getTeacher_code() {
        return teacher_code;
    }

    public void setTeacher_code(String teacher_code) {
        this.teacher_code = teacher_code;
    }

    public String getStudent_code() {
        return student_code;
    }

    public void setStudent_code(String student_code) {
        this.student_code = student_code;
    }
}
