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
