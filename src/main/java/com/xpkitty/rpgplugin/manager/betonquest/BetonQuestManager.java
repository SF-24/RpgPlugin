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

package com.xpkitty.rpgplugin.manager.betonquest;

import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.betonquest.conditions.AbilityScoreCondition;
import com.xpkitty.rpgplugin.manager.betonquest.conditions.SkillCondition;
import com.xpkitty.rpgplugin.manager.betonquest.events.GiveXpEvent;
import com.xpkitty.rpgplugin.manager.betonquest.events.GiveXpEventFactory;
import com.xpkitty.rpgplugin.manager.betonquest.events.GiveXpStaticEventFactory;
import org.betonquest.betonquest.BetonQuest;
import org.betonquest.betonquest.api.logger.BetonQuestLoggerFactory;
import org.betonquest.betonquest.api.quest.event.EventFactory;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class BetonQuestManager {

    private boolean useBetonQuest = false;
    BetonQuestLoggerFactory loggerFactory;

    public BetonQuestManager() {
        Initialise();
    }

    public void Initialise() {
        if (Bukkit.getServer().getPluginManager().isPluginEnabled("BetonQuest")) {
            // Load BetonQuest and inject methods
            loggerFactory = Bukkit.getServicesManager().load(BetonQuestLoggerFactory.class);
            useBetonQuest = true;
            InjectMethods();
        }
    }

    // Register and inject custom methods
    private void InjectMethods() {
        BetonQuest.getInstance().registerConditions("rpgabilityscore", AbilityScoreCondition.class);
        BetonQuest.getInstance().registerConditions("rpgskill", SkillCondition.class);

        Plugin plugin = Bukkit.getPluginManager().getPlugin("RpgPlugin");
        BetonQuest.getInstance().registerEvent("rpgxp", new GiveXpEventFactory(loggerFactory, Bukkit.getServer(), Bukkit.getScheduler(), plugin), new GiveXpStaticEventFactory(loggerFactory, Bukkit.getServer(), Bukkit.getScheduler(), plugin));
    }

    public boolean useBetonQuest() {return useBetonQuest;}
}
