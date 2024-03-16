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
import org.betonquest.betonquest.BetonQuest;
import org.betonquest.betonquest.api.logger.BetonQuestLoggerFactory;

public class BetonQuestManager {

    Rpg rpg;
    BetonQuestLoggerFactory loggerFactory;

    public BetonQuestManager(Rpg rpg) {
        this.rpg=rpg;
    }

    public void Initialise() {
        loggerFactory = rpg.getServer().getServicesManager().load(BetonQuestLoggerFactory.class);
    }

    public BetonQuestLoggerFactory getLoggerFactory() {
        return loggerFactory;
    }
}
