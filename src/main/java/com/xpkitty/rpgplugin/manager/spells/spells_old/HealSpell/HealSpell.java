// 2023. Author: S.Frynas (XpKitty), e-mail: sebastian.frynas@outlook.com, licence: GNU GPL v3

package com.xpkitty.rpgplugin.manager.spells.spells_old.HealSpell;

import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.spells.Spell;
import com.xpkitty.rpgplugin.manager.spells.enum_list.SpellList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class HealSpell extends Spell {
    Rpg rpg;
    UUID uuid;
    SpellList type;

    public HealSpell(Rpg rpg, SpellList type, UUID uuid) {
        super(rpg, type, uuid);
        this.rpg = rpg;
        this.uuid = uuid;
        this.type = type;
        onStart(Bukkit.getPlayer(uuid));
    }

    @Override
    public void onStart(Player player) {
        HealRunnable task = new HealRunnable(rpg,player);
        task.start();
    }
}
