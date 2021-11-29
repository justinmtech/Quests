package com.justinmtech.quests.listeners;

import com.justinmtech.quests.Quests;
import com.justinmtech.quests.core.Quest;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BreakListener implements Listener {
    private final Quests plugin;
    private final static String TYPE = "BlockBreak";

    public BreakListener(Quests plugin) {
    this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
            Player player = e.getPlayer();
            if (plugin.getData().hasQuest(player, TYPE)) {
                try {
                    Quest quest = plugin.getData().getQuest(player, TYPE);
                    quest.incrementProgress();

                    if (quest.getProgress() <= quest.getCompletion()) {
                    player.sendMessage(ChatColor.GOLD + "You have mined " + quest.getProgress() + "/" + quest.getCompletion() + " blocks!");
                    }

                    if (quest.getProgress() == quest.getCompletion()) {
                        quest.giveReward("Blocks Broken");
                        plugin.getData().removeQuest(player, TYPE);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
    }
}
