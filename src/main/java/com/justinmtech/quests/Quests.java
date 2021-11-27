package com.justinmtech.quests;

import com.justinmtech.quests.core.Quest;
import com.justinmtech.quests.listeners.BreakListener;
import com.justinmtech.quests.listeners.KillMobListener;
import com.justinmtech.quests.listeners.PlaceListener;
import com.justinmtech.quests.listeners.WalkListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

//TODO test in-game
//TODO persist data through reboots
//TODO find out what the configuration requirements are

public final class Quests extends JavaPlugin {
    List<Quest> activeQuests;

    @Override
    public void onEnable() {
        // Plugin startup logic

        //load config

        //determine data location (mysql or flatfile)

        //load data if exists or create new tables if none exist

        //
        System.out.println("Quests enabled!");
        this.getServer().getPluginManager().registerEvents(new BreakListener(this), this);
        this.getServer().getPluginManager().registerEvents(new KillMobListener(this), this);
        this.getServer().getPluginManager().registerEvents(new PlaceListener(this), this);
        this.getServer().getPluginManager().registerEvents(new WalkListener(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("Quests disabled!");

        //save data
    }

    public List<Quest> getActiveQuests() {
        return activeQuests;
    }

    public void setActiveQuests(List<Quest> activeQuests) {
        this.activeQuests = activeQuests;
    }
}
