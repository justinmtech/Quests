package com.justinmtech.quests.core;

import org.bukkit.event.Listener;

public class Task {
    private String taskName;
    private String taskDescription;
    private Listener event;
    private int completion;

    public Task(String taskName, String taskDescription, Listener event, int completion) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.event = event;
        this.completion = completion;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public Listener getEvent() {
        return event;
    }

    public void setEvent(Listener event) {
        this.event = event;
    }

    public int getCompletion() {
        return completion;
    }

    public void setCompletion(int completion) {
        this.completion = completion;
    }
}
