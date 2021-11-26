package com.justinmtech.quests.listeners;

import com.justinmtech.quests.Quests;
import com.justinmtech.quests.core.Quest;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class KillMobListener implements Listener {
    private Quests plugin;

    public KillMobListener(Quests plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onMobKill(EntityDeathEvent e) {
        LivingEntity mob = e.getEntity();
        Player player = (Player) e.getEntity().getLastDamageCause().getEntity();

        Quest quest = plugin.getActiveQuests()
                .stream().filter(q -> q.getPlayer().equals(player))
                .findAny().orElseThrow(null);
        quest.incrementProgress();
        plugin.getActiveQuests().stream().filter(q -> q.getPlayer().equals(player) && q.getTask().equals(this)).findAny().orElseThrow(null).incrementProgress();
        player.sendMessage(ChatColor.YELLOW + "You have killed" + quest.getProgress() + "/" + quest.getCompletion() + " mobs!");
        if (quest.getProgress() == quest.getCompletion()) {
            quest.giveReward("Mobs Killed");
        }
    }

}
