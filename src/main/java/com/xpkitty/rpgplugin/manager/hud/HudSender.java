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
package com.xpkitty.rpgplugin.manager.hud;

import com.xpkitty.rpgplugin.Rpg;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public class HudSender {

    public static void sendHud(Rpg rpg, Player player, HudType hudType) {
        int energy = EnergyManager.getEnergyCount(rpg, player);
        String energyText = EnergyManager.getEnergyHudText(player, energy);


        TextComponent hudText = new TextComponent(energyText);

        if (energyText != null) {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, hudText);
        }
    }

}
