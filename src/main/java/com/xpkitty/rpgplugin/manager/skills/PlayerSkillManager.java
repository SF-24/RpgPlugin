package com.xpkitty.rpgplugin.manager.skills;

import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.MiscPlayerManager;
import org.bukkit.entity.Player;

public class PlayerSkillManager {

    public static int getPlayerSkillLevel(Rpg rpg, Player player, PlayerSkills skill) {
        return 0;
    }

    public static int getPlayerSkillExp(Rpg rpg, Player player, PlayerSkills skill) {
        return 0;
    }

    public static int getPlayerSkillMod(Rpg rpg, Player player, PlayerSkills skill) {
        int level = getPlayerSkillLevel(rpg,player,skill);

        int v = MiscPlayerManager.calculateAbilityScoreModifier(level)+5;


        return v;
    }


}
