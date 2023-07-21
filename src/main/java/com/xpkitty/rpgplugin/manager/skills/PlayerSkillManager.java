// 2023. Author: S.Frynas (XpKitty), e-mail: sebastian.frynas@outlook.com, licence: GNU GPL v3

package com.xpkitty.rpgplugin.manager.skills;

import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.MiscPlayerManager;
import com.xpkitty.rpgplugin.manager.data.new_player_data.NewDataReader;
import org.bukkit.entity.Player;

public class PlayerSkillManager {

    public static int getPlayerSkillLevel(Rpg rpg, Player player, PlayerSkills skill) {
        return new NewDataReader(player,rpg).loadData(rpg,player).getSkillLevel(skill);
    }

    public static int getPlayerSkillExp(Rpg rpg, Player player, PlayerSkills skill) {
        return new NewDataReader(player,rpg).loadData(rpg,player).getSkillXp(skill);
    }

    public static int getPlayerSkillMod(Rpg rpg, Player player, PlayerSkills skill) {
        int level = getPlayerSkillLevel(rpg,player,skill);
        int v = MiscPlayerManager.calculateAbilityScoreModifier(level+10);
        return v;
    }
}
