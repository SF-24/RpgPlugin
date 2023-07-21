// 2023. Author: S.Frynas (XpKitty), e-mail: sebastian.frynas@outlook.com, licence: GNU GPL v3

package com.xpkitty.rpgplugin.manager.data.new_player_data;

import com.xpkitty.rpgplugin.manager.skills.PlayerSkills;
import com.xpkitty.rpgplugin.manager.skills.SkillClass;

import java.util.HashMap;

public class PlayerDataClass {

    HashMap<PlayerSkills, SkillClass> skillsMap = new HashMap<>();

    PlayerDataClass() {
        for(PlayerSkills skill : PlayerSkills.values()) {
            skillsMap.put(skill, new SkillClass(skill));
        }
    }

    public int getSkillLevel(PlayerSkills skill) {
        return skillsMap.get(skill).getLevel();
    }

    public int getSkillXp(PlayerSkills skill) {
        return skillsMap.get(skill).getXp();
    }

    public void setXp(PlayerSkills skill, int amount) {
        SkillClass skillClass = skillsMap.get(skill);
        int newValue = skillClass.getXp()+amount;
        skillClass.setXp(newValue);
        skillsMap.put(skill,skillClass);
    }

    public void setLevel(PlayerSkills skill, int amount) {
        SkillClass skillClass = skillsMap.get(skill);
        int newValue = skillClass.getXp()+amount;
        skillClass.setLevel(newValue);
        skillsMap.put(skill,skillClass);
    }

    // check if hashmap is missing any skills
    public void updateSkills() {
        for(PlayerSkills skill : PlayerSkills.values()) {
            if(!skillsMap.containsKey(skill)) {
                skillsMap.put(skill,new SkillClass(skill));
            }
        }
    }

    public HashMap<PlayerSkills, SkillClass> getSkillMap() {
        return skillsMap;
    }
}
