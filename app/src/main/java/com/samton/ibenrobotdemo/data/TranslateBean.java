package com.samton.ibenrobotdemo.data;

/**
 * Created by admin on 2017/10/12.
 */

public class TranslateBean {
    private String Command;
    private boolean state;
    private int direction;

    public TranslateBean(String command, boolean state, int direction) {
        this.Command = command;
        this.state = state;
        this.direction = direction;
    }

    public String getCommand() {
        return Command;
    }

    public void setCommand(String command) {
        Command = command;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }
}
