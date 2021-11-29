package com.justinmtech.quests.listeners;

import com.justinmtech.quests.Quests;
import com.justinmtech.quests.core.Quest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;

public class PlayerJoinListener implements Listener {
    private Quests plugin;

    public PlayerJoinListener(Quests plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        File file = new File("plugins//Quests//data//" + player.getUniqueId() + ".yml");
        if (!file.exists()) {
            try {
                plugin.getData().getActiveQuests().add(new Quest(player, "DistanceTravelled", 10));
                plugin.getData().getActiveQuests().add(new Quest(player, "KillMob", 10));
                plugin.getData().getActiveQuests().add(new Quest(player, "BlockPlace", 10));
                plugin.getData().getActiveQuests().add(new Quest(player, "BlockBreak", 10));
                System.out.println(plugin.getData().getActiveQuests().size());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            plugin.getData().saveData(player);
        } else {
            plugin.getData().loadData(player);
        }
    }
}
