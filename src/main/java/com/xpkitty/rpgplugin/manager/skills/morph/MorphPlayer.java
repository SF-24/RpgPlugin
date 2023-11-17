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
package com.xpkitty.rpgplugin.manager.skills.morph;

import com.xpkitty.rpgplugin.Rpg;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.level.Level;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.UUID;

public class MorphPlayer {

    public static void MorphPlayer(Rpg rpg, Player player) {
        Location loc = player.getLocation();
        UUID uuid = player.getUniqueId();

        spawnWolf(uuid,loc,player);
    }

    public static void spawnWolf(UUID uuid, Location location,Player player) {
        ServerLevel world = ((CraftWorld) location.getWorld()).getHandle();
        ServerPlayer serverPlayer = ((CraftPlayer)player).getHandle();

        ClientboundRemoveEntitiesPacket destroyPacket = new ClientboundRemoveEntitiesPacket(serverPlayer.getId());
        serverPlayer.connection.send(destroyPacket);

        Wolf wolf = new Wolf(EntityType.WOLF, world);

        wolf.absMoveTo(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
/*

        try {
            Field field = wolf.getClass().getDeclaredField("id");
            field.setAccessible(true);
            field.set(wolf, uuid);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ClientboundAddEntityPacket spawnPacket = new ClientboundAddEntityPacket(wolf);

        for (Player element : Bukkit.getOnlinePlayers()) {
            ((CraftPlayer) element).getHandle().connection.send(spawnPacket);
        }*/
    }

}
