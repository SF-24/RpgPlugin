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

import org.bukkit.Bukkit;

public class VaultCompatibility {
    public static void vaultSetup() {
        if (Bukkit.getServer().getPluginManager().isPluginEnabled("Vault")) {
            Bukkit.getLogger().info("[(EliteMobs] Vault detected.");
            if (true /* USE VAULT */) {
                Bukkit.getLogger().warning("[RpgPlugin] Vault is enabled ");
                // TODO: ADD VAULT COMPATIBILITY
            }

        }
    }
}
