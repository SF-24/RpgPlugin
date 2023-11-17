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
package com.xpkitty.rpgplugin.manager.spells;

import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.spells.CastSpell;
import com.xpkitty.rpgplugin.manager.spells.enum_list.SpellList;
import org.bukkit.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.util.Vector;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class SpellManager {



    public void launch(Player player, double distOverHead, double distToFacing, Class<? extends Projectile> projectileClass, Rpg rpg, float deleteTime, Float power) {
        final Location start = player.getLocation().add(0, distOverHead, 0);
        // Optionally, set y to zero at player.getLocation().getDirection().setY(0)
        final Vector facing = player.getEyeLocation().add(player.getLocation().getDirection().multiply(distToFacing)).toVector();
        // multiply by a constant, if you want a higher velocity
        final Vector initialV = facing.subtract(start.toVector()).normalize();
        final Projectile projectile = player.getWorld().spawn(start, projectileClass);
        projectile.setVelocity(initialV);
        projectile.setShooter(player);

        if(projectile instanceof Fireball) {
            Fireball fireball = (Fireball) projectile;
            fireball.setYield(power);
        }

        Bukkit.getScheduler().runTaskLater(rpg, (Runnable) () -> {
            if(!projectile.isDead()) {
                projectile.remove();
                player.playSound(projectile.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, SoundCategory.MASTER,1.0f,0.8f);
                player.getWorld().spawnParticle(Particle.SMOKE_LARGE,projectile.getLocation(),1);
            }
        }, (long) (deleteTime*20));
    }

    public static void castSpell(Player player, SpellList spell, Rpg rpg) {
        CastSpell.castSpell(player,spell,rpg, new Vector(), false);
    }

    public SpellList getActiveSpell(Player player, Rpg rpg) {
        YamlConfiguration modifyYamlFile = rpg.getConnectListener().getPlayerDataInstance(player).getModifyYaml();

        if(modifyYamlFile.getString("active_spell") != null) {
            String spellName = modifyYamlFile.getString("active_spell");

            for(SpellList spell : SpellList.values()) {
                if(spell.name().toUpperCase(Locale.ROOT).equalsIgnoreCase(spellName.toUpperCase(Locale.ROOT))) {
                    return spell;
                }
            }
        } else {
            if(rpg.getConnectListener().getPlayerDataInstance(player).getModifyYaml().contains("spell_list")) {
                List<String> spells = rpg.getConnectListener().getPlayerDataInstance(player).getModifyYaml().getStringList("spell_list");
                if(spells.size()>0) {
                    for(SpellList spell : SpellList.values()) {
                        if(spells.contains(spell.name().toUpperCase(Locale.ROOT))) {
                            return spell;
                        }
                    }
                }
            }


        }
        return null;
    }

    public SpellList getActiveSpellSneak(Player player, Rpg rpg) {
        YamlConfiguration modifyYamlFile = rpg.getConnectListener().getPlayerDataInstance(player).getModifyYaml();

        if(modifyYamlFile.getString("active_spell_sneak") != null) {
            String spellName = modifyYamlFile.getString("active_spell_sneak");

            for(SpellList spell : SpellList.values()) {
                if(spell.name().toUpperCase(Locale.ROOT).equalsIgnoreCase(spellName.toUpperCase(Locale.ROOT))) {
                    return spell;
                }
            }
        } else {
            if(rpg.getConnectListener().getPlayerDataInstance(player).getModifyYaml().contains("spell_list")) {
                List<String> spells = rpg.getConnectListener().getPlayerDataInstance(player).getModifyYaml().getStringList("spell_list");
                if(spells.size()>0) {
                    for(SpellList spell : SpellList.values()) {
                        if(spells.contains(spell.name().toUpperCase(Locale.ROOT))) {
                            return spell;
                        }
                    }
                }
            }


        }
        return null;
    }

    public SpellList getActiveSpellRightSneak(Player player, Rpg rpg) {
        YamlConfiguration modifyYamlFile = rpg.getConnectListener().getPlayerDataInstance(player).getModifyYaml();

        if(modifyYamlFile.getString("active_spell_right_sneak") != null) {
            String spellName = modifyYamlFile.getString("active_spell_right_sneak");

            for(SpellList spell : SpellList.values()) {
                if(spell.name().toUpperCase(Locale.ROOT).equalsIgnoreCase(spellName.toUpperCase(Locale.ROOT))) {
                    return spell;
                }
            }
        } else {
            if(rpg.getConnectListener().getPlayerDataInstance(player).getModifyYaml().contains("spell_list")) {
                List<String> spells = rpg.getConnectListener().getPlayerDataInstance(player).getModifyYaml().getStringList("spell_list");
                if(spells.size()>0) {
                    for(SpellList spell : SpellList.values()) {
                        if(spells.contains(spell.name().toUpperCase(Locale.ROOT))) {
                            return spell;
                        }
                    }
                }
            }


        }
        return null;
    }

    public SpellList getActiveSpellRight(Player player, Rpg rpg) {
        YamlConfiguration modifyYamlFile = rpg.getConnectListener().getPlayerDataInstance(player).getModifyYaml();

        if(modifyYamlFile.getString("active_spell_right") != null) {
            String spellName = modifyYamlFile.getString("active_spell_right");

            for(SpellList spell : SpellList.values()) {
                if(spell.name().toUpperCase(Locale.ROOT).equalsIgnoreCase(spellName.toUpperCase(Locale.ROOT))) {
                    return spell;
                }
            }
        } else {
            if(rpg.getConnectListener().getPlayerDataInstance(player).getModifyYaml().contains("spell_list")) {
                List<String> spells = rpg.getConnectListener().getPlayerDataInstance(player).getModifyYaml().getStringList("spell_list");
                if(spells.size()>0) {
                    for(SpellList spell : SpellList.values()) {
                        if(spells.contains(spell.name().toUpperCase(Locale.ROOT))) {
                            return spell;
                        }
                    }
                }
            }


        }
        return null;
    }

    public void setActiveSpell(Player player, Rpg rpg, SpellList spell) {
        YamlConfiguration modifyYamlFile = rpg.getConnectListener().getPlayerDataInstance(player).getModifyYaml();
        File yamlFile = rpg.getConnectListener().getPlayerDataInstance(player).getYamlFile();

        modifyYamlFile.set("active_spell",spell.name());
        try {
            modifyYamlFile.save(yamlFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setActiveSpellSneak(Player player, Rpg rpg, SpellList spell) {
        YamlConfiguration modifyYamlFile = rpg.getConnectListener().getPlayerDataInstance(player).getModifyYaml();
        File yamlFile = rpg.getConnectListener().getPlayerDataInstance(player).getYamlFile();

        modifyYamlFile.set("active_spell_sneak",spell.name());
        try {
            modifyYamlFile.save(yamlFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setActiveSpellRightSneak(Player player, Rpg rpg, SpellList spell) {
        YamlConfiguration modifyYamlFile = rpg.getConnectListener().getPlayerDataInstance(player).getModifyYaml();
        File yamlFile = rpg.getConnectListener().getPlayerDataInstance(player).getYamlFile();

        modifyYamlFile.set("active_spell_right_sneak",spell.name());
        try {
            modifyYamlFile.save(yamlFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setActiveSpellRight(Player player, Rpg rpg, SpellList spell) {
        YamlConfiguration modifyYamlFile = rpg.getConnectListener().getPlayerDataInstance(player).getModifyYaml();
        File yamlFile = rpg.getConnectListener().getPlayerDataInstance(player).getYamlFile();

        modifyYamlFile.set("active_spell_right",spell.name());
        try {
            modifyYamlFile.save(yamlFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void launchSnowball(Player player, double distOverHead, double distToFacing, Class<Snowball> snowballClass, Rpg rpg, float deleteTime, Float power, String name) {

        final Location start = player.getLocation().add(0, distOverHead, 0);
        // Optionally, set y to zero at player.getLocation().getDirection().setY(0)
        final Vector facing = player.getEyeLocation().add(player.getLocation().getDirection().multiply(distToFacing)).toVector();
        // multiply by a constant, if you want a higher velocity
        final Vector initialV = facing.subtract(start.toVector()).normalize();
        final Projectile projectile = player.getWorld().spawn(start, snowballClass);
        projectile.setVelocity(initialV);
        projectile.setShooter(player);
        projectile.setGravity(false);
        projectile.setCustomName(name);

        Bukkit.getScheduler().runTaskLater(rpg, (Runnable) () -> {
            if(!projectile.isDead()) {
                projectile.remove();
                player.playSound(projectile.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, SoundCategory.MASTER,1.0f,0.8f);
                player.getWorld().spawnParticle(Particle.SMOKE_LARGE,projectile.getLocation(),1);
            }
        }, (long) (deleteTime*20));
    }
}
