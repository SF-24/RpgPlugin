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
package com.xpkitty.rpgplugin.manager.skills;

import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.MiscPlayerManager;
import com.xpkitty.rpgplugin.manager.data.new_player_data.NewDataReader;
import org.bukkit.entity.Player;

public class PlayerSkillManager {

    public static int getPlayerSkillLevel(Rpg rpg, Player player, PlayerSkills skill) {
        return new NewDataReader(player,rpg).loadData(rpg,player).getSkillLevel(skill);
    }

    public static int getPlayerSkillExp(Rpg rpg, Player player, PlayerSkills skill) {
        return new NewDataReader(player,rpg).loadData(rpg,player).getSkillXp(skill);
    }

    public static int getPlayerSkillMod(Rpg rpg, Player player, PlayerSkills skill) {
        int level = getPlayerSkillLevel(rpg,player,skill);
        int v = MiscPlayerManager.calculateAbilityScoreModifier(level+10);
        return v;
    }
}
