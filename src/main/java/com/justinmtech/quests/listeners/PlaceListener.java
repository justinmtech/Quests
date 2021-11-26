package com.justinmtech.quests.listeners;

import com.justinmtech.quests.Quests;
import com.justinmtech.quests.core.Quest;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class PlaceListener implements Listener {
    private Quests plugin;

    public PlaceListener(Quests plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        Player player = e.getPlayer();
        Quest quest = plugin.getActiveQuests().stream()
                .filter(q -> q.getPlayer().equals(player))
                .findAny().orElseThrow(null);
        plugin.getActiveQuests().stream()
                .filter(q -> q.getPlayer().equals(player) && q.getTask().equals(this))
                .findAny().orElseThrow(null).incrementProgress();
        player.sendMessage(
                ChatColor.YELLOW + "You have placed"
                        + quest.getProgress() + "/" + quest.getCompletion() + " blocks!");
        if (quest.getProgress() == quest.getCompletion()) {
            quest.giveReward("Blocks Placed");
        }
    }
}
