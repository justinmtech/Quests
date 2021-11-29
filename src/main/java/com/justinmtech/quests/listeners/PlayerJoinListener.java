package com.justinmtech.quests.listeners;

import com.justinmtech.quests.Quests;
import com.justinmtech.quests.core.Quest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    private Quests plugin;

    public PlayerJoinListener(Quests plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        plugin.getActiveQuests().add(new Quest(player, new MoveListener(plugin), "DistanceTravelled", 10));
        plugin.getActiveQuests().add(new Quest(player, new KillMobListener(plugin), "KillMob", 10));
        plugin.getActiveQuests().add(new Quest(player, new PlaceListener(plugin), "BlockPlace", 10));
        plugin.getActiveQuests().add(new Quest(player, new BreakListener(plugin), "BlockBreak", 10));
    }
}
