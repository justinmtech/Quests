package com.justinmtech.quests.listeners;

import com.justinmtech.quests.Quests;
import com.justinmtech.quests.core.Quest;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.io.Serializable;

public class PlaceListener implements Listener, Serializable {
    private Quests plugin;
    private final static String TYPE = "BlockPlace";

    public PlaceListener(Quests plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        Player player = e.getPlayer();
        if (plugin.getData().hasActiveQuestOfType(player, TYPE)) {
            try {
                Quest quest = plugin.getData().getActiveQuestByPlayerAndType(player, TYPE);
                quest.incrementProgress();

                if (quest.getProgress() <= quest.getCompletion()) {
                    player.sendMessage(ChatColor.GOLD + "You have placed " + quest.getProgress() + "/" + quest.getCompletion() + " blocks!");
                }

                if (quest.getProgress() == quest.getCompletion()) {
                    quest.giveReward("Blocks Placed");
                    plugin.getData().removeQuestByPlayerAndType(player, TYPE);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
