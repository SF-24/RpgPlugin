package com.xpkitty.rpgplugin.manager.player_class.abilities.ability_type;

import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.player_class.abilities.AttackType;
import org.bukkit.entity.Player;

public class AnvilStrikeAbility {

    public AnvilStrikeAbility(Player player, Rpg rpg) {
        rpg.getMiscPlayerManager().setNextAttack(AttackType.ANVIL_STRIKE,player);
    }
}
