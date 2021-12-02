package com.justinmtech.quests.listeners;

import com.justinmtech.quests.Quests;
import com.justinmtech.quests.core.Quest;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class PlaceListener implements Listener {
    private final Quests plugin;
    private final static String TYPE = "BlockPlace";
    private final String rewardCommand;

    public PlaceListener(Quests plugin) {
        this.plugin = plugin;
        rewardCommand = plugin.getConfig().getString("rewardCommands.BlockPlace");
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        Player player = e.getPlayer();
        if (plugin.getData().hasQuest(player, TYPE)) {
            try {
                Quest quest = plugin.getData().getQuest(player, TYPE);
                quest.incrementProgress();

                if (quest.getProgress() <= quest.getCompletion()) {
                    player.sendMessage(ChatColor.GOLD + "You have placed " + quest.getProgress() + "/" + quest.getCompletion() + " blocks!");
                }

                if (quest.getProgress() == quest.getCompletion()) {
                    quest.giveReward(player, rewardCommand);
                    plugin.getData().removeQuest(player, TYPE);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}
