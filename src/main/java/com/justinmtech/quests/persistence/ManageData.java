package com.justinmtech.quests.persistence;

import com.justinmtech.quests.core.Quest;
import org.bukkit.entity.Player;

import java.util.List;

public interface ManageData {

    boolean initialSetup();

    List<Quest> getAllQuests();
    Quest getQuest(Player player, String type);
    List<Quest> getQuests(Player player);

    boolean loadData(Player player);
    boolean saveData(List<Player> players);
    boolean saveData(Player player);

    boolean hasQuest(Player player, String type);

    void removeQuest(Player player, String type);
    void removeAllQuests();
    void removeAllQuests(Player player);
}

