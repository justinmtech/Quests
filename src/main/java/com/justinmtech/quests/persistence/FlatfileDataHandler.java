package com.justinmtech.quests.persistence;

import com.justinmtech.quests.core.Quest;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.io.File;
import java.util.List;

public class FlatfileDataHandler implements ManageData {


    @Override
    public void initialSetup() {
        //create data file if none exists

    }

    @Override
    public List<Quest> getAllActiveQuests() {
        //return list of all active quests
    }

    @Override
    public void storeAllActiveQuests(List<Quest> quests) {
        //store all active quests
    }

    @Override
    public void createQuest(Quest quest) {
        //create new quest entry
        File file = new File("/data" + quest.getPlayer().getUniqueId() + ".yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
                FileConfiguration fileConfig = YamlConfiguration.loadConfiguration(file);
                fileConfig.set("player", quest.getPlayer().getName());
                fileConfig.set("task", quest.getTask());
                fileConfig.set("progress", quest.getProgress());
                fileConfig.set("completion", quest.getCompletion());
                fileConfig.save(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Quest getQuest(Player player, Listener listener) {
        //get quest by name and listener
        Quest quest = null;
        try {
        File file = new File("/data" + player.getPlayer().getUniqueId() + ".yml");
        if (file.exists()) {

        }

        } catch (Exception e) {

        }
    }

    @Override
    public void updateQuest(Quest quest) {
        //replace a quest with new quest
    }

    @Override
    public void deleteQuest(Quest quest) {
        //delete a quest
    }

    @Override
    public void deleteAllQuests() {
        //delete all quests
    }

    @Override
    public void deleteAllQuestsFromPlayer(Player player) {
        //delete all quests matching player
    }
}
