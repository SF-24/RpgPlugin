// 2023. Author: S.Frynas (XpKitty), e-mail: sebastian.frynas@outlook.com, licence: GNU GPL v3

package com.xpkitty.rpgplugin.listener;

import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.StringManager;
import com.xpkitty.rpgplugin.manager.damage.DamageType;
import com.xpkitty.rpgplugin.manager.item.lightsaber.ExtendableWeaponManager;
import com.xpkitty.rpgplugin.manager.item.lightsaber.LightsaberManager;
import com.xpkitty.rpgplugin.manager.player_class.abilities.AttackType;
import org.bukkit.*;
import org.bukkit.Color;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.Objects;

public class DamageListener implements Listener {

    Rpg rpg;

    public DamageListener(Rpg rpg) {
        this.rpg = rpg;
    }

    @EventHandler
    public void onDamage(EntityDamageByBlockEvent e) {

        if (e.getDamager() != null && (e.getCause()== EntityDamageEvent.DamageCause.CONTACT && e.getDamager().getType()==Material.CACTUS)) {
            if(e.getEntityType()== EntityType.CAT) {
                e.getEntity().setGlowing(true);
                e.getEntity().getWorld().spawnParticle(Particle.WAX_OFF,e.getEntity().getLocation(),50);
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {

        if(e.getDamager() instanceof LivingEntity) {
            if (((LivingEntity) e.getDamager()).getEquipment() != null) {
                ItemStack stack = ((LivingEntity) e.getDamager()).getEquipment().getItemInMainHand();
                ItemStack offhand = ((LivingEntity) e.getDamager()).getEquipment().getItemInOffHand();

                if(ExtendableWeaponManager.isExtendableWeapon(stack) && !ExtendableWeaponManager.isExtended(stack)) {
                    e.setDamage(1f);
                }
                if(LightsaberManager.isLightsaber(stack) && !LightsaberManager.isExtended(stack)) {
                    e.setDamage(1f);
                }
                if((LightsaberManager.isLightsaber(stack)) && (LightsaberManager.isExtended(stack))) {
                    if(LightsaberManager.isLightsaber(offhand) && LightsaberManager.isExtended(offhand)) {
                        if(e.getEntity() instanceof Player) {
                            ((Player) e.getEntity()).swingOffHand();
                        }
                    }
                }


            }
        }

        if(e.getDamager().getType() == EntityType.PLAYER) {
                Player player = (Player) e.getDamager();

                ItemStack stack = player.getInventory().getItemInMainHand();

                if(rpg.getMiscPlayerManager().getNextAttack(player) != null) {

                    AttackType attackType = rpg.getMiscPlayerManager().getNextAttack(player);

                    Location loc = e.getEntity().getLocation();
                    loc.setY(loc.getY()+1);

                    if(attackType.getParticle().equals(Particle.REDSTONE)) {
                        loc.getWorld().spawnParticle(attackType.getParticle(),loc,150,0.25,0.35,0.25,1,new Particle.DustOptions(Color.RED,1));
                    } else {
                        loc.getWorld().spawnParticle(attackType.getParticle(),loc,150,0.25,0.25,0.25,1);

                    }

                    rpg.getMiscPlayerManager().setNextAttack(null,player);

                    double dmg = e.getDamage() * attackType.getDamageMultiplier();
                    e.setDamage(dmg);

                    Vector dir = player.getLocation().getDirection();
                    if(attackType.getKnockbackPower()>0) {
                        for(int i =0; i< attackType.getKnockbackPower(); i++) {
                            dir.add(player.getLocation().getDirection());

                            if(dir.getY()>0.5) {
                                dir.setY(0.5);
                            }

                            if(dir.getY()<0.25) {
                                dir.setY(0.25);
                            }
                        }
                    }


                    e.getEntity().setVelocity(dir);

                    int stunTime = (int) attackType.getStunTime()*20;

                    if(e.getEntity() instanceof LivingEntity && stunTime>0) {
                        LivingEntity le = (LivingEntity) e.getEntity();

                        le.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, stunTime, 7,true,false));
                        le.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, stunTime, attackType.getStunWeakenPower() -1,true,false));
                    }
                }


                if(stack.getItemMeta() != null) {

                    if(stack.getItemMeta().getLocalizedName().contains("STR")) {
                        String str = stack.getItemMeta().getLocalizedName();
                        String str2 = str.substring(16, 18);
                        int num = Integer.parseInt(str2);

                        YamlConfiguration yamlConfiguration = rpg.getConnectListener().getPlayerDataInstance(player).getModifyYaml();

                        int Strength = yamlConfiguration.getInt("ability_scores.STR.value");
                        StringManager stringManager = new StringManager();
                        double dmg = e.getDamage();
                        double newDmg = dmg / 2;
                        if (Strength < num) {
                            player.sendMessage(ChatColor.RED + "This item requires " + num + " STR");
                            System.out.println(player + "/'s STR in lower than " + Strength);
                            e.setDamage(newDmg);
                        } else {
                            stringManager.debugMessage(player, "You have enough STR to use this item.");
                        }
                    }

                    if(stack.getItemMeta().getLocalizedName().contains("DEX")) {
                        StringManager stringManager = new StringManager();
                        String str3 = stack.getItemMeta().getLocalizedName();
                        String str4 = str3.substring(16, 18);
                        int num2 = Integer.parseInt(str4);

                        YamlConfiguration yamlConfiguration2 = rpg.getConnectListener().getPlayerDataInstance(player).getModifyYaml();

                        int Strength2 = yamlConfiguration2.getInt("ability_scores.DEX.value");
                        StringManager stringManagerDex = new StringManager();
                        double dmg2 = e.getDamage();
                        double newDmg2 = dmg2 / 2;

                        if(player.hasPermission("rpgpl.debug")) {
                            stringManager.debugMessage(player, "Your dex: " + Strength2 + ", item DEX: " + num2);
                            stringManager.debugMessage(player, "Your dmg: " + dmg2 + ", dmg / 2: " + newDmg2);
                        }
                        if (Strength2 < num2) {
                            player.sendMessage(ChatColor.RED + "This item requires " + num2 + " DEX");
                            System.out.println(player + "/'s DEX in lower than " + Strength2);
                            e.setDamage(newDmg2);
                        } else {
                            stringManager.debugMessage(player, "You have enough DEX to use this item.");
                        }

                    }
                }





                if(stack.getType() != Material.AIR && Objects.requireNonNull(stack.getItemMeta()).getLocalizedName() != null) {
                    String name = player.getInventory().getItemInMainHand().getItemMeta().getLocalizedName();
                    if(name.length()>=5) {
                        if(name.charAt(2) == 'd') {
                            int first = Integer.parseInt(String.valueOf(name.charAt(1)));
                            int second = Integer.parseInt(String.valueOf(name.charAt(4)));

                            int damage = 0;
                            for(int i = 1; i <= first; i++) {
                                int number = randomNumber(1, second);
                                damage += number;
                            }

                            if(name.length()>=7) {
                                int value = Integer.parseInt(String.valueOf(name.charAt(6)));
                                if(name.charAt(5) == '+') {
                                    damage += value;
                                } else if(name.charAt(5) == '-') {
                                    damage -= value;
                                }
                            }

                            String damageType;
                            if(name.length()>=12) {
                                damageType = name.substring(7,11);

                            } else {
                                damageType = "default";
                            }


                            e.setDamage(damage);
                        }
                    }
                }
            }
        //}
    }

    public int randomNumber(int min, int max) {
        int number = (int)(Math.random()*(max-min+1)+min);
        return number;
    }
}
