package com.justinmtech.quests.core;

import com.justinmtech.quests.Quests;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.zip.GZIPOutputStream;

public class Data implements ConfigurationSerializable {
    private List<Quest> activeQuests;
    //private static transient final long serialVersionUID = -1681012206529286330L;

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
            //BukkitObjectOutputStream out =
            //        new BukkitObjectOutputStream(new GZIPOutputStream(new FileOutputStream(filePath)));
            //out.writeObject(this);
            //out.close();
            Configuration config = YamlConfiguration.loadConfiguration(file);
            config.set("quests", getActiveQuestsByPlayer(player));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void removeAllQuestsOfPlayer(Player player) {
        activeQuests.removeIf(q -> q.getPlayer().equals(player));
        System.out.println(activeQuests.size());
    }

    @Override
    public Map<String, Object> serialize() {
        HashMap<String, Object> mapSerializer = new HashMap<>();

        //mapSerializer.put("quests", getActiveQuestsByPlayer(this.player));

        return mapSerializer;
    }
}
