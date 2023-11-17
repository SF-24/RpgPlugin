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
package com.xpkitty.rpgplugin.manager.spells;

import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.spells.spells_old.FireballSpell;
import com.xpkitty.rpgplugin.manager.spells.spells_old.HealSpell.HealSpell;
import com.xpkitty.rpgplugin.manager.spells.spells_old.SacredFlameSpell.SacredFlameSpell;
import com.xpkitty.rpgplugin.manager.spells.enum_list.SpellList;
import com.xpkitty.rpgplugin.manager.spells.hardcoded_hp_spells.AlohomoraSpell.AlohomoraSpell;
import com.xpkitty.rpgplugin.manager.spells.hardcoded_hp_spells.Colloportus.ColloportusSpell;
import com.xpkitty.rpgplugin.manager.spells.hardcoded_hp_spells.FlipendoSpell.FlipendoSpell;
import com.xpkitty.rpgplugin.manager.spells.hardcoded_hp_spells.IncendioSpell.IncendioSpell;
import com.xpkitty.rpgplugin.manager.spells.hardcoded_hp_spells.LumosSpell;
import com.xpkitty.rpgplugin.manager.spells.hardcoded_hp_spells.NoxSpell;
import com.xpkitty.rpgplugin.manager.spells.hardcoded_hp_spells.StupefySpell.StupefySpell;
import com.xpkitty.rpgplugin.manager.spells.hardcoded_hp_spells.WingardiumLeviosaSpell.WingariumLeviosaSpell;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class CastSpell {

    public static void castSpell(Player player, SpellList spell, Rpg rpg, Vector dir, boolean useVector) {
        switch (spell) {
            case INCENDIO:
                new IncendioSpell(rpg,spell,player.getUniqueId(),dir);
                break;
            case FLIPENDO:
                new FlipendoSpell(rpg,spell,player.getUniqueId(),dir);
                break;
            case ALOHOMORA:
                new AlohomoraSpell(rpg,spell,player.getUniqueId(),dir);
                break;
            case COLLOPORTUS:
                new ColloportusSpell(rpg,spell,player.getUniqueId(),dir);
                break;
            case LUMOS:
                new LumosSpell(rpg,spell,player.getUniqueId(),dir);
                break;
            case NOX:
                new NoxSpell(rpg,spell,player.getUniqueId(),dir);
                break;
            case STUPEFY:
                new StupefySpell(rpg,spell,player.getUniqueId(),dir);
                break;
            case WINGARDIUM_LEVIOSA:
                new WingariumLeviosaSpell(rpg,spell,player.getUniqueId(),dir);
                break;
            case FIREBALL:
                new FireballSpell(rpg,spell,player.getUniqueId());
                break;
            case SACRED_FLAME:
                new SacredFlameSpell(rpg,spell,player.getUniqueId());
                break;
            case HEAL:
                new HealSpell(rpg,spell,player.getUniqueId());
                break;
            default:
                player.sendMessage("NULL POINTER EXCEPTION ERROR! PLEASE CONTACT A SERVER ADMINISTRATOR.");
                return;
        }
    }
}
