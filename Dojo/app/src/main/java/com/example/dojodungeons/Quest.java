package com.example.dojodungeons;

public class Quest {
    private int questNum;
    private String description;
    private int staminaValue;
    private int progress;
    private int current;
    private int goal;

    public static final Quest[] quests = {
            new Quest(1,"Slow and Steady: Take 100 steps", 2, 0, 1, 100),
            new Quest(2,"Walk it off: walk 200 steps", 4 , 0 , 2 , 200)

    };

    public Quest() {
    }

    public Quest(int questNum, String description, int staminaValue, int progress, int current, int goal) {
        this.questNum = questNum;
        this.description = description;
        this.staminaValue = staminaValue;
        this.progress = progress;
        this.current = current;
        this.goal = goal;
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

    public void setcurrent(int current) {
        this.current = current;
    }

    public int getcurrent() {return current;}

    public void setgoal(int goal) {
        this.goal = goal;
    }

    public int getgoal() {return goal;}


}
