package com.justinmtech.quests.persistence;

import com.justinmtech.quests.core.Quest;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FlatfileDataHandler implements ManageData {
    private List<Quest> quests;
    FileConfiguration config;

    public FlatfileDataHandler() {
        this.quests = new ArrayList<>();
    }

    @Override
    public void removeQuest(Player player, String type) {
        Quest quest = getQuest(player, type);
        quests.removeIf(q -> q.equals(quest));
    }

    @Override
    public boolean hasQuest(Player player, String type) {
        return quests.stream().anyMatch(q -> q.getPlayer().equals(player) && q.getType().equals(type));
    }

    @Override
    public boolean initialSetup() {
        try {
            File directory = new File("plugins//Quests//data");
            if (!directory.exists()) {
                directory.mkdir();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Quest> getAllQuests() {
        return quests;
    }

    @Override
    public Quest getQuest(Player player, String type) {
        return quests.stream().filter(q -> q.getPlayer().equals(player) && q.getType().equals(type)).findAny().orElseThrow(null);
    }

    @Override
    public boolean loadData(Player player) {
        String filePath = "plugins//Quests//data//" + player.getUniqueId() + ".yml";
        File file = new File(filePath);
        try {
            if (file.exists()) {

                config = YamlConfiguration.loadConfiguration(file);

                List<Quest> quests = (List<Quest>) config.get("quests");

                for (int i = 0; i < quests.size(); i++) {
                    quests.get(i).setPlayer(player);
                }
                this.quests.addAll(quests);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Quest> getQuests(Player player) {
        List<Quest> quests = null;
        try {
            quests = this.quests.stream().filter(q -> q.getPlayer().equals(player)).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return quests;
    }

    @Override
    public boolean saveData(Player player) {
        try {
            String filePath = "plugins//Quests//data//" + player.getUniqueId() + ".yml";
            File file = new File(filePath);

            if (!file.exists()) {
                file.createNewFile();
            }

            List<Quest> quests = getQuests(player);

            config = YamlConfiguration.loadConfiguration(file);
            config.set("quests", quests);
            config.save(file);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean saveData(List<Player> players) {
        try {
            int listSize = players.size();
            for (int i = 0; i < listSize; i++) {
                saveData(players.get(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void removeAllQuests() {
        quests.clear();
    }

    @Override
    public void removeAllQuests(Player player) {
        quests.removeIf(q -> q.getPlayer().equals(player));
    }
}
