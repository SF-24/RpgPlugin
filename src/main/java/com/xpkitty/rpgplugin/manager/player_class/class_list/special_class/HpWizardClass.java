package com.xpkitty.rpgplugin.manager.player_class.class_list.special_class;

import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.MiscPlayerManager;
import com.xpkitty.rpgplugin.manager.player_class.BaseClass;
import com.xpkitty.rpgplugin.manager.player_class.ClassList;
import com.xpkitty.rpgplugin.manager.spells.enum_list.SpellList;
import org.bukkit.entity.Player;

import java.util.UUID;

public class HpWizardClass extends BaseClass {
    public HpWizardClass(Rpg rpg, UUID uuid, ClassList type) {
        super(rpg, uuid, type);
    }

    @Override
    public void onStart(Rpg rpg, Player player, ClassList type) {
        MiscPlayerManager.learnSpell(player,rpg, SpellList.SACRED_FLAME,false);
        MiscPlayerManager.learnSpell(player,rpg, SpellList.HEAL,false);
    }
}
