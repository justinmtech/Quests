package com.justinmtech.quests.core;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedList;
import java.util.List;

public class
Quest {
    private Player player;
    private Listener task;
    private int progress;
    private int completion;

    public Quest(Player player, int progress, int completion) {
        this.player = player;
        this.progress = progress;
        this.completion = completion;
    }

    public Quest(Player player, Listener task) {
        this.player = player;
        this.task = task;
    }

    public Quest() {
    }

    public void giveReward(String questName) {
        player.sendMessage("You completed the " + questName + " quest!");
        player.getInventory().addItem(new ItemStack(Material.DIAMOND));
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

    public Listener getTask() {
        return task;
    }

    public void setTask(Listener task) {
        this.task = task;
    }

    public void incrementProgress() {
        this.progress += 1;
    }
}
