package com.justinmtech.quests;

import com.justinmtech.quests.listeners.*;
import com.justinmtech.quests.core.Quest;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//TODO test in-game
//TODO persist data through reboots
//TODO find out what the configuration requirements are

public final class Quests extends JavaPlugin {
    private List<Quest> activeQuests;

    @Override
    public void onEnable() {
        this.activeQuests = new ArrayList();
        // Plugin startup logic

        //load config

        //determine data location (mysql or flatfile)

        //load data if exists or create new tables if none exist


        //
        System.out.println("Quests enabled!");
        this.getServer().getPluginManager().registerEvents(new BreakListener(this), this);
        this.getServer().getPluginManager().registerEvents(new KillMobListener(this), this);
        this.getServer().getPluginManager().registerEvents(new PlaceListener(this), this);
        this.getServer().getPluginManager().registerEvents(new MoveListener(this), this);
        this.getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("Quests disabled!");

        //save data
    }

    private void createDataFolder() {

    }

    public List<Quest> getActiveQuests() {
        return activeQuests;
    }

    public List<Quest> getActiveQuestsByPlayer(Player player) {
        List<Quest> quests = null;
        try {
           quests = activeQuests.stream().filter(q -> q.getPlayer().equals(player)).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return quests;
    }

    public void removeQuestByPlayerAndType(Player player, String type) {
        Quest quest = getActiveQuestByPlayerAndType(player, type);
        activeQuests.removeIf(q -> q.equals(quest));
        System.out.println(quest.getType() + " quest removed from player");
        System.out.println(activeQuests);
    }

    public Quest getActiveQuestByPlayerAndType(Player player, String type) {
        Quest quest = null;
        return activeQuests.stream().filter(q -> q.getPlayer().equals(player) && q.getType().equals(type)).findAny().orElseThrow(null);
    }

    public void setActiveQuests(List<Quest> activeQuests) {
        this.activeQuests = activeQuests;
    }

    public boolean hasActiveQuestOfType(Player player, String type) {
        return activeQuests.stream().anyMatch(q -> q.getPlayer().equals(player) && q.getType().equals(type));
    }
}
