package com.justinmtech.quests.persistence;

import com.justinmtech.quests.Quests;
import com.justinmtech.quests.core.Quest;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MySQLDataHandler implements ManageData {
    private final List<Quest> quests;
    private final String username;
    private final String password;
    private final String database;
    private final String table;


    public MySQLDataHandler(Quests plugin) {
        this.quests = new ArrayList<>();
        username = (String) plugin.getConfig().get("mysql.username");
        password = (String) plugin.getConfig().get("mysql.password");
        database = (String) plugin.getConfig().get("mysql.database");
        table = (String) plugin.getConfig().get("mysql.table");
        initialSetup();
    }

    private boolean doesTableExist() {
        String query = "select * FROM information_schema.tables " +
                "WHERE table_schema = '" + database + "'" +
                "AND table_name = '" + table + "'" +
                "LIMIT 1";
        PreparedStatement pStat;

        try {
            pStat = connect().prepareStatement(query);
            pStat.execute();
            ResultSet rs = pStat.getResultSet();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void createTable() {
            String query1 = "use " + database;
            String query2 = "create table " + table + "(" +
                    "player_uuid VARCHAR(64) NOT NULL, " +
                    "quest_type VARCHAR(64) NOT NULL, " +
                    "quest_progress INT NOT NULL, " +
                    "quest_completion INT NOT NULL" +
                    ")";
            PreparedStatement pStat;
            try {
                pStat = connect().prepareStatement(query1);
                pStat.execute();
                pStat = connect().prepareStatement(query2);
                pStat.execute();
                pStat.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    private Connection connect() {
        Connection conn;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            String url = "jdbc:mysql://localhost/" + database + "?"
                    + "autoReconnect=true&useSSL=false";
            conn = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
            conn = null;
        }
        return conn;
    }

    @Override
    public boolean initialSetup() {
        if (!doesTableExist()) {
            createTable();
            return true;
        }
        return false;
    }

    @Override
    public List<Quest> getAllQuests() {
        return quests;
    }

    @Override
    public Quest getQuest(Player player, String type) {
        return quests.stream().filter(q -> q.getPlayer().equals(player) &&
                q.getType().equals(type)).findAny().orElseThrow(null);
    }

    private boolean doesEntryExist() {
        PreparedStatement pStat;
        String query = "select * from " + table + " LIMIT 1";
        try {
            pStat = connect().prepareStatement(query);
            pStat.execute();
            ResultSet rs = pStat.getResultSet();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Quest> getQuests(Player player) {
        return quests.stream().filter(q -> q.getPlayer().equals(player)).collect(Collectors.toList());
    }

    @Override
    public boolean loadData(Player player) {
        final int questsSize = quests.size();
        String uuid = player.getUniqueId().toString();
        PreparedStatement pStat;
        String query = "select * from " + table + " where player_uuid=?";
            try {
                pStat = connect().prepareStatement(query);
                pStat.setString(1, uuid);
                pStat.execute();
                ResultSet rs = pStat.getResultSet();
                while (rs.next()) {
                    String type = rs.getString(2);
                    int progress = rs.getInt(3);
                    int completion = rs.getInt(4);
                    quests.add(new Quest(player, type, progress, completion));
                }
                pStat.close();
                if (quests.size()!= questsSize) {
                return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return false;
    }

    @Override
    public boolean saveData(List<Player> players) {
        try {
            for (Player player : players) {
                saveData(player);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean saveData(Player player) {
        PreparedStatement pStat;
        String uuid = player.getUniqueId().toString();
        String query = "insert into " + table + " (player_uuid, quest_type, quest_progress, quest_completion) VALUES (?, ?, ?, ?)";
        if (doesEntryExist()) {
            query = "update " + table + " SET player_uuid=?, quest_type=?, quest_progress=?, quest_completion=? WHERE player_uuid=? AND quest_type=?";
        }
            List<Quest> playerQuestList = quests.stream().filter(q -> q.getPlayer().equals(player)).collect(Collectors.toList());

        for (Quest quest : playerQuestList) {
            try {
                pStat = connect().prepareStatement(query);
                pStat.setString(1, uuid);
                pStat.setString(2, quest.getType());
                pStat.setInt(3, quest.getProgress());
                pStat.setInt(4, quest.getCompletion());
                if (query.contains("update")) {
                    pStat.setString(5, uuid);
                    pStat.setString(6, quest.getType());
                }
                pStat.executeUpdate();
                pStat.close();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
            return true;
    }

    @Override
    public boolean hasQuest(Player player, String type) {
        return quests.stream().anyMatch(q -> q.getPlayer().equals(player) && q.getType().equals(type));
    }

    @Override
    public void removeQuest(Player player, String type) {
        quests.removeIf(q -> q.getPlayer().equals(player) && q.getType().equals(type));
        PreparedStatement pStat;
        String query = "delete from " + table + " where player_uuid=? AND quest_type=?";
        String uuid = player.getUniqueId().toString();
        try {
            pStat = connect().prepareStatement(query);
            pStat.setString(1, uuid);
            pStat.setString(2, type);
            pStat.execute();
            pStat.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
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
