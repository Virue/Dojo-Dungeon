package com.example.dojodungeons;

public class Quest {
    private int questNum;
    private String description;
    private int staminaValue;
    private int progress;

    public static final Quest[] quests = {
            new Quest(1,"Take 100 Steps", 37, 22)
    };

    public Quest() {

    }

    public Quest(int questNum, String description, int staminaValue, int progress) {
        this.questNum = questNum;
        this.description = description;
        this.staminaValue = staminaValue;
        this.progress = progress;
    }

    public int getquestNum() {
        return questNum;
    }

    public String getdescription() {
        return description;
    }

    public void setdescription(String description) {
        this.description = description;
    }

    public int getstaminaValue() {
        return staminaValue;
    }

    public void setstaminaValue(int staminaValue) {
        this.staminaValue = staminaValue;
    }


    public void setquestNum(int questNum) {
        this.questNum = questNum;
    }

    public void setprogress(int progress) {
        this.progress = progress;
    }

    public int getprogress() {return progress;}


}
