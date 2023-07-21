// 2023. Author: S.Frynas (XpKitty), e-mail: sebastian.frynas@outlook.com, licence: GNU GPL v3

package com.xpkitty.rpgplugin.manager.spells.spells_old.CustomLaserSpell;

import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.spells.enum_list.SpellList;
import com.xpkitty.rpgplugin.manager.spells.spell_elements.CustomParticle;
import com.xpkitty.rpgplugin.manager.spells.spell_elements.SpellFunction;
import com.xpkitty.rpgplugin.manager.spells.spell_elements.SpellTarget;
import com.xpkitty.rpgplugin.manager.spells.spell_template.GenericSpellRunnable;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

import java.util.UUID;

public class LaserSpellCustom {
    Rpg rpg;
    UUID uuid;
    SpellList type;
    float power;
    int particleSize;

    public LaserSpellCustom(Rpg rpg, SpellList type, UUID uuid, float power, Color color, int particleSize, float volume, float pitch, String sound) {
        this.rpg = rpg;
        this.uuid = uuid;
        this.type = type;
        this.particleSize = particleSize;
        this.power = power;

        if(sound == null) {
            sound="minecraft:blaster.blasterpistol";
        }
        if(volume==0) {
            volume=5.0f;
        }
        onStart(Bukkit.getPlayer(uuid),power,color,particleSize,volume,pitch,sound);
    }

    public void onStart(Player player,float power, Color color, int particleSize, float volume, float pitch,String sound) {
        player.getWorld().playSound(player.getLocation(), sound, SoundCategory.PLAYERS, volume, pitch);

        //LaserRunnableCustom task = new LaserRunnableCustom(rpg,player,power,color,particleSize);
        //task.start();
        CustomParticle particle = new CustomParticle(Particle.REDSTONE,color,1,0);

        GenericSpellRunnable task = new GenericSpellRunnable(rpg,player, SpellFunction.ENTITY_DAMAGE,2.0f,50, SpellTarget.ENTITY,String.valueOf(power),"","",particle,0);
        task.start();
    }
}
