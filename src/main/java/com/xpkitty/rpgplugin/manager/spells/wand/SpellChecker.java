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
package com.xpkitty.rpgplugin.manager.spells.wand;

import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.spells.CastSpell;
import com.xpkitty.rpgplugin.manager.spells.enum_list.HpSpellsList;
import com.xpkitty.rpgplugin.manager.spells.enum_list.SpellList;
import com.xpkitty.rpgplugin.manager.spells.spell_crafting.CustomSpellManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.List;

public class SpellChecker {

    public static void CheckHpSpell(Player player, Rpg rpg, List<Integer> wandMovement, Vector dir, int castPower) {
        Vector newDir = player.getLocation().getDirection().normalize();

        boolean isInEnum = false;

        for(HpSpellsList spell : HpSpellsList.values()) {
            if(spell.getWandMovement().equals(wandMovement)) {
                isInEnum=true;

                for(SpellList element : SpellList.values()) {
                    if(element.name().equalsIgnoreCase(spell.name())) {
                        CastSpell.castSpell(player,element,rpg,newDir,true);
                    }
                }

                for(Player player2 : Bukkit.getOnlinePlayers()) {
                    if(player.getWorld().getName().equalsIgnoreCase(player2.getWorld().getName())) {
                        if (player.getLocation().distance(player2.getLocation()) <= spell.getIncantationVolume()) {
                            player2.sendMessage("<" + player.getDisplayName() + "> " + spell.getDisplay());
                        }
                    }
                }
            }
        }

        if(!isInEnum) {
            CustomSpellManager.testForSpellPattern(rpg,player,wandMovement,castPower);
        }
    }

}
