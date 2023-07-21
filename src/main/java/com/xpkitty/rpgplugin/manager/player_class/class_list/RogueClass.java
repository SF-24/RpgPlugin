// 2023. Author: S.Frynas (XpKitty), e-mail: sebastian.frynas@outlook.com, licence: GNU GPL v3

package com.xpkitty.rpgplugin.manager.player_class.class_list;

import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.MiscPlayerManager;
import com.xpkitty.rpgplugin.manager.player_class.BaseClass;
import com.xpkitty.rpgplugin.manager.player_class.ClassList;
import com.xpkitty.rpgplugin.manager.player_class.abilities.AbilityType;
import org.bukkit.entity.Player;

import java.util.UUID;

public class RogueClass extends BaseClass {
    public RogueClass(Rpg rpg, UUID uuid, ClassList type) {
        super(rpg, uuid, type);
    }

    @Override
    public void onStart(Rpg rpg, Player player, ClassList type) {
        MiscPlayerManager.learnAbility(player,rpg, AbilityType.LEAP);
        MiscPlayerManager.learnAbility(player,rpg, AbilityType.SAP);
    }
}
