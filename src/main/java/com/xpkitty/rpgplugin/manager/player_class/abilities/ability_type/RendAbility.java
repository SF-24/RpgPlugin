package com.xpkitty.rpgplugin.manager.player_class.abilities.ability_type;

import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.player_class.abilities.AttackType;
import org.bukkit.entity.Player;

public class RendAbility {
    public RendAbility(Player player, Rpg rpg) {
        rpg.getMiscPlayerManager().setNextAttack(AttackType.REND,player);
    }
}
