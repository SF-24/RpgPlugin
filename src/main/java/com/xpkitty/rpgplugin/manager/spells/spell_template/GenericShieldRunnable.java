package com.xpkitty.rpgplugin.manager.spells.spell_template;

import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.spells.spell_elements.CustomParticle;
import com.xpkitty.rpgplugin.manager.spells.spell_elements.SpellFunction;
import com.xpkitty.rpgplugin.manager.spells.spell_elements.SpellTarget;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class GenericShieldRunnable {

    Location loc;
    Vector dir;
    Player player;
    Rpg rpg;
    float speed;
    double dist = 0;
    double t = 0;
    int flyDistance;
    SpellFunction function;
    SpellTarget target;

    int spellStrength;

    String val1;
    String val2;
    String val3;

    CustomParticle particle;

    public GenericShieldRunnable(Rpg rpg, Player player, SpellFunction function, float speed, int flyDistance, SpellTarget targets, String val1, String val2, String val3, CustomParticle particle, int spellStrength) {
        this.player = player;
        this.rpg = rpg;
        this.dir = player.getLocation().getDirection().normalize();
        this.loc = player.getLocation();
        this.speed=speed;
        this.target=targets;
        this.flyDistance=flyDistance;
        this.function=function;
        this.val1=val1;
        this.val2=val2;
        this.val3=val3;
        this.spellStrength=spellStrength;

        this.particle=particle;

        if(function.equals(SpellFunction.ENTITY_KNOCKBACK)) {
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_DRAGON_FIREBALL_EXPLODE,2.0f,2.0f);
        }
        run();
    }

    public void run() {
        rpg.shieldManager.addShield(player,1,1,null,null);
    }
}
