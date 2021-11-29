package com.justinmtech.quests.listeners;

import com.justinmtech.quests.Quests;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {
    private Quests plugin;

    public PlayerQuitListener(Quests plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        plugin.getData().saveData(player);
        this.plugin.getData().removeAllQuestsOfPlayer(player);
    }
}
