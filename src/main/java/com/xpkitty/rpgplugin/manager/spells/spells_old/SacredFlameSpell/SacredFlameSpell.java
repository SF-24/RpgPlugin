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
package com.xpkitty.rpgplugin.manager.spells.spells_old.SacredFlameSpell;

import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.spells.Spell;
import com.xpkitty.rpgplugin.manager.spells.enum_list.SpellList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class SacredFlameSpell extends Spell {
    Rpg rpg;
    UUID uuid;
    SpellList type;

    public SacredFlameSpell(Rpg rpg, SpellList type, UUID uuid) {
        super(rpg, type, uuid);
        this.rpg = rpg;
        this.uuid = uuid;
        this.type = type;
        onStart(Bukkit.getPlayer(uuid));
    }

    @Override
    public void onStart(Player player) {
        SacredFlameRunnable task = new SacredFlameRunnable(rpg,player);
        task.start();
    }
}
