package com.justinmtech.quests;

import com.justinmtech.quests.core.Data;
import com.justinmtech.quests.listeners.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.Serializable;

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
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("Quests disabled!");
        data.getActiveQuests().clear();

        //save data
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
