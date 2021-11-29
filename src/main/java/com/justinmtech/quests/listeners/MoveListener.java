package com.justinmtech.quests.listeners;

import com.justinmtech.quests.Quests;
import com.justinmtech.quests.core.Quest;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.io.Serializable;

public class MoveListener implements Listener {
    private Quests plugin;
    private final static String TYPE = "DistanceTravelled";

    public MoveListener(Quests plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onWalk(PlayerMoveEvent e) {
        try {
        Player player = e.getPlayer();
            if (plugin.getData().hasActiveQuestOfType(player, TYPE)) {
                Quest quest = plugin.getData().getActiveQuestByPlayerAndType(player, TYPE);
                int distanceWalked = player.getStatistic(Statistic.WALK_ONE_CM) / 100;
                int distanceSprinted = player.getStatistic(Statistic.SPRINT_ONE_CM) / 100;
                int distanceTravelled = distanceWalked + distanceSprinted;
                System.out.println(distanceTravelled);
                    if (distanceTravelled >= quest.getCompletion()) {
                        quest.giveReward("Distance Travelled");
                        plugin.getData().removeQuestByPlayerAndType(player, TYPE);
                    }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
