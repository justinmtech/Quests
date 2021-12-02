package com.justinmtech.quests.listeners;

import com.justinmtech.quests.Quests;
import com.justinmtech.quests.core.Quest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;

public class PlayerJoinListener implements Listener {
    private final Quests plugin;

    public PlayerJoinListener(Quests plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        boolean mysqlEnabled = plugin.getConfig().getBoolean("mysql.enabled");
        Player player = e.getPlayer();
        File file = new File("plugins//Quests//data//" + player.getUniqueId() + ".yml");

        if ((!plugin.getData().loadData(player) && mysqlEnabled) || (!file.exists() && !mysqlEnabled)) {
            plugin.getData().getAllQuests().add(new Quest(player, "BlockBreak", 10));
            plugin.getData().getAllQuests().add(new Quest(player, "BlockPlace", 10));
            plugin.getData().getAllQuests().add(new Quest(player, "KillMob", 2));
            plugin.getData().getAllQuests().add(new Quest(player, "DistanceTravelled", 100));

            plugin.getData().saveData(player);
        }
    }
}
