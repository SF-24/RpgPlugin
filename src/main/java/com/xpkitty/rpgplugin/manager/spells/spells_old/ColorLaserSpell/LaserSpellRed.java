package com.xpkitty.rpgplugin.manager.spells.spells_old.ColorLaserSpell;

import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.spells.enum_list.SpellList;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.UUID;

public class LaserSpellRed {
    Rpg rpg;
    UUID uuid;
    SpellList type;
    float power;

    public LaserSpellRed(Rpg rpg, SpellList type, UUID uuid, float power) {
        this.rpg = rpg;
        this.uuid = uuid;
        this.type = type;
        this.power = power;
        onStart(Bukkit.getPlayer(uuid),power);
    }

    public void onStart(Player player,float power) {
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 5.0f, 2.0f);
        LaserRunnableRed task = new LaserRunnableRed(rpg,player,power);
        task.start();
    }
}
