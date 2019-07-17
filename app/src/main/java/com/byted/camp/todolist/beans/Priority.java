package com.byted.camp.todolist.beans;

import android.graphics.Color;

public enum Priority {
    LOW(1,Color.GREEN),MIDDLE(2,Color.YELLOW),HIGH(3,Color.RED);

    public final int intValue;
    public final int setColor;

    Priority(int intValue, int setColor) {
        this.intValue = intValue;
        this.setColor = setColor;
    }

    public static Priority from(int intValue) {
        for (Priority priority : Priority.values()) {
            if (priority.intValue == intValue) {
                return priority;
            }
        }
        return LOW;
    }
}
