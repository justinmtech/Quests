package com.justinmtech.quests.listeners;

import com.justinmtech.quests.Quests;
import com.justinmtech.quests.core.Quest;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerStatisticIncrementEvent;

public class WalkListener implements Listener {
    private Quests plugin;

    public WalkListener(Quests plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onWalk(PlayerMoveEvent e) {
        Location from = e.getFrom();
        Location to = e.getTo();

        int distance = (int) from.distance(to);

        Player player = e.getPlayer();
        Quest quest = plugin.getActiveQuests().stream()
                .filter(q -> q.getPlayer().equals(player))
                .findAny().orElseThrow(null);
        plugin.getActiveQuests().stream()
                .filter(q -> q.getPlayer().equals(player) && q.getTask().equals(this))
                .findAny().orElseThrow(null).incrementProgress();
        if (quest.getProgress() == quest.getCompletion()) {
            quest.giveReward("Blocks Travelled");
        }

    }
}
