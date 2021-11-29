package com.justinmtech.quests.persistence;

import com.justinmtech.quests.core.Quest;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.List;

public class MySQLDataHandler implements ManageData {
    @Override
    public void initialSetup() {
        //create database and tables
    }

    @Override
    public List<Quest> getAllActiveQuests() {
        return null;
    }

    @Override
    public void storeAllActiveQuests(List<Quest> quests) {

    }

    @Override
    public void createQuest(Quest quest) {

    }

    @Override
    public Quest getQuest(Player player, String type) {
        return null;
    }

    @Override
    public List<Quest> getQuestsByPlayer(Player player) {
        return null;
    }

    @Override
    public void updateQuest(Quest quest) {

    }

    @Override
    public void deleteQuest(Quest quest) {

    }

    @Override
    public void deleteAllQuests() {

    }

    @Override
    public void deleteAllQuestsFromPlayer(Player player) {

    }
}
