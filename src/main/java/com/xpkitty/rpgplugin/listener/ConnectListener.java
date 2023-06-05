package com.xpkitty.rpgplugin.listener;

import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.ExperienceManager;
import com.xpkitty.rpgplugin.manager.MiscPlayerManager;
import com.xpkitty.rpgplugin.manager.data.new_player_data.NewDataReader;
import com.xpkitty.rpgplugin.manager.data.player_data.PlayerDataManager;
import com.xpkitty.rpgplugin.manager.data.player_data.PlayerSkinFile;
import com.xpkitty.rpgplugin.manager.data.player_data.PlayerSpellFile;
import com.xpkitty.rpgplugin.manager.data.profile_data.ProfileManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.File;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public class ConnectListener implements Listener {

    YamlConfiguration yamlConfiguration;
    File file;
    Rpg rpg;



    public HashMap<UUID, PlayerSkinFile> playerSkinData = new HashMap<>();
    public HashMap<UUID, PlayerDataManager> playerData = new HashMap<>();
    public HashMap<UUID, ProfileManager> playerProfileData = new HashMap<>();
    public HashMap<UUID, PlayerSpellFile> playerSpellData = new HashMap<>();

    boolean useSQL = false;

    public ConnectListener(Rpg rpg) {
        this.rpg = rpg;

        if(rpg.getDatabaseConfigManager().getDatabaseYamlConfiguration().getBoolean("use_sql")) {
            useSQL=true;
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {


        if(Bukkit.getServer().getOnlinePlayers().size() == 0) {
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "cc reload");
        }

        if(!playerProfileData.containsKey(e.getPlayer().getUniqueId())) {
            playerProfileData.put(e.getPlayer().getUniqueId(), new ProfileManager(e.getPlayer(),rpg));
        }

        if(!playerSkinData.containsKey(e.getPlayer().getUniqueId())) {
            playerSkinData.put(e.getPlayer().getUniqueId(), new PlayerSkinFile(e.getPlayer(),rpg));
        }

        if(!playerData.containsKey(e.getPlayer().getUniqueId())) {
            PlayerDataManager playerDataManager = new PlayerDataManager(e.getPlayer(),rpg,MiscPlayerManager.getCurrentPlayerProfile(rpg, e.getPlayer()));
            playerData.put(e.getPlayer().getUniqueId(), playerDataManager);
        }

        if(!playerSpellData.containsKey(e.getPlayer().getUniqueId())) {
            playerSpellData.put(e.getPlayer().getUniqueId(),new PlayerSpellFile( e.getPlayer(),rpg));
        }

        YamlConfiguration yamlConfiguration = getPlayerDataInstance(e.getPlayer()).getModifyYaml();
        File file = getPlayerDataInstance(e.getPlayer()).getYamlFile();

        // make new data manager
        NewDataReader newDataReader = new NewDataReader(e.getPlayer(),rpg);


        int exp = yamlConfiguration.getInt("experience");
        int level = yamlConfiguration.getInt("level");

        ExperienceManager.setXpBar(rpg, e.getPlayer(), exp, level);

        skinTest(e.getPlayer());

        MiscPlayerManager miscPlayerManager = new MiscPlayerManager();
        miscPlayerManager.testResourcePack(e.getPlayer(),rpg,e.getPlayer().getWorld(),getPlayerDataInstance(e.getPlayer()));

        this.file = file;
        this.yamlConfiguration = yamlConfiguration;

        MiscPlayerManager.loadPlayerLocation(e.getPlayer(),rpg);
        MiscPlayerManager.loadPlayerInventory(e.getPlayer(),rpg);

        try {
            if(useSQL) {
                rpg.getDatabaseManager().updatePlayerData(e.getPlayer());
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        PlayerSkinFile skinData = getPlayerSkinInstance(e.getPlayer());
        if(skinData.verifySkin(skinData.getActiveSkin())) {
            skinData.activateSkin(skinData.getActiveSkin());
        }

        rpg.getSettingsManager().addPlayer(e.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        e.getPlayer().closeInventory();

        rpg.getSpellHotbarManager().deadctivateSpellHotbar(e.getPlayer());

        MiscPlayerManager.saveLocationToFile(e.getPlayer(), rpg);
        MiscPlayerManager.savePlayerInventory(e.getPlayer(), rpg);

        playerSkinData.remove(e.getPlayer().getUniqueId());
        playerData.remove(e.getPlayer().getUniqueId());
        playerProfileData.remove(e.getPlayer().getUniqueId());
        playerSkinData.remove(e.getPlayer().getUniqueId());
        rpg.getSpellHotbarManager().removePlayer(e.getPlayer());

        try {
            if(useSQL) {
                rpg.getDatabaseManager().deletePlayerData(e.getPlayer());
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void reloadProfile(Player player) {

        PlayerSkinFile skinData = getPlayerSkinInstance(player);
        if(skinData.verifySkin("default") && !skinData.getActiveSkin().equalsIgnoreCase("default")) {
            skinData.activateSkin("default");
        }

        MiscPlayerManager.savePlayerInventory(player,rpg);
        MiscPlayerManager.saveLocationToFile(player,rpg);

        PlayerDataManager playerDataManager = new PlayerDataManager(player,rpg,MiscPlayerManager.getCurrentPlayerProfile(rpg, player));
        playerData.put(player.getUniqueId(), playerDataManager);
        playerProfileData.put(player.getUniqueId(), new ProfileManager(player,rpg));
        playerSkinData.put(player.getUniqueId(), new PlayerSkinFile(player,rpg));
        playerSpellData.put(player.getUniqueId(),new PlayerSpellFile(player,rpg));



        YamlConfiguration yamlConfiguration = getPlayerDataInstance(player).getModifyYaml();
        File file = getPlayerDataInstance(player).getYamlFile();

        int exp = yamlConfiguration.getInt("experience");
        int level = yamlConfiguration.getInt("level");

        ExperienceManager.setXpBar(rpg, player, exp, level);

        com.xpkitty.rpgplugin.manager.MiscPlayerManager miscPlayerManager = new MiscPlayerManager();
        miscPlayerManager.testResourcePack(player,rpg,player.getWorld(),getPlayerDataInstance(player));

        this.file = file;
        this.yamlConfiguration = yamlConfiguration;

        MiscPlayerManager.loadPlayerLocation(player,rpg);
        MiscPlayerManager.loadPlayerInventory(player,rpg);

        try {
            if(useSQL) {
                rpg.getDatabaseManager().updatePlayerData(player);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        skinData = getPlayerSkinInstance(player);
        if(skinData.verifySkin(skinData.getActiveSkin())) {
            skinData.activateSkin(skinData.getActiveSkin());
        }
    }

    public HashMap<UUID, PlayerDataManager> getInstances() { return playerData; }

    public PlayerDataManager getPlayerDataInstance(Player player) {
        if(playerData.containsKey(player.getUniqueId())) {
            return playerData.get(player.getUniqueId());
        }
        return null;
    }

    public PlayerSkinFile getPlayerSkinInstance(Player player) {
        if(playerSkinData.containsKey(player.getUniqueId())) {
            return playerSkinData.get(player.getUniqueId());
        }
        return null;
    }

    public ProfileManager getPlayerProfileInstance(Player player) {
        if(playerProfileData.containsKey(player.getUniqueId())) {
            return playerProfileData.get(player.getUniqueId());
        }
        return null;
    }

    public PlayerSpellFile getPlayerSpellFile(Player player) {
        if(playerSpellData.containsKey(player.getUniqueId())) {
            return playerSpellData.get(player.getUniqueId());
        }
        return null;
    }




    private void skinTest(Player player) {
        YamlConfiguration yamlConfiguration = rpg.getConnectListener().getPlayerDataInstance(player).getModifyYaml();
        this.yamlConfiguration = yamlConfiguration;
        File file = rpg.getConnectListener().getPlayerDataInstance(player).getYamlFile();
        this.file = file;

        if(yamlConfiguration.contains("skin.skinData") && !yamlConfiguration.getString("skin.skinData").equalsIgnoreCase("def")) {
            if(yamlConfiguration.contains("skin.skinSignature") && !yamlConfiguration.getString("skin.skinData").equalsIgnoreCase("def")) {
                player.setDisplayName(yamlConfiguration.getString("player-name"));
                player.setPlayerListName(yamlConfiguration.getString("player-name"));
            }
        }
    }

}
