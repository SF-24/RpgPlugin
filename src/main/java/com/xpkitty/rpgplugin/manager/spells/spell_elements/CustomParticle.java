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
package com.xpkitty.rpgplugin.manager.spells.spell_elements;

import net.minecraft.core.particles.ParticleType;
import org.bukkit.Color;
import org.bukkit.Particle;

public class CustomParticle {

    Particle type;
    Color color;
    int size, count;

    public CustomParticle(Particle particleType, Color color, int size, int count) {
        this.type=particleType;
        this.color=color;
        this.size=size;
        this.count=count;
    }

    public Color getColor() { return color; }
    public Particle getType() { return type; }
    public int getSize() { return size; }
    public int getCount() { return count; }
}
