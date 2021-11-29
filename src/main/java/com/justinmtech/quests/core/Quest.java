package com.justinmtech.quests.core;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Quest implements ConfigurationSerializable {
    private transient Player player;
    private UUID uuid;
    private String type;
    private int progress;
    private int completion;
    //private static transient final long serialVersionUID = -1681012206529286330L;

    public Quest(Player player, int progress, int completion) {
        this.player = player;
        this.uuid = player.getUniqueId();
        this.progress = progress;
        this.completion = completion;
    }

    public Quest(Player player, String type, int completion) {
        this.player = player;
        this.uuid = player.getUniqueId();
        this.type = type;
        this.completion = completion;
    }

    public Quest() {
    }

    public Quest(Map<String, Object> serializedQuest) {
        this.uuid = UUID.fromString((String) serializedQuest.get("player-uuid"));
        this.type = (String) serializedQuest.get("type");
        this.progress = (int) serializedQuest.get("progress");
        this.completion = (int) serializedQuest.get("completion");
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
        HashMap<String, Object> mapSerializer = new HashMap<>();

        mapSerializer.put("player-uuid", uuid.toString());
        mapSerializer.put("type", type);
        mapSerializer.put("progress", progress);
        mapSerializer.put("completion", completion);

        return mapSerializer;
    }
}
