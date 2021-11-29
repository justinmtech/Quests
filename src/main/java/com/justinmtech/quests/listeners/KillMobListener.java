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
    private final Quests plugin;
    private final static String TYPE = "KillMob";

    public KillMobListener(Quests plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onMobKill(EntityDeathEvent e) {
        LivingEntity mob = e.getEntity();
        Player player = null;
        try {
        player = mob.getKiller();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (plugin.getData().hasQuest(player, TYPE)) {
            try {
                Quest quest = plugin.getData().getQuest(player, TYPE);
                quest.incrementProgress();

                if (quest.getProgress() <= quest.getCompletion()) {
                    player.sendMessage(ChatColor.GOLD + "You have killed " + quest.getProgress() + "/" + quest.getCompletion() + " mobs!");
                }

                if (quest.getProgress() == quest.getCompletion()) {
                    quest.giveReward("Mobs Killed");
                    plugin.getData().removeQuest(player, TYPE);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}
