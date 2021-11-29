package com.justinmtech.quests.listeners;

import com.justinmtech.quests.Quests;
import com.justinmtech.quests.core.Quest;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MoveListener implements Listener {
    private final Quests plugin;
    private final static String TYPE = "DistanceTravelled";

    public MoveListener(Quests plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onWalk(PlayerMoveEvent e) {
        try {
        Player player = e.getPlayer();
            if (plugin.getData().hasQuest(player, TYPE)) {
                Quest quest = plugin.getData().getQuest(player, TYPE);
                int distanceWalked = player.getStatistic(Statistic.WALK_ONE_CM) / 100;
                int distanceSprinted = player.getStatistic(Statistic.SPRINT_ONE_CM) / 100;
                int distanceTravelled = distanceWalked + distanceSprinted;
                System.out.println(distanceTravelled);
                    if (distanceTravelled >= quest.getCompletion()) {
                        quest.giveReward("Distance Travelled");
                        plugin.getData().removeQuest(player, TYPE);
                    }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
