package com.justinmtech.quests;

import com.justinmtech.quests.persistence.FlatfileDataHandler;
import com.justinmtech.quests.core.Quest;
import com.justinmtech.quests.listeners.*;
import com.justinmtech.quests.persistence.ManageData;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.Serializable;
import java.util.List;

//TODO test in-game
//TODO persist data through reboots
//TODO find out what the configuration requirements are

public final class Quests extends JavaPlugin implements Serializable {
    private ManageData data;
    //private List<Quest> activeQuests;

    public Quests() {
        this.data = new FlatfileDataHandler();
    }

    @Override
    public void onEnable() {
        //this.activeQuests = new ArrayList();
        // Plugin startup logic

        //load config

        //determine data location (mysql or flatfile)

        //load data if exists or create new tables if none exist

        data.initialSetup();

        makeDefaultConfig();

        System.out.println("Quests enabled!");
        this.getServer().getPluginManager().registerEvents(new BreakListener(this), this);
        this.getServer().getPluginManager().registerEvents(new KillMobListener(this), this);
        this.getServer().getPluginManager().registerEvents(new PlaceListener(this), this);
        this.getServer().getPluginManager().registerEvents(new MoveListener(this), this);
        this.getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        this.getServer().getPluginManager().registerEvents(new PlayerQuitListener(this), this);

        ConfigurationSerialization.registerClass(Quest.class);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        data.saveData((List<Player>) Bukkit.getOnlinePlayers());
        data.removeAllQuests();
        System.out.println("Quest data saved and plugin disabled!");
    }

    private void makeDefaultConfig() {
        this.getConfig().set("mysqlEnabled", false);
        this.saveConfig();
    }

    public ManageData getData() {
        return data;
    }

}
