package com.justinmtech.quests.core;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class Data {
    private List<Quest> activeQuests;
    FileConfiguration config;

    public Data() {
        this.activeQuests = new ArrayList<>();
    }

    public List<Quest> getActiveQuests() {
        return activeQuests;
    }

    public List<Quest> getActiveQuestsByPlayer(Player player) {
        List<Quest> quests = null;
        try {
            quests = activeQuests.stream().filter(q -> q.getPlayer().equals(player)).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return quests;
    }

    public void removeQuestByPlayerAndType(Player player, String type) {
        Quest quest = getActiveQuestByPlayerAndType(player, type);
        activeQuests.removeIf(q -> q.equals(quest));
        System.out.println(quest.getType() + " quest removed from player");
        System.out.println(activeQuests);
    }

    public Quest getActiveQuestByPlayerAndType(Player player, String type) {
        Quest quest = null;
        return activeQuests.stream().filter(q -> q.getPlayer().equals(player) && q.getType().equals(type)).findAny().orElseThrow(null);
    }

    public void setActiveQuests(List<Quest> activeQuests) {
        this.activeQuests = activeQuests;
    }

    public boolean hasActiveQuestOfType(Player player, String type) {
        return activeQuests.stream().anyMatch(q -> q.getPlayer().equals(player) && q.getType().equals(type));
    }


    public boolean createDataDirectory() {
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

    public boolean saveData(Player player) {
        String filePath = "plugins//Quests//data//" + player.getUniqueId() + ".yml";
        File file = new File(filePath);
        try {
            if (!file.exists()) {
            file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            List<Quest> quests = getActiveQuestsByPlayer(player);

            config = YamlConfiguration.loadConfiguration(file);
            config.set("quests", quests);
            config.save(file);
            return true;


/*            BukkitObjectOutputStream out =
                    new BukkitObjectOutputStream(new GZIPOutputStream(new FileOutputStream(filePath)));
            out.writeObject(quests);
            out.close();
            return true;*/
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void saveDataForListOfPlayers(List<Player> players) {
        int listSize = players.size();
        for (int i = 0; i < listSize; i++) {
            saveData(players.get(i));
            System.out.println("Data saved for player " + players.get(i).getName());
        }
    }

    public boolean loadData(Player player) {
        String filePath = "plugins//Quests//data//" + player.getUniqueId() + ".yml";
        File file = new File(filePath);
        try {
            if (file.exists()) {

                config = YamlConfiguration.loadConfiguration(file);

                List<Quest> quests = (List<Quest>) config.get("quests");

/*                BukkitObjectInputStream in =
                        new BukkitObjectInputStream(new GZIPInputStream(new FileInputStream(filePath)));
                List<Quest> quests = (List<Quest>) in.readObject();
                */
                for (int i = 0; i < quests.size(); i++) {
                    quests.get(i).setPlayer(player);
                }
                System.out.println(quests);
                activeQuests.addAll(quests);
                System.out.println(activeQuests.size());
                System.out.println(player.getName() + " data loaded");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void removeAllQuestsOfPlayer(Player player) {
        activeQuests.removeIf(q -> q.getPlayer().equals(player));
        System.out.println(activeQuests.size());
    }

/*    @Override
    public Map<String, Object> serialize() {
        HashMap<String, Object> mapSerializable = new HashMap<>();

        mapSerializable.put("quests", activeQuests);

        return mapSerializable;
    }*/
}
