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
package com.xpkitty.rpgplugin.manager.spells.spell_template;

import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.spells.spell_crafting.SpellType;
import com.xpkitty.rpgplugin.manager.spells.spell_elements.CustomParticle;
import com.xpkitty.rpgplugin.manager.spells.spell_elements.SpellFunction;
import com.xpkitty.rpgplugin.manager.spells.spell_elements.SpellTarget;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

public class CastSpellFromTemplate {

    public CastSpellFromTemplate(Rpg rpg, Player player, int spellStrength, SpellFunction function, float speed, int distance, SpellTarget targets, String val1, String val2, String val3, String particleType, Color particleColor, int particleSize, int particleCount, SpellType spellType) {
        CustomParticle particle = new CustomParticle(Particle.REDSTONE, particleColor, particleSize, particleCount);


        for(Particle type : Particle.values()) {
            if(type.name().equalsIgnoreCase(particleType)) {
                particle=new CustomParticle(type,particleColor,particleSize,particleCount);
            }
        }

        spellStrength/=2;

        if(spellType.equals(SpellType.BEAM)) {
            GenericSpellRunnable genericSpellRunnable = new GenericSpellRunnable(rpg, player, function, speed, distance, targets, val1, val2, val3, particle, spellStrength);
            genericSpellRunnable.start();
        } else {
            GenericShieldRunnable genericShieldRunnable = new GenericShieldRunnable(rpg, player, function, speed, distance, targets, val1, val2, val3, particle, spellStrength);
        }
    }

}
