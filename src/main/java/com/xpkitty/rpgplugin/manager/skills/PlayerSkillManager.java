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
package com.xpkitty.rpgplugin.manager.skills;

import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.MiscPlayerManager;
import com.xpkitty.rpgplugin.manager.data.new_player_data.NewDataReader;
import org.bukkit.entity.Player;

public class PlayerSkillManager {

    public static void givePlayerSkillExp(Player player, PlayerSkills skill, int amount) {
        Rpg rpg = Rpg.getRpg();
        NewDataReader newDataReader = new NewDataReader(player,rpg);
        newDataReader.addXp(player,skill,amount);
    }

    public static int getPlayerSkillLevel(Player player, PlayerSkills skill) {
        Rpg rpg = Rpg.getRpg();
        return new NewDataReader(player,rpg).loadData(rpg,player).getSkillLevel(skill);
    }

    public static int getPlayerSkillExp(Player player, PlayerSkills skill) {
        Rpg rpg = Rpg.getRpg();

        return new NewDataReader(player,rpg).loadData(rpg,player).getSkillXp(skill);
    }

    public static int getPlayerSkillMod(Player player, PlayerSkills skill) {
        int level = getPlayerSkillLevel(player,skill);
        return MiscPlayerManager.calculateAbilityScoreModifier(level+10);
    }

    public static java.util.Set<PlayerSkills> getPlayerSkillList(Player player) {
        Rpg rpg = Rpg.getRpg();
        NewDataReader newDataReader = new NewDataReader(player,rpg);
        return newDataReader.loadData(rpg,player).getSkillMap().keySet();
    }
}
