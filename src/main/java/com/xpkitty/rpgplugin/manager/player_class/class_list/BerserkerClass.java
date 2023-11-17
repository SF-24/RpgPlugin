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
package com.xpkitty.rpgplugin.manager.player_class.class_list;

import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.MiscPlayerManager;
import com.xpkitty.rpgplugin.manager.player_class.BaseClass;
import com.xpkitty.rpgplugin.manager.player_class.ClassList;
import com.xpkitty.rpgplugin.manager.player_class.abilities.AbilityType;
import org.bukkit.entity.Player;

import java.util.UUID;

public class BerserkerClass extends BaseClass {
    public BerserkerClass(Rpg rpg, UUID uuid, ClassList type) {
        super(rpg, uuid, type);
    }

    @Override
    public void onStart(Rpg rpg, Player player, ClassList type) {
        MiscPlayerManager.learnAbility(player,rpg, AbilityType.ANVIL_STRIKE);
        MiscPlayerManager.learnAbility(player,rpg, AbilityType.REND);
    }
}
