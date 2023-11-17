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
package com.xpkitty.rpgplugin.manager.player_class.abilities;

import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.player_class.abilities.ability_type.*;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class RunAbility {

    public RunAbility(Player player, Rpg rpg, AbilityType ability) {

        switch (ability) {
            case DASH:
                new DashAbility(player,rpg);
                break;
            case LEAP:
                new LeapAbility(player,rpg);
                break;
            case ANVIL_STRIKE:
                new AnvilStrikeAbility(player,rpg);
                break;
            case REND:
                new RendAbility(player,rpg);
                break;
            case SAP:
                new SapAbility(player,rpg);
                break;
            default:
                player.sendMessage(ChatColor.RED + "ERROR! Ability [" + ability.getName() + "] cannot be identified. Please inform a server administrator");
        }



    }
}
