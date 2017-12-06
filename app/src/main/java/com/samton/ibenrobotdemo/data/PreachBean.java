package com.samton.ibenrobotdemo.data;

import java.util.List;

/**
 * Created by 曹大勇 on 2017/8/12.
 */

public class PreachBean {
    private String Command;
    private List<Position> positions;
    private List<String> contents;

    public String getCommand() {
        return Command;
    }

    public void setCommand(String command) {
        Command = command;
    }

    public List<Position> getPositions() {
        return positions;
    }

    public void setPositions(List<Position> positions) {
        this.positions = positions;
    }

    public List<String> getContents() {
        return contents;
    }

    public void setContents(List<String> contents) {
        this.contents = contents;
    }

    public static class Position{
        private String xPos;
        private String yPos;
        private String Yaw;

        public Position(String xPos, String yPos, String yaw) {
            this.xPos = xPos;
            this.yPos = yPos;
            Yaw = yaw;
        }

        public String getxPos() {
            return xPos;
        }

        public void setxPos(String xPos) {
            this.xPos = xPos;
        }

        public String getyPos() {
            return yPos;
        }

        public void setyPos(String yPos) {
            this.yPos = yPos;
        }

        public String getYaw() {
            return Yaw;
        }

        public void setYaw(String yaw) {
            Yaw = yaw;
        }
    }
}
