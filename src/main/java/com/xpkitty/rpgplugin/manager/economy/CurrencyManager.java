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

import com.xpkitty.rpgplugin.Rpg;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;

public class CurrencyManager {

    public static boolean useVault() {
        return Rpg.getRpg().getVaultCompatibilityManager().useEconomy();
    }

    public static double getPlayerBalance(Player player) {
        if(useVault() && Rpg.getRpg().getEconomy().hasAccount(player)) {
            return Rpg.getRpg().getEconomy().getBalance(player);
        }
        return 0;
    }

    public static void addPlayerCurrency(Player player, Double amount) {
        if(useVault()) {
            Economy economy = Rpg.getRpg().getEconomy();
            if(!economy.hasAccount(player)) {
                economy.createPlayerAccount(player);
            }
            economy.depositPlayer(player,amount);
        }
    }

    public static boolean hasPlayerCurrency(Player player, Double amount) {
        if(useVault()) {
            Economy economy = Rpg.getRpg().getEconomy();
            return getPlayerBalance(player) >= amount;
        }
        return false;
    }

    public static boolean takePlayerCurrency(Player player, Double amount) {
        if(hasPlayerCurrency(player,amount)) {
            Rpg.getRpg().getEconomy().withdrawPlayer(player,amount);
            return true;
        }
        return false;
    }
}
