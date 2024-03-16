/*
 *     Copyright (C) 2024 Sebastian Frynas
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

package com.xpkitty.rpgplugin.manager.economy;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.logging.Level;

public class VaultCompatibilityManager {
    private Economy economy = null;
    private boolean enableEconomy = false;

    // TODO: ADD USE VAULT CONFIG SETTING
    protected boolean useVault = true;

    public VaultCompatibilityManager() {
        if (Bukkit.getServer().getPluginManager().isPluginEnabled("Vault")) {
            Bukkit.getLogger().info("[(EliteMobs] Vault detected.");
            if (useVault) {
                Bukkit.getLogger().warning("[RpgPlugin] Vault is enabled ");
                enableEconomy = setupEconomy();
                if(enableEconomy) {
                    Bukkit.getLogger().log(Level.INFO, Color.GREEN + "[RpgPlugin] Vault Economy has been loaded successfully");
                }
            }

        }
    }

    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return true;
    }

    public Economy getEconomy() {return  economy;}
    public boolean useEconomy() {return enableEconomy;}
}
