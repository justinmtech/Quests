package com.justinmtech.quests;

import com.justinmtech.quests.core.Data;
import com.justinmtech.quests.core.Quest;
import com.justinmtech.quests.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.Serializable;
import java.util.List;

//TODO test in-game
//TODO persist data through reboots
//TODO find out what the configuration requirements are

public final class Quests extends JavaPlugin implements Serializable {
    private Data data;
    //private List<Quest> activeQuests;

    public Quests() {
        this.data = new Data();
    }

    @Override
    public void onEnable() {
        //this.activeQuests = new ArrayList();
        // Plugin startup logic

        //load config

        //determine data location (mysql or flatfile)

        //load data if exists or create new tables if none exist

        data.createDataDirectory();

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
        data.saveDataForListOfPlayers((List<Player>) Bukkit.getOnlinePlayers());
        data.getActiveQuests().clear();
        System.out.println("Quest data saved and plugin disabled!");
    }

    private void makeDefaultConfig() {
/*        try {
            this.getDataFolder().createNewFile();
            this.getDataFolder().mkdir();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        this.getConfig().set("mysqlEnabled", false);
        this.saveConfig();
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    private void createDataFolder() {

    }
}
