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
package com.xpkitty.rpgplugin.manager.player_class;

import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.player_class.class_list.*;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ClassManager {

    public static void setClass(Rpg rpg, Player player, ClassList classList) {

        UUID uuid = player.getUniqueId();

        switch (classList) {
            case WARRIOR:
                new WarriorClass(rpg,uuid,classList);
                break;
            case RANGER:
                new RangerClass(rpg,uuid,classList);
                break;
            case WIZARD:
                new WizardClass(rpg,uuid,classList);
                break;
            case ROGUE:
                new RogueClass(rpg,uuid,classList);
                break;
            case CLERIC:
                new ClericClass(rpg,uuid,classList);
                break;
            case BERSERKER:
                new BerserkerClass(rpg,uuid,classList);
                break;
            default:
                break;
        }

    }

}
