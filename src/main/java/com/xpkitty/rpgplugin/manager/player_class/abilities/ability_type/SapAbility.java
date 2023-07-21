// 2023. Author: S.Frynas (XpKitty), e-mail: sebastian.frynas@outlook.com, licence: GNU GPL v3

package com.xpkitty.rpgplugin.manager.player_class.abilities.ability_type;

import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.player_class.abilities.AttackType;
import org.bukkit.entity.Player;

public class SapAbility {

    public SapAbility(Player player, Rpg rpg) {
        rpg.getMiscPlayerManager().setNextAttack(AttackType.SAP,player);
    }
}
