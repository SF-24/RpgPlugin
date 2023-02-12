package com.xpkitty.rpgplugin.manager.player_class;

import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.player_class.class_list.*;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ClassManager {

    public static void setClass(Rpg rpg, Player player, ClassList classList) {

        UUID uuid = player.getUniqueId();

        switch (classList) {
            case WARRIOR:
                new WarriorClass(rpg,uuid,classList);
                break;
            case RANGER:
                new RangerClass(rpg,uuid,classList);
                break;
            case WIZARD:
                new WizardClass(rpg,uuid,classList);
                break;
            case ROGUE:
                new RogueClass(rpg,uuid,classList);
                break;
            case CLERIC:
                new ClericClass(rpg,uuid,classList);
                break;
            case BERSERKER:
                new BerserkerClass(rpg,uuid,classList);
                break;
            default:
                break;
        }

    }

}
