/*
 *     Copyright (C) 2023 Sebastian Frynas
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 *     Contact: sebastian.frynas@outlook.com
 *
 */
package com.xpkitty.rpgplugin;

import com.jeff_media.armorequipevent.ArmorEquipEvent;
import com.xpkitty.rpgplugin.command.*;
import com.xpkitty.rpgplugin.command.hogwarts.HousePointCommand;
import com.xpkitty.rpgplugin.command.tabcompleter.*;
import com.xpkitty.rpgplugin.listener.*;
import com.xpkitty.rpgplugin.manager.MiscPlayerManager;
import com.xpkitty.rpgplugin.manager.PlayerLevelManager;
import com.xpkitty.rpgplugin.manager.data.ConfigManager;
import com.xpkitty.rpgplugin.manager.data.HogwartsDataManager;
import com.xpkitty.rpgplugin.manager.data.database_data.DatabaseConfigManager;
import com.xpkitty.rpgplugin.manager.data.database_data.DatabaseManager;
import com.xpkitty.rpgplugin.manager.data.player_settings.settings_manager.SettingsManager;
import com.xpkitty.rpgplugin.manager.data.spell_data.MainSpellData;
import com.xpkitty.rpgplugin.manager.hogwarts.HogwartsHouseManager;
import com.xpkitty.rpgplugin.manager.hogwarts.HogwartsHouseMathManager;
import com.xpkitty.rpgplugin.manager.hud.EnergyManager;
import com.xpkitty.rpgplugin.manager.hud.HudSender;
import com.xpkitty.rpgplugin.manager.hud.HudType;
import com.xpkitty.rpgplugin.manager.player_class.abilities.ClickManager;
import com.xpkitty.rpgplugin.manager.player_groups.guilds.GuildConfig;
import com.xpkitty.rpgplugin.manager.player_groups.guilds.GuildManager;
import com.xpkitty.rpgplugin.manager.spells.shield.ShieldManager;
import com.xpkitty.rpgplugin.manager.spells.spell_learning.SpellLearnManager;
import com.xpkitty.rpgplugin.manager.spells.spell_ui.SpellHotbarManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.sql.SQLException;
import java.util.List;

public final class Rpg extends JavaPlugin {

    public ShieldManager shieldManager = new ShieldManager();
    private final SettingsManager settingsManager = new SettingsManager(this);
    private ConfigManager configManager = new ConfigManager();
    ConnectListener connectListener;
    ClickListener clickListener;
    PlayerLevelManager playerLevelManager;
    MiscPlayerManager miscPlayerManager = new MiscPlayerManager();
    MainSpellData mainSpellData = new MainSpellData(this);
    ClickManager clickManager = new ClickManager(this);
    GuildManager guildManager = new GuildManager(this);
    GuildConfig guildConfig = new GuildConfig(this);
    HogwartsDataManager hogwartsDataManager = new HogwartsDataManager(this);
    DatabaseConfigManager databaseConfigManager = new DatabaseConfigManager(this);
    DatabaseManager databaseManager;
    SpellHotbarManager spellHotbarManager = new SpellHotbarManager(this);


    @Override
    public void onEnable() {

        if(getDatabaseConfigManager().getDatabaseYamlConfiguration().getBoolean("use_sql")) {
            try {
                databaseManager = new DatabaseManager(this);
                databaseManager.setServerOnline();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        configManager.setupConfig(this);
        getDataFolder().mkdir();

        PlayerLevelManager playerLevelManager = new PlayerLevelManager(this);
        this.playerLevelManager = playerLevelManager;

        ConnectListener connectListener = new ConnectListener(this);
        this.connectListener = connectListener;
        ClickListener clickListener = new ClickListener(this);
        this.clickListener = clickListener;

        guildManager.loadGuilds();

        BukkitTask task = getServer().getScheduler().runTaskTimer(this, () -> {
            for(Player player : Bukkit.getOnlinePlayers()) {
                if(EnergyManager.getEnergyCount(this,player)<getConnectListener().getPlayerSpellFile(player).getBaseEnergy()) {
                    EnergyManager.giveEnergy(this,player,getConnectListener().getPlayerSpellFile(player).getEnergyRegen());
                }
            }
        }, 0,20);




        BukkitTask hudTask = getServer().getScheduler().runTaskTimer(this, () -> {
            for(Player player : Bukkit.getOnlinePlayers()) {
                ItemStack handItem = player.getInventory().getItemInMainHand();
                if(handItem.getItemMeta()!=null) {
                    if(handItem.getItemMeta().getLocalizedName().contains("HP_WAND")) {
                        HudSender.sendHud(this,player, HudType.SPELL_HUD);
                    }
                }
            }
        }, 0,5);



        Bukkit.getPluginManager().registerEvents(connectListener, this);
        Bukkit.getPluginManager().registerEvents(new PingListener(this), this);
        Bukkit.getPluginManager().registerEvents(new UIListener(this), this);
        Bukkit.getPluginManager().registerEvents(clickListener, this);
        Bukkit.getPluginManager().registerEvents(new PlayerListener(this), this);
        Bukkit.getPluginManager().registerEvents(new KillListener(this), this);
        Bukkit.getPluginManager().registerEvents(new DamageListener(this), this);
        Bukkit.getPluginManager().registerEvents(new DataListener(this), this);
        Bukkit.getPluginManager().registerEvents(new BlockBreakListener(this),this);
        Bukkit.getPluginManager().registerEvents(new WandListener(this),this);
        Bukkit.getPluginManager().registerEvents(new ServerListener(this), this);
        Bukkit.getPluginManager().registerEvents(new KeyListener(this), this);
        Bukkit.getPluginManager().registerEvents(new DeathAndRespawnListener(this),this);
        ArmorEquipEvent.registerListener(this);

        getCommand("settings").setExecutor(new SettingsCommand(this));
        getCommand("menu").setExecutor(new MenuCommand(this));
        getCommand("getitem").setExecutor(new GetItemCommand(this));
        getCommand("setskill").setExecutor(new SetSkillCommand(this));
        getCommand("experience").setExecutor(new ExperienceCommand(this));
        getCommand("rpg").setExecutor(new RpgCommand(this));
        getCommand("achievements").setExecutor(new AchievementCommand(this));
        getCommand("party").setExecutor(new PartyCommand(this));
        getCommand("guild").setExecutor(new GuildCommand(this));
        getCommand("profile").setExecutor(new ProfileCommand(this));
        getCommand("spell").setExecutor(new SpellCommand(this));
        getCommand("house_points").setExecutor(new HousePointCommand(this));
        getCommand("player_model").setExecutor(new PlayerModelCommand(this));

        getCommand("settings").setTabCompleter(new SettingsTabCompleter());
        getCommand("getitem").setTabCompleter(new GetItemCommandTab());
        getCommand("setskill").setTabCompleter(new SetSkillCommandTab());
        getCommand("experience").setTabCompleter(new ExperienceCommandTabCompleter());
        getCommand("rpg").setTabCompleter(new RpgCommandTabCompleter(this));
        getCommand("party").setTabCompleter(new PartyCommandTab());
        getCommand("profile").setTabCompleter(new ProfileCommandTab());
        getCommand("guild").setTabCompleter(new GuildCommandTabCompleter());
    }

    @Override
    public void onDisable() {
        for(Player player: Bukkit.getServer().getOnlinePlayers()) {
            player.closeInventory();
            spellHotbarManager.deadctivateSpellHotbar(player);

            MiscPlayerManager.savePlayerInventory(player, this);
            MiscPlayerManager.saveLocationToFile(player, this);

            player.kickPlayer("Server is being reloaded or stopped!");
            if(getDatabaseConfigManager().getDatabaseYamlConfiguration().getBoolean("use_sql")) {
                try {
                    databaseManager.deletePlayerData(player);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        if(databaseConfigManager.getDatabaseYamlConfiguration().getBoolean("use_sql")) {
            try {
                databaseManager.setServerOffline();
                databaseManager.disconnect();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public ConnectListener getConnectListener() { return connectListener; }

    public ConfigManager getConfigManager() { return configManager; }

    public PlayerLevelManager getPlayerLevelManager() { return playerLevelManager; }

    public MiscPlayerManager getMiscPlayerManager() {
        return miscPlayerManager;
    }

    public ClickManager getClickManager() {return clickManager;}

    public HogwartsDataManager getHogwartsDataManager() { return hogwartsDataManager; }

    public GuildConfig getGuildConfig() { return guildConfig; }

    //public GuildDataManager getGuildDataManager() { return guildDataManager; }
    public GuildManager getGuildManager() { return guildManager; }

    public DatabaseManager getDatabaseManager() {return databaseManager;}
    public DatabaseConfigManager getDatabaseConfigManager() {return databaseConfigManager;}

    public MainSpellData getMainSpellData() { return mainSpellData; }

    public SpellHotbarManager getSpellHotbarManager() { return spellHotbarManager; }

    public SettingsManager getSettingsManager() {return settingsManager;}

    public void mapSpellPattern(List<Integer> pattern, Player player, Vector dir) {
        System.out.println("");
        System.out.println("EXECUTING TEST RUN SPELL");
        System.out.println("PATTERN: ");
        System.out.println(pattern);
        System.out.println("");

        SpellLearnManager.loadCastTest(this,player,pattern,dir,false);
    }


    public boolean isInHouse(Player player) {
        return HogwartsHouseManager.getHogwartsHouse(this, player) != null;
    }

    public void giveHousePoints(Player player, int amount, boolean message) {
        if (isInHouse(player)) {
            if (message) {
                if(amount>1) {
                    player.sendMessage(ChatColor.BOLD + HogwartsHouseManager.getHogwartsHouse(this, player).getPrefix() + amount + " points to " + HogwartsHouseManager.getHogwartsHouse(this, player).getDisplay());
                } else {
                    player.sendMessage(ChatColor.BOLD + HogwartsHouseManager.getHogwartsHouse(this, player).getPrefix() + amount + " point to " + HogwartsHouseManager.getHogwartsHouse(this, player).getDisplay());
                }
            }
            HogwartsHouseMathManager.addHousePoints(this, player, null, amount);
        }
    }
}
