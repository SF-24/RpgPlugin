/*
 *     Copyright (C) 2024 Sebastian Frynas
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

package com.xpkitty.rpgplugin.manager.betonquest.conditions;

import com.xpkitty.rpgplugin.manager.skills.PlayerSkillManager;
import com.xpkitty.rpgplugin.manager.skills.PlayerSkills;
import org.betonquest.betonquest.Instruction;
import org.betonquest.betonquest.api.Condition;
import org.betonquest.betonquest.api.profiles.Profile;
import org.betonquest.betonquest.exceptions.InstructionParseException;
import org.betonquest.betonquest.exceptions.QuestRuntimeException;

import java.util.Locale;

public class SkillCondition extends Condition {

    PlayerSkills skill;
    int value;

    public SkillCondition(final Instruction instruction) throws InstructionParseException {
        super(instruction, true);
        final String string = instruction.next().toUpperCase(Locale.ROOT);
        try {
            skill = PlayerSkills.valueOf(string);
        } catch (final IllegalArgumentException e) {
            throw new InstructionParseException("No such ability score: " + string, e);
        }
        value = instruction.getInt();
    }

    @Override
    protected Boolean execute(Profile profile) throws QuestRuntimeException {
        return value >= PlayerSkillManager.getPlayerSkillLevel(profile.getOnlineProfile().get().getPlayer(), skill);
    }
}
