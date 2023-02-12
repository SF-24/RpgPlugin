package com.xpkitty.rpgplugin.manager.spells.spells_old.LaserSpell;

import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.spells.enum_list.SpellList;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.UUID;

public class LaserSpell {
    Rpg rpg;
    UUID uuid;
    SpellList type;
    float power;

    public LaserSpell(Rpg rpg, SpellList type, UUID uuid,float power) {
        this.rpg = rpg;
        this.uuid = uuid;
        this.type = type;
        onStart(Bukkit.getPlayer(uuid),power);
        this.power=power;
    }

    public void onStart(Player player,float power) {
        //player.sendMessage("power: " + power);
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 5.0f, 2.0f);
        LaserRunnable task = new LaserRunnable(rpg,player,power);
        task.start();
    }
}
