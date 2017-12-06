package com.samton.ibenrobotdemo.data;

/**
 * Created by admin on 2017/7/18.
 */

public class RobotFace {
    private int imageId;
    private boolean isCheck = false;
    private String text;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RobotFace(int imageId, String text,String name) {
        this.imageId = imageId;
        this.text = text;
        this.name = name;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
