// 2023. Author: S.Frynas (XpKitty), e-mail: sebastian.frynas@outlook.com, licence: GNU GPL v3

package com.xpkitty.rpgplugin.manager.spells.spells_old;

import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.spells.Spell;
import com.xpkitty.rpgplugin.manager.spells.enum_list.SpellList;
import com.xpkitty.rpgplugin.manager.spells.SpellManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;

import java.util.UUID;

public class FireballSpell extends Spell {
    Rpg rpg;
    UUID uuid;
    SpellList type;

    public FireballSpell(Rpg rpg, SpellList type, UUID uuid) {
        super(rpg, type, uuid);
        this.rpg=rpg;
        this.type=type;
        this.uuid=uuid;
        onStart(Bukkit.getPlayer(uuid));
    }

    @Override
    public void onStart(Player player) {
        Float power = type.getPower();
        new SpellManager().launch(player,1.0f,10, Fireball.class,rpg,6.50f,power);
    }
}
