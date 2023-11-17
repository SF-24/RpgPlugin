
/*
 *     Copyright (C) 2023 Sebastian Frynas
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 *     Contact: sebastian.frynas@outlook.com
 *
 */

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
