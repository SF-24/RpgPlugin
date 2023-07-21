// 2023. Author: S.Frynas (XpKitty), e-mail: sebastian.frynas@outlook.com, licence: GNU GPL v3

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
