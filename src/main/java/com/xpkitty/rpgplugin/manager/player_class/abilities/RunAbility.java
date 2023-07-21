// 2023. Author: S.Frynas (XpKitty), e-mail: sebastian.frynas@outlook.com, licence: GNU GPL v3

package com.xpkitty.rpgplugin.manager.player_class.abilities;

import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.player_class.abilities.ability_type.*;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class RunAbility {

    public RunAbility(Player player, Rpg rpg, AbilityType ability) {

        switch (ability) {
            case DASH:
                new DashAbility(player,rpg);
                break;
            case LEAP:
                new LeapAbility(player,rpg);
                break;
            case ANVIL_STRIKE:
                new AnvilStrikeAbility(player,rpg);
                break;
            case REND:
                new RendAbility(player,rpg);
                break;
            case SAP:
                new SapAbility(player,rpg);
                break;
            default:
                player.sendMessage(ChatColor.RED + "ERROR! Ability [" + ability.getName() + "] cannot be identified. Please inform a server administrator");
        }



    }
}
