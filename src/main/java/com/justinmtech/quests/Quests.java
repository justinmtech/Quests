package com.justinmtech.quests;

import com.justinmtech.quests.core.Quest;
import com.justinmtech.quests.listeners.BreakListener;
import com.justinmtech.quests.listeners.KillMobListener;
import com.justinmtech.quests.listeners.PlaceListener;
import com.justinmtech.quests.listeners.WalkListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class Quests extends JavaPlugin {
    List<Quest> activeQuests;

    @Override
    public void onEnable() {
        // Plugin startup logic
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
    }

    public List<Quest> getActiveQuests() {
        return activeQuests;
    }

    public void setActiveQuests(List<Quest> activeQuests) {
        this.activeQuests = activeQuests;
    }
}
