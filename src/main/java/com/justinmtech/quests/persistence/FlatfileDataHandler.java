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
    private final List<Quest> quests;
    FileConfiguration config;

    public FlatfileDataHandler() {
        this.quests = new ArrayList<>();
    }

    @Override
    public void removeQuest(Player player, String type) {
        quests.remove(getQuest(player, type));
    }

    @Override
    public boolean hasQuest(Player player, String type) {
        return quests.stream().anyMatch(q -> q.getPlayer().equals(player) && q.getType().equals(type));
    }

    @Override
    public boolean initialSetup() {
        boolean setup = false;
        try {
            File directory = new File("plugins//Quests//data");
            if (!directory.exists()) {
                setup = directory.mkdir();
            }
        } catch (Exception e) {
            e.printStackTrace();
            setup = false;
        }
        return setup;
    }

    @Override
    public List<Quest> getAllQuests() {
        return quests;
    }

    @Override
    public Quest getQuest(Player player, String type) {
        return quests.stream().filter(q -> q.getPlayer().equals(player) && q.getType().equals(type)).findAny().orElseThrow(null);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean loadData(Player player) {
        String filePath = "plugins//Quests//data//" + player.getUniqueId() + ".yml";
        File file = new File(filePath);
        try {
            if (file.exists()) {
                config = YamlConfiguration.loadConfiguration(file);
                List<Quest> quests = (List<Quest>) config.getList("quests");
                assert quests != null;
                for (Quest quest : quests) {
                    quest.setPlayer(player);
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
                return file.createNewFile();
            }

            List<Quest> quests = getQuests(player);

            if (quests.size() == 0) {
                return file.delete();
            }

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
            for (Player player : players) {
                saveData(player);
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
