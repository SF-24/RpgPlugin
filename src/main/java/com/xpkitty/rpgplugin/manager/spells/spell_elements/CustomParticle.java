// 2023. Author: S.Frynas (XpKitty), e-mail: sebastian.frynas@outlook.com, licence: GNU GPL v3

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
