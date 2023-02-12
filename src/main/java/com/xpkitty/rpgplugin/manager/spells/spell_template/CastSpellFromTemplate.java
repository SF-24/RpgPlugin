package com.xpkitty.rpgplugin.manager.spells.spell_template;

import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.spells.spell_elements.CustomParticle;
import com.xpkitty.rpgplugin.manager.spells.spell_elements.SpellFunction;
import com.xpkitty.rpgplugin.manager.spells.spell_elements.SpellTarget;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

public class CastSpellFromTemplate {

    public CastSpellFromTemplate(Rpg rpg, Player player, int spellStrength, SpellFunction function, float speed, int distance, SpellTarget targets, String val1, String val2, String val3, String particleType, Color particleColor, int particleSize, int particleCount) {
        CustomParticle particle = new CustomParticle(Particle.REDSTONE, particleColor, particleSize, particleCount);


        for(Particle type : Particle.values()) {
            if(type.name().equalsIgnoreCase(particleType)) {
                particle=new CustomParticle(type,particleColor,particleSize,particleCount);
            }
        }

        GenericSpellRunnable genericSpellRunnable = new GenericSpellRunnable(rpg,player,function,speed,distance,targets,val1,val2,val3,particle,spellStrength);
        genericSpellRunnable.start();
    }

}
