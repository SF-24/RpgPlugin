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
package com.xpkitty.rpgplugin.manager.player_class;

import com.xpkitty.rpgplugin.Rpg;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.Locale;
import java.util.UUID;

public abstract class BaseClass {

    protected ClassList type;
    protected UUID uuid;

    public BaseClass(Rpg rpg, UUID uuid, ClassList type) {
        this.type = type;
        this.uuid = uuid;

        Player player = Bukkit.getPlayer(uuid);

        assert player != null;
        rpg.getConnectListener().getPlayerDataInstance(player).getModifyYaml().set("player_class",type.name().toLowerCase(Locale.ROOT));

        try {
            rpg.getConnectListener().getPlayerDataInstance(player).getModifyYaml().save(rpg.getConnectListener().getPlayerDataInstance(player).getYamlFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        onStart(rpg,player,type);
    }

    public UUID getUuid() { return uuid;}
    public ClassList getType() { return type; }

    public abstract void onStart(Rpg rpg, Player player, ClassList type);

}
