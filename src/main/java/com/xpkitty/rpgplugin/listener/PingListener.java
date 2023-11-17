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
