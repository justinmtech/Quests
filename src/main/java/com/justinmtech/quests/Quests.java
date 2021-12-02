package com.justinmtech.quests;

import com.justinmtech.quests.core.Quest;
import com.justinmtech.quests.listeners.*;
import com.justinmtech.quests.persistence.FlatfileDataHandler;
import com.justinmtech.quests.persistence.ManageData;
import com.justinmtech.quests.persistence.MySQLDataHandler;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.util.List;

public final class Quests extends JavaPlugin {
    private ManageData data;

    @Override
    public void onEnable() {
        generateConfigIfEmpty();
        if (setupDataHandling()) {
            this.getServer().getPluginManager().registerEvents(new BreakListener(this), this);
            this.getServer().getPluginManager().registerEvents(new KillMobListener(this), this);
            this.getServer().getPluginManager().registerEvents(new PlaceListener(this), this);
            this.getServer().getPluginManager().registerEvents(new MoveListener(this), this);
            this.getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
            this.getServer().getPluginManager().registerEvents(new PlayerQuitListener(this), this);
            ConfigurationSerialization.registerClass(Quest.class);
            System.out.println("Quests enabled!");
        } else {
            getServer().getPluginManager().disablePlugin(this);
            System.out.println("Quests has a configuration error and could not start!");
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onDisable() {
        data.saveData((List<Player>) Bukkit.getOnlinePlayers());
        data.removeAllQuests();
        System.out.println("Quest data saved and plugin disabled!");
    }

    private void generateConfigIfEmpty() {
        try {
            File file = new File("plugins//Quests//config.yml");
            if (!file.exists()) {
                this.getConfig().set("mysql.enabled", false);
                this.getConfig().set("mysql.username", "admin");
                this.getConfig().set("mysql.password", "password");
                this.getConfig().set("mysql.database", "database");
                this.getConfig().set("mysql.table", "table");
                this.getConfig().set("rewardCommands.BlockBreak", "give player diamond 1");
                this.getConfig().set("rewardCommands.BlockPlace", "give player emerald 1");
                this.getConfig().set("rewardCommands.KillMob", "give player gold_ingot 1");
                this.getConfig().set("rewardCommands.DistanceTravelled", "give player melon_slice 1");
                this.saveConfig();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ManageData getData() {
        return data;
    }

    private boolean setupDataHandling() {
        try {
            if (this.getConfig().getBoolean("mysql.enabled")) {
                data = new MySQLDataHandler(this);
            } else {
                data = new FlatfileDataHandler();
                data.initialSetup();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
