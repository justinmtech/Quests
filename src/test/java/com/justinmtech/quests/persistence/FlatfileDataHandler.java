package com.justinmtech.quests.persistence;

import com.justinmtech.quests.core.Quest;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FlatfileDataHandler implements ManageData {


    @Override
    public void initialSetup() {
        //create data file if none exists

    }

    @Override
    public List<Quest> getAllActiveQuests() {
        List<Quest> quests = new ArrayList<>();
        //return list of all active quests
        return quests;
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
                fileConfig.set("quests." + quest.getTask().toString(), quest.getTask());
                fileConfig.set("quests." + quest.getTask().toString() + ".progress", quest.getProgress());
                fileConfig.set("quests." + quest.getTask().toString() + ".completion", quest.getCompletion());
                fileConfig.save(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Quest getQuest(Player player, Listener listener) {
        Quest quest = null;
        try {
            File file = new File("/data" + player.getPlayer().getUniqueId() + ".yml");
            if (file.exists()) {
                FileConfiguration fileConfig = YamlConfiguration.loadConfiguration(file);
                quest.setPlayer(player);
                quest.setTask((Listener)fileConfig.get("quests." + listener.toString()));
                quest.setProgress((int)fileConfig.get("quests." + listener.toString() + ".progress"));
                quest.setCompletion((int)fileConfig.get("quests." + listener.toString() + ".completion"));
                return quest;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return quest;
    }

    @Override
    public List<Quest> getQuestsByPlayer(Player player) {
        List<Quest> quests = new ArrayList<>();
        try {
            File file = new File("/data" + player.getUniqueId() + ".yml");
            if (file.exists()) {
                FileConfiguration fileConfig = YamlConfiguration.loadConfiguration(file);
                List list = (List)fileConfig.get("quests");
                int listSize = list.size();

                for (int i = 0; i < listSize; i++) {
                Quest quest = new Quest();
                quest.setPlayer(player);
                quest.setTask((Listener)fileConfig.get("quests." + list.get(i) + ".task"));
                quest.setProgress((int)fileConfig.get("quests." + list.get(i) + "progress"));
                quest.setCompletion((int)fileConfig.get("completion"));
                quests.add(quest);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return quests;
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
