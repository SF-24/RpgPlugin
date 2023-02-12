package com.xpkitty.rpgplugin.manager.player_class.class_list;

import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.MiscPlayerManager;
import com.xpkitty.rpgplugin.manager.player_class.BaseClass;
import com.xpkitty.rpgplugin.manager.player_class.ClassList;
import com.xpkitty.rpgplugin.manager.player_class.abilities.AbilityType;
import org.bukkit.entity.Player;

import java.util.UUID;

public class BerserkerClass extends BaseClass {
    public BerserkerClass(Rpg rpg, UUID uuid, ClassList type) {
        super(rpg, uuid, type);
    }

    @Override
    public void onStart(Rpg rpg, Player player, ClassList type) {
        MiscPlayerManager.learnAbility(player,rpg, AbilityType.ANVIL_STRIKE);
        MiscPlayerManager.learnAbility(player,rpg, AbilityType.REND);
    }
}
