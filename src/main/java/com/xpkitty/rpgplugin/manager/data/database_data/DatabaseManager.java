// 2023. Author: S.Frynas (XpKitty), e-mail: sebastian.frynas@outlook.com, licence: GNU GPL v3

package com.xpkitty.rpgplugin.manager.data.database_data;

import com.xpkitty.rpgplugin.Rpg;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.Arrays;
import java.util.Base64;

public class DatabaseManager {

    Rpg rpg;

    protected String HOST;
    protected int PORT;
    protected String DATABASE;
    protected String USERNAME;
    protected String PASSWORD;

    private Connection connection;



    public DatabaseManager(Rpg rpg) {

        YamlConfiguration yamlConfiguration = rpg.getDatabaseConfigManager().yamlConfiguration;
        HOST = yamlConfiguration.getString("host");
        PORT = yamlConfiguration.getInt("port");
        DATABASE = yamlConfiguration.getString("database");
        USERNAME = yamlConfiguration.getString("username");

        String usr = yamlConfiguration.getString("username");
        byte[] decodedUsername = Base64.getDecoder().decode(usr);
        USERNAME = new String(decodedUsername);

        String pass = yamlConfiguration.getString("password");
        byte[] decodedPassword = Base64.getDecoder().decode(pass);
        PASSWORD = new String(decodedPassword);

        this.rpg = rpg;
        if(rpg.getDatabaseConfigManager().getDatabaseYamlConfiguration().getBoolean("use_sql")) {
            try {
                connect();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }



    public void connect() throws SQLException {
        connection = DriverManager.getConnection(
                "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE + "?useSSL=false",
                USERNAME,
                PASSWORD);

        System.out.println("");
        System.out.println("CONNECTING TO SQL DATABASE");
        System.out.println("DATABASE CONNECTION STATUS: " + isConnected());
        System.out.println("");
    }



    public void disconnect() {
        if(isConnected()) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            System.out.println("");
            System.out.println("DISCONNECTING FROM SQL DATABASE");
            System.out.println("");
        }
    }



    public void updatePlayerData(Player player) throws SQLException {
        int level = rpg.getConnectListener().getPlayerDataInstance(player).getModifyYaml().getInt("level");
        String playerClass = rpg.getConnectListener().getPlayerDataInstance(player).getModifyYaml().getString("player_class");

        boolean hasPlayerId = false;

        PreparedStatement getStatement = connection.prepareStatement("SELECT * FROM players WHERE uuid = ?");
        getStatement.setString(1, String.valueOf(player.getUniqueId()));
        ResultSet results = getStatement.executeQuery();

        while(results.next()) {
            System.out.println("Uodate SQL player data for player of UUID: " + results.getString("UUID") + " and username " + results.getString("ign"));
            hasPlayerId = true;
        }


        if(hasPlayerId) {
            PreparedStatement ps = connection.prepareStatement("UPDATE players SET level = ? WHERE uuid = ?;");
            ps.setInt(1, level);
            ps.setString(2, String.valueOf(player.getUniqueId()));
            ps.executeUpdate();

            PreparedStatement ps2 = connection.prepareStatement("UPDATE players SET class = ? WHERE uuid = ?;");
            ps2.setString(1, playerClass);
            ps2.setString(2, String.valueOf(player.getUniqueId()));
            ps2.executeUpdate();
        } else {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO players (uuid,ign,level,class) VALUES (?,?,?,?);");
            ps.setString(1, String.valueOf(player.getUniqueId()));
            ps.setString(2,player.getDisplayName());
            ps.setInt(3, level);
            ps.setString(4,playerClass);
            ps.executeUpdate();
        }
    }



    public void deletePlayerData(Player player) throws SQLException {

        System.out.println("");
        System.out.println("");
        System.out.println("DELETING PLAYER DATA FOR PLAYER: " + player.getName());
        System.out.println("");
        System.out.println("");

        PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM players WHERE uuid = ?;");
        deleteStatement.setString(1, String.valueOf(player.getUniqueId()));
        deleteStatement.executeUpdate();

    }



    public void setServerOnline() throws SQLException {
            PreparedStatement ps = connection.prepareStatement("UPDATE server_status SET value = ? WHERE name = ?;");
        ps.setString(1, "true");
        ps.setString(2, "online");
        ps.executeUpdate();

        System.out.println("SETTING SERVER STATUS ONLINE");
    }

    public void setServerOffline() throws SQLException {

        PreparedStatement ps = connection.prepareStatement("UPDATE server_status SET value = ? WHERE name = ?;");
        ps.setString(1, "false");
        ps.setString(2, "online");
        ps.executeUpdate();

        System.out.println("SETTING SERVER STATUS OFFLINE");
    }

    public void clearPlayers() throws SQLException {
        System.out.println("CLEARING ONLINE PLAYER DATA");
        PreparedStatement ds = connection.prepareStatement("DELETE FROM players;");
        ds.executeUpdate();
    }



    public Connection getConnection() { return connection; }

    public boolean isConnected() { return connection != null; }

}
