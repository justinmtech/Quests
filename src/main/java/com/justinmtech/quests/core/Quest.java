package com.justinmtech.quests.core;

import org.bukkit.entity.Player;

import java.util.LinkedList;
import java.util.List;

public class Quest {
    private Player player;
    private List<Task> tasks;
    private int progress;
    private int completion;

    public Quest(Player player, int completion) {
        this.tasks = new LinkedList<>();
        this.player = player;
        this.progress = 0;
        this.completion = completion;
    }

    public Quest(Player player, int progress, int completion) {
        this.tasks = new LinkedList<>();
        this.player = player;
        this.progress = progress;
        this.completion = completion;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getCompletion() {
        return completion;
    }

    public void setCompletion(int completion) {
        this.completion = completion;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
