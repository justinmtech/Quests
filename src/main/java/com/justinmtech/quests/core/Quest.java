package com.justinmtech.quests.core;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Quest implements ConfigurationSerializable {
    private Player player;
    private Listener task;
    private String type;
    private int progress;
    private int completion;
    private static transient final long serialVersionUID = -1681012206529286330L;

public Quest(Player player, int progress, int completion) {
        this.player = player;
        this.progress = progress;
        this.completion = completion;
    }

    public Quest(Player player, Listener task, String type, int completion) {
        this.player = player;
        this.task = task;
        this.type = type;
        this.completion = completion;
    }

    public Quest() {
    }

    public void giveReward(String questName) {
        player.sendMessage(ChatColor.AQUA + "You completed the " + questName
                + " quest and received " + ChatColor.GREEN + "1 Diamond!");
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }

    @Override
    public Map<String, Object> serialize() {
        HashMap<String, Object>  mapSerializer = new HashMap<>();

        mapSerializer.put("type", this.type);
        mapSerializer.put("progress", this.progress);
        mapSerializer.put("completion", this.completion);

        return mapSerializer;
    }
}
