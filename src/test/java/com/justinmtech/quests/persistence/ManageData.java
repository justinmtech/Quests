package com.justinmtech.quests.persistence;

import com.justinmtech.quests.core.Quest;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.List;

public interface ManageData {

    void initialSetup();

    List<Quest> getAllActiveQuests();

    void storeAllActiveQuests(List<Quest> quests);

    void createQuest(Quest quest);

    Quest getQuest(Player player, Listener listener);

    List<Quest> getQuestsByPlayer(Player player);

    void updateQuest(Quest quest);

    void deleteQuest(Quest quest);

    void deleteAllQuests();

    void deleteAllQuestsFromPlayer(Player player);
}
