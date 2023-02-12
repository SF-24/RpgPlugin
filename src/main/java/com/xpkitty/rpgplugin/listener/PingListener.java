package com.xpkitty.rpgplugin.listener;

import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.data.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import java.io.File;

public class PingListener implements Listener {

    private Rpg rpg;

    public PingListener(Rpg rpg) {
        this.rpg = rpg;
    }

    @EventHandler
    public void onPing(ServerListPingEvent e) {
        ConfigManager configManager = rpg.getConfigManager();
        String line1 = ChatColor.translateAlternateColorCodes('&', configManager.getConfigLineToString("motd-line1"));
        String line2 = ChatColor.translateAlternateColorCodes('&', configManager.getConfigLineToString("motd-line2"));

        e.setMotd(line1 + "\n" + line2);

        try {
            e.setServerIcon(Bukkit.loadServerIcon(new File("icon.png")));
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }

}
