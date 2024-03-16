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
package com.xpkitty.rpgplugin.command;

import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.MiscPlayerManager;
import com.xpkitty.rpgplugin.manager.skills.morph.MorphPlayer;
import com.xpkitty.rpgplugin.manager.data.player_data.PlayerSkinFile;
import com.xpkitty.rpgplugin.manager.hogwarts.HogwartsHouseList;
import com.xpkitty.rpgplugin.manager.hogwarts.HogwartsHouseManager;
import com.xpkitty.rpgplugin.manager.hogwarts.HogwartsHouseMathManager;
import com.xpkitty.rpgplugin.manager.player_class.abilities.AbilityType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class RpgCommand implements CommandExecutor {

    Rpg rpg;

    public RpgCommand(Rpg rpg) {
        this.rpg=rpg;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player) {
            Player player = (Player) sender;

            if(args.length==1&&args[0].equalsIgnoreCase("morph")){
                MorphPlayer.MorphPlayer(rpg,player);
            }

            if(args.length >= 2 && args[0].equalsIgnoreCase("ability_scores")) {
                if(args.length>=3 && args[1].equalsIgnoreCase("set")) {
                    YamlConfiguration yamlConfiguration = rpg.getConnectListener().getPlayerDataInstance(player).getModifyYaml();
                    File file = rpg.getConnectListener().getPlayerDataInstance(player).getYamlFile();

                    if(yamlConfiguration.contains("ability_scores." + args[2].toUpperCase())) {
                        int val = Integer.parseInt(args[3]);
                        int mod = MiscPlayerManager.calculateAbilityScoreModifier(val);
                        yamlConfiguration.set("ability_scores." + args[2].toUpperCase() + ".value", val);
                        yamlConfiguration.set("ability_scores." + args[2].toUpperCase() + ".modifier", mod);
                        try {
                            yamlConfiguration.save(file);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "ERROR! This ability score type does not exist");
                    }
                }


            } else if(args[0].equalsIgnoreCase("energy")) {
                if(args[1].equalsIgnoreCase("set") && args.length==3) {
                    try {
                        int count= Integer.parseInt(args[2]);
                        rpg.getConnectListener().getPlayerSpellFile(player).setCurrentEnergy(count);
                    } catch (NumberFormatException e) {
                        player.sendMessage(ChatColor.RED + "Specify a number you dunderhead");
                    }
                }

            } else if(args.length==2 && args[0].equalsIgnoreCase("set_ability_sneak_setting")) {
                if(args[1].equalsIgnoreCase("true")) {
                    MiscPlayerManager.setSneakAbilitySetting(player,true);
                    player.sendMessage("Set ability sneak setting to true");
                } else if(args[1].equalsIgnoreCase("false")) {
                    MiscPlayerManager.setSneakAbilitySetting(player,false);
                    player.sendMessage("Set ability sneak setting to false");
                } else {
                    player.sendMessage(ChatColor.RED + "Please specify TRUE or FALSE");
                }
            } else if(args.length==2 && args[0].equalsIgnoreCase("set_hotbar_count_setting")) {

                try {
                    int i = Integer.parseInt(args[1]);

                    if (i > 0 && i < 9) {
                        rpg.getConnectListener().getPlayerSpellFile(player).setHotBarCount(i);
                    } else {
                        player.sendMessage(ChatColor.RED + "Specify a number from 1 to 9");
                    }
                } catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.RED + "ERROR! Specify an integer you dunderhead!");
                }


            } else if(args.length==2 && args[0].equalsIgnoreCase("set_lightsaber_right_click_eject_setting")) {

                if(args[1].equalsIgnoreCase("true")) {
                    MiscPlayerManager.setLightsaberRightClickToggleSetting(player,true);
                } else if(args[1].equalsIgnoreCase("false")) {
                    MiscPlayerManager.setLightsaberRightClickToggleSetting(player,false);
                    MiscPlayerManager.setLightsaberRightClickToggleSetting(player,false);
                } else {
                    player.sendMessage(ChatColor.RED + "Error! Join the Dark Side or specify a boolean.");
                }

            } else if(args.length==2 && args[0].equalsIgnoreCase("set_auto_cast")) {

                if(args[1].equalsIgnoreCase("true")) {
                    MiscPlayerManager.setAutoCastSetting(player,true);
                } else if(args[1].equalsIgnoreCase("false")) {
                    MiscPlayerManager.setAutoCastSetting(player,false);
                } else {
                    player.sendMessage(ChatColor.RED + "Error! Join the Death Eaters or specify a boolean.");
                }


            } else if(args[0].equalsIgnoreCase("skin") || args[0].equalsIgnoreCase("skins") || args[0].equalsIgnoreCase("playerskin") || args[0].equalsIgnoreCase("playerskins")) {
                if(args[1].equalsIgnoreCase("list") && args.length==2) {
                    player.sendMessage(ChatColor.GOLD + "Listing Skins:");

                    for(String skin : rpg.getConnectListener().getPlayerSkinInstance(player).listSkins()) {
                        PlayerSkinFile playerSkinFile = rpg.getConnectListener().getPlayerSkinInstance(player);

                        if(playerSkinFile.getModifySkinFile().getString("skins." + skin + ".texture").equalsIgnoreCase("null") || playerSkinFile.getModifySkinFile().getString("skins." + skin + ".signature").equalsIgnoreCase("null")) {
                            player.sendMessage(" " + ChatColor.RED + skin);
                        } else {
                            player.sendMessage( " " + skin);
                        }


                    }
                } else if(args[1].equalsIgnoreCase("create") && args.length==3) {
                    player.sendMessage(ChatColor.AQUA + "Creating new section in player skins: " + args[2]);

                    rpg.getConnectListener().getPlayerSkinInstance(player).createSkinIfNotExists(args[2]);

                } else if(args[1].equalsIgnoreCase("load") && args.length==3) {
                    player.sendMessage(ChatColor.AQUA + "Loading player skin: " + args[2]);

                    boolean loadedSkin = rpg.getConnectListener().getPlayerSkinInstance(player).activateSkin(args[2]);

                    if(!loadedSkin) {
                        player.sendMessage(ChatColor.RED + "ERROR LOADING SKIN!");
                    }
                }


            } else if(args[0].equalsIgnoreCase("hogwarts")) {
                if((args[1].equalsIgnoreCase("house") && args.length == 2) || (args[1].equalsIgnoreCase("house") && args[2].equalsIgnoreCase("list") && args.length == 3)) {
                    player.sendMessage("Listing hogwarts houses");
                    for(HogwartsHouseList element : HogwartsHouseList.values()) {
                        player.sendMessage(element.getPrefix() + element.name());
                    }
                } else if(args[1].equalsIgnoreCase("house")) {
                    if(args[2].equalsIgnoreCase("points")) {
                        if(args.length == 3 || (args.length==4 && args[3].equalsIgnoreCase("list"))) {
                            player.sendMessage(ChatColor.WHITE + "Displaying house points status");

                            for(HogwartsHouseList element : HogwartsHouseList.values()) {
                                int points = HogwartsHouseManager.getHousePoints(rpg, element);
                                if(rpg.getHogwartsDataManager().getModifyYaml().contains("hogwarts." + element.name().toLowerCase(Locale.ROOT))) {
                                    points = (int) rpg.getHogwartsDataManager().getModifyYaml().get("hogwarts." + element.name().toLowerCase(Locale.ROOT));
                                }
                                player.sendMessage(element.getPrefix() + element.getDisplay() + " " + element.getPrefix() + ChatColor.BOLD + points);
                            }
                        } else if(args.length == 4 && (args[3].equalsIgnoreCase("give") || args[3].equalsIgnoreCase("set") || args[3].equalsIgnoreCase("take"))) {
                            player.sendMessage(ChatColor.RED + "Please specify amount and house or player");
                            player.sendMessage("");
                            player.sendMessage(ChatColor.RED + "Type: \"/rpg hogwarts house\" or \"/rpg hogwarts house list\" for list of hogwarts houses");

                        } else if(args.length == 6 && (args[3].equalsIgnoreCase("give") || args[3].equalsIgnoreCase("set") || args[3].equalsIgnoreCase("take"))) {
                            int amount = Integer.parseInt(args[5]);

                            if(args[3].equalsIgnoreCase("give") && player.hasPermission("rpgpl.points.give.*")) {
                                if(HogwartsHouseManager.getHouseFromString(args[4]) != null) {
                                    HogwartsHouseList house = HogwartsHouseManager.getHouseFromString(args[4]);
                                    HogwartsHouseMathManager.addHousePoints(rpg, house, player, amount);

                                } else {
                                    Player target = Bukkit.getPlayer(args[4]);
                                    if(target != null) {
                                        HogwartsHouseMathManager.addHousePoints(rpg, target, player, amount);
                                    } else {
                                        player.sendMessage(ChatColor.RED + "Specified target player is null");
                                    }
                                }
                            } else if(args[3].equalsIgnoreCase("take") && player.hasPermission("rpgpl.points.take.*")) {
                                if(HogwartsHouseManager.getHouseFromString(args[4]) != null) {
                                    HogwartsHouseList house = HogwartsHouseManager.getHouseFromString(args[4]);
                                    HogwartsHouseMathManager.takeHousePoints(rpg, house, player, amount);

                                } else {
                                    Player target = Bukkit.getPlayer(args[4]);
                                    if(target != null) {
                                        HogwartsHouseMathManager.takeHousePoints(rpg, target, player, amount);
                                    } else {
                                        player.sendMessage(ChatColor.RED + "Specified target player is null");
                                    }
                                }
                            } else if(args[3].equalsIgnoreCase("set") && player.hasPermission("rpgpl.points.*")) {
                                if(HogwartsHouseManager.getHouseFromString(args[4]) != null) {
                                    HogwartsHouseList house = HogwartsHouseManager.getHouseFromString(args[4]);
                                    assert house != null;
                                    HogwartsHouseManager.setHousePoints(rpg, house, amount);

                                    player.sendMessage(house.getPrefix() + house.getDisplay() + ChatColor.RESET + " now has " + amount + " house points" );
                                } else {
                                    Player target = Bukkit.getPlayer(args[4]);
                                    if(target != null) {
                                        HogwartsHouseManager.setHousePoints(rpg, target, amount);
                                        player.sendMessage("Operation successful");

                                    } else {
                                        player.sendMessage(ChatColor.RED + "Specified target player is null");
                                    }
                                }
                            }
                        }


                    } else if(args[2].equalsIgnoreCase("join")) {
                        if(args.length >= 4) {
                            boolean exists = false;
                            for(HogwartsHouseList element : HogwartsHouseList.values()) {
                                if(element.name().equalsIgnoreCase(args[3])) {
                                    exists = true;
                                    player.sendMessage("You have been sorted into " + element.getPrefix() + element.getDisplay());
                                    HogwartsHouseManager.setHogwartsHouse(rpg,player,element);
                                }
                            }

                            if(!exists) {
                                player.sendMessage(ChatColor.RED + "Please specify a Hogwarts house");
                            }
                        } else {
                            player.sendMessage(ChatColor.RED + "Please specify a Hogwarts house");
                        }
                    } else if(args[2].equalsIgnoreCase("get")) {
                        HogwartsHouseList house = HogwartsHouseManager.getHogwartsHouse(rpg,player);
                        if(house != null) {
                            player.sendMessage("You are in " + house.getPrefix() + house.getDisplay());
                        } else {
                            player.sendMessage(ChatColor.RED + "You have not been sorted into a Hogwarts House");
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "Invalid syntax");
                    }
                }


            } else if(args.length == 1 && args[0].equalsIgnoreCase("updatewand")) {
                if(Objects.requireNonNull(player.getInventory().getItemInMainHand().getItemMeta()).getLocalizedName().contains("HP_WAND")) {
                    ItemMeta meta = player.getInventory().getItemInMainHand().getItemMeta();
                    ItemStack item = new ItemStack(Material.MINECART);
                    item.setItemMeta(meta);
                    player.getInventory().setItemInMainHand(item);
                }

            } else if(args.length == 2 && args[0].equalsIgnoreCase("setname")) {
                player.setDisplayName(args[1]);
                player.setPlayerListName(args[1]);
            } else if(args.length == 1 && args[0].equalsIgnoreCase("getitemname")) {
                player.sendMessage(ChatColor.WHITE + player.getInventory().getItemInMainHand().getItemMeta().getDisplayName());
            } else if(args.length == 1 && args[0].equalsIgnoreCase("getlocalizeditemname")) {
                player.sendMessage(ChatColor.WHITE + Objects.requireNonNull(player.getInventory().getItemInMainHand().getItemMeta()).getLocalizedName());
            } else if(args.length >= 2 && args[0].equalsIgnoreCase("setitemname")) {
               if(player.getInventory().getItemInMainHand().getType() != Material.AIR) {
                   ItemMeta meta = player.getInventory().getItemInMainHand().getItemMeta();
                   String name = ChatColor.translateAlternateColorCodes('&',args[1]);
                   if(args.length >= 3) {
                       name = name + " " + ChatColor.translateAlternateColorCodes('&',args[2]);
                   }
                   if(args.length >= 4) {
                       name = name + " " + ChatColor.translateAlternateColorCodes('&',args[3]);
                   }
                   if(args.length >= 5) {
                       name = name + " " + ChatColor.translateAlternateColorCodes('&',args[4]);
                   }
                   if(args.length >= 6) {
                       name = name + " " + ChatColor.translateAlternateColorCodes('&',args[5]);
                   }
                   assert meta != null;
                   meta.setDisplayName(name);
                   player.getInventory().getItemInMainHand().setItemMeta(meta);
               }
            }  else if(args.length >= 2 && args[0].equalsIgnoreCase("setlocalizeditemname")) {
                if(player.getInventory().getItemInMainHand().getType() != Material.AIR) {
                    ItemMeta meta = player.getInventory().getItemInMainHand().getItemMeta();
                    String name = ChatColor.translateAlternateColorCodes('&',args[1]);
                    if(args.length >= 3) {
                        name = name + " " + ChatColor.translateAlternateColorCodes('&',args[2]);
                    }
                    if(args.length >= 4) {
                        name = name + " " + ChatColor.translateAlternateColorCodes('&',args[3]);
                    }
                    assert meta != null;
                    meta.setLocalizedName(name);
                    player.getInventory().getItemInMainHand().setItemMeta(meta);
                }
            }  else if(args.length<3 && args[0].equalsIgnoreCase("ability")) {
                player.sendMessage(ChatColor.RED + "Syntax error. Use:");
                player.sendMessage(ChatColor.RED + "/rpg ability add <ability>");
                player.sendMessage(ChatColor.RED + "/rpg ability remove <ability>");
                player.sendMessage("");
                player.sendMessage(ChatColor.RED + "Here is a list of abilities");
                for(AbilityType ability : AbilityType.values()) {
                    player.sendMessage(ChatColor.RED + "- " + ability.name().toLowerCase(Locale.ROOT));
                }
            } else if(args.length==3 && args[0].equalsIgnoreCase("ability")) {
                String abilityString = args[2];
                boolean error = true;
                AbilityType abilityType = null;

                for(AbilityType ability : AbilityType.values()) {
                    if (abilityString.equalsIgnoreCase(ability.name())) {
                        error = false;
                        abilityType = ability;
                        break;
                    }
                }

                if(!error) {

                    if(args[1].equalsIgnoreCase("add")) {

                        for(AbilityType ability : AbilityType.values()) {
                            if(args[2].equalsIgnoreCase(ability.name().toLowerCase(Locale.ROOT))) {
                                MiscPlayerManager.learnAbility(player, ability);
                            }
                        }


                    } else if(args[1].equalsIgnoreCase("remove")) {
                        YamlConfiguration modifyYaml = rpg.getConnectListener().getPlayerDataInstance(player).getModifyYaml();

                        player.sendMessage(ChatColor.WHITE + "You have unlearned ability: " + ChatColor.AQUA + abilityType.getName());

                        ArrayList<AbilityType> abilities = MiscPlayerManager.getAbilities(player);
                        for(AbilityType ability : AbilityType.values()) {
                            if(abilities.contains(ability) && ability.name().equalsIgnoreCase(abilityString)) {
                                ArrayList<String> abilitiesList = (ArrayList<String>) modifyYaml.getStringList("abilities");
                                abilitiesList.remove(ability.name().toLowerCase(Locale.ROOT));
                                modifyYaml.set("abilities",abilitiesList);
                            }
                        }
                        try {
                            modifyYaml.save(rpg.getConnectListener().getPlayerDataInstance(player).getComboFileYaml());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    } else {
                        player.sendMessage(ChatColor.RED + "Syntax error. Use:");
                        player.sendMessage(ChatColor.RED + "/rpg ability add <ability>");
                        player.sendMessage(ChatColor.RED + "/rpg ability remove <ability>");
                        player.sendMessage("");
                        player.sendMessage(ChatColor.RED + "Here is a list of abilities");
                        for(AbilityType ability : AbilityType.values()) {
                            player.sendMessage(ChatColor.RED + "- " + ability.name().toLowerCase(Locale.ROOT));
                        }
                    }

                } else {
                    player.sendMessage(ChatColor.RED + "Syntax error. Use:");
                    player.sendMessage(ChatColor.RED + "/rpg ability add <ability>");
                    player.sendMessage(ChatColor.RED + "/rpg ability remove <ability>");
                    player.sendMessage("");
                    player.sendMessage(ChatColor.RED + "Here is a list of abilities");
                    for(AbilityType ability : AbilityType.values()) {
                        player.sendMessage(ChatColor.RED + "- " + ability.name().toLowerCase(Locale.ROOT));
                    }
                }







            } else if(args.length==2 && args[0].equalsIgnoreCase("setskillpoints")) {
                YamlConfiguration yamlConfiguration = rpg.getConnectListener().getPlayerDataInstance(player).getModifyYaml();
                File file = rpg.getConnectListener().getPlayerDataInstance(player).getYamlFile();

                yamlConfiguration.set("skill_points",Integer.parseInt(args[1]));
                player.sendMessage("You now have " + ChatColor.AQUA + args[1] + ChatColor.RESET + " skill points.");
                try {
                    yamlConfiguration.save(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if(args.length>=4 && args[0].equalsIgnoreCase("vector")) {
                float x = Float.parseFloat(args[1]);
                float y = Float.parseFloat(args[2]);
                float z = Float.parseFloat(args[3]);

                Vector vector = new Vector(x,y,z);
                player.setVelocity(vector);

            } else {
                player.sendMessage(ChatColor.RED + "ERROR! Incorrect arguments.");
            }



        }
        System.out.println("Only a player can execute this command.");


        return false;
    }
}
