// 2023. Author: S.Frynas (XpKitty), e-mail: sebastian.frynas@outlook.com, licence: GNU GPL v3

package com.xpkitty.rpgplugin.command;

import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.item.GetItem;
import com.xpkitty.rpgplugin.manager.item.lightsaber.LightsaberColour;
import com.xpkitty.rpgplugin.manager.item.lightsaber.LightsaberList;
import com.xpkitty.rpgplugin.manager.item.lightsaber.LightsaberManager;
import com.xpkitty.rpgplugin.manager.spells.OldWand;
import com.xpkitty.rpgplugin.manager.spells.enum_list.SpellList;
import com.xpkitty.rpgplugin.manager.spells.wand.GetWand;
import com.xpkitty.rpgplugin.manager.spells.wand.WandCore;
import com.xpkitty.rpgplugin.manager.spells.wand.WandType;
import com.xpkitty.rpgplugin.manager.spells.wand.WandWood;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CrossbowMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.UUID;

public class GetItemCommand implements CommandExecutor {
    Rpg rpg;

    public GetItemCommand(Rpg rpg) {
        this.rpg = rpg;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;



            if(args[0].equalsIgnoreCase("hp_wand")) {
                WandCore wandCore = null;
                WandWood wandWood = null;
                WandType wandType = null;

                boolean coreExists = false;
                boolean woodExists = false;
                boolean typeExists = false;

                if (args.length != 4) {
                    player.sendMessage(ChatColor.RED + "INCORRECT COMMAND SYNTAX, USE:");
                    player.sendMessage(ChatColor.RED + "/getitem hp_wand <wand core>|random <wand wood>|random <wand texture>|random");
                } else {
                    for (WandCore element : WandCore.values()) {
                        if (args[1].equalsIgnoreCase(element.name())) {
                            wandCore = element;
                            coreExists = true;
                            break;
                        }
                    }

                    for (WandWood element : WandWood.values()) {
                        if (args[2].equalsIgnoreCase(element.name())) {
                            wandWood = element;
                            woodExists = true;
                            break;
                        }
                    }

                    for (WandType element : WandType.values()) {
                        if (args[3].equalsIgnoreCase(element.name())) {
                            wandType = element;
                            typeExists = true;
                            break;
                        }
                    }

                    if (args[1].equalsIgnoreCase("random")) {
                        wandCore = GetWand.getRandomWandCore(1, 1);
                        coreExists = true;
                    }

                    if (args[2].equalsIgnoreCase("random")) {
                        wandWood = GetWand.getRandomWandWood();
                        woodExists = true;
                    }

                    if (args[3].equalsIgnoreCase("random")) {
                        wandType = GetWand.getRandomWandTexture(wandWood);
                        typeExists = true;
                    }

                    if (coreExists && woodExists && typeExists) {
                        player.sendMessage(ChatColor.WHITE + "Created new wand");
                        GetWand.GetCustomWand(player, false, wandCore, wandWood, wandType);
                    } else {
                        if (!coreExists) {
                            player.sendMessage(ChatColor.RED + "Wand core does not exist, displaying list:");
                            for (WandCore element : WandCore.values()) {
                                player.sendMessage(element.name().toLowerCase(Locale.ROOT));
                            }
                        } else if (!woodExists) {
                            player.sendMessage(ChatColor.RED + "Wand wood does not exist, displaying list:");
                            for (WandWood element : WandWood.values()) {
                                player.sendMessage(element.name().toLowerCase(Locale.ROOT));
                            }
                        } else {
                            player.sendMessage(ChatColor.RED + "Wand texture does not exist, displaying list:");
                            for (WandType element : WandType.values()) {
                                player.sendMessage(element.name().toLowerCase(Locale.ROOT));
                            }
                        }
                    }
                }


            } else if(args[0].equalsIgnoreCase("darksaber") && args.length==1) {

                ItemStack darkSaber = new ItemStack(Material.NETHERITE_SWORD);
                ItemMeta darkSaberMeta = darkSaber.getItemMeta();

                darkSaberMeta.setDisplayName(ChatColor.GOLD + "Darksaber");
                darkSaberMeta.setCustomModelData(9);
                darkSaberMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(UUID.randomUUID(), "damage", 14, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
                darkSaberMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(UUID.randomUUID(), "speed", -2.25, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
                darkSaberMeta.setUnbreakable(true);
                darkSaberMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                darkSaberMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

                ArrayList<String> lore = new ArrayList<>();

                lore.add(" ");
                lore.add(ChatColor.GRAY + "When in Main Hand:");
                lore.add(ChatColor.DARK_GREEN + " 15 Attack Damage");
                lore.add(ChatColor.DARK_GREEN + " 1.75 Attack Speed");

                darkSaberMeta.setLore(lore);

                darkSaber.setItemMeta(darkSaberMeta);

                player.getInventory().addItem(darkSaber);





                // LIGHTSABER
            } else if(args[0].equalsIgnoreCase("lightsaber")) {

                if(args.length==4) {

                    int damage = 5;
                    int id = 0;

                    try {
                        damage = Integer.parseInt(args[1]);
                    } catch (NumberFormatException e) {
                        player.sendMessage(ChatColor.RED + "Usage: /getitem lightsaber <attack damage> <id> <colour>");
                        player.sendMessage(ChatColor.RED + "Attack Damage must be an integer");
                    }
                    try {
                        id = Integer.parseInt(args[2]);
                    } catch (NumberFormatException e) {
                        player.sendMessage(ChatColor.RED + "Usage: /getitem lightsaber <attack damage> <id> <colour>");
                        player.sendMessage(ChatColor.RED + "Attack Damage must be an integer");
                    }

                    String colour = args[3];

                    boolean validatedId = false;
                    boolean validatedColour = false;


                    //VALIDATE COLOUR
                    for(LightsaberColour lightsaberColour : LightsaberColour.values()) {
                        if(lightsaberColour.name().substring(0,3).equalsIgnoreCase(colour)) {
                            if(!colour.equalsIgnoreCase("NUL")) {
                                validatedColour=true;
                            }
                        }
                    }

                    //CREATE ITEM VARIABLES
                    ItemStack saber = new ItemStack(Material.NETHERITE_SWORD);
                    ItemMeta saberMeta = saber.getItemMeta();

                    //SET VALUES
                    saberMeta.setDisplayName(ChatColor.DARK_PURPLE + "Lightsaber");


                    ArrayList<String> lore = new ArrayList<>();
                    lore.add(" ");
                    lore.add(ChatColor.GRAY + "When in Main Hand:");
                    lore.add(ChatColor.DARK_GREEN + " " + damage + " Attack Damage");
                    lore.add(ChatColor.DARK_GREEN + " 1.75 Attack Speed");

                    // set damageM
                    int damageM = damage-1;

                    // Get lightsaber type
                    LightsaberList lightsaberType= LightsaberManager.getLightsaberType(id);

                    float attackSpeed = lightsaberType.getLightsaberType().getBaseSpeed();
                    attackSpeed = -1*(4-attackSpeed);


                    saberMeta.setCustomModelData(id);
                    saberMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(UUID.randomUUID(), "damage", damageM, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
                    saberMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(UUID.randomUUID(), "speed", -2.25, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
                    saberMeta.setUnbreakable(true);
                    saberMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                    saberMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                    saberMeta.setLore(lore);

                    String stringId = String.valueOf(id);
                    if(stringId.length()<2) {
                        stringId = "0" + stringId;
                    }
                    if(stringId.length()<3) {
                        stringId = "0" + stringId;
                    }

                    saberMeta.setLocalizedName("LIGHTSABER" + stringId + colour);

                    for(LightsaberList lightsaberList : LightsaberList.values()) {
                        if(lightsaberList.getSheathedId() == id) {
                            validatedId = true;
                            saberMeta.setCustomModelData(lightsaberList.getSheathedId());
                        }
                    }

                    saber.setItemMeta(saberMeta);
                    player.getInventory().addItem(saber);


                } else {
                    player.sendMessage(ChatColor.RED + "Usage: /getitem lightsaber <attack damage> <id> <colour>");

                    if(args.length==3) {
                        player.sendMessage(ChatColor.RED + "displaying list of colours in format: <colour>|<id>");

                        for(LightsaberColour lightsaberColour : LightsaberColour.values()) {
                            player.sendMessage(ChatColor.RED + lightsaberColour.name() + "|" + lightsaberColour.name().substring(0,3));
                        }
                    } else if(args.length==2) {
                        player.sendMessage(ChatColor.RED + "Listing lightsaber ids in format: name[id]");

                        for(LightsaberList lightsaberList : LightsaberList.values()) {
                            player.sendMessage(ChatColor.RED + lightsaberList.name() + " [" + lightsaberList.getSheathedId() + "]");
                        }
                    }
                }










                // BLASTER
            } else if(args[0].equalsIgnoreCase("blaster")) {
              if(args.length==5 || args.length==6 || args.length==7 || args.length==8 || args.length==9) {
                  int damageWhenShot = Integer.parseInt(args[1]);
                  int modelData = Integer.parseInt(args[4]);

                  boolean damageValidated = false;

                  if(damageWhenShot<100) {
                      damageValidated = true;
                  }

                  String material = args[3];


                  if(damageWhenShot < 1) {

                  } else {

                      boolean colorValidated = false;
                      String color = args[2];
                      if(color.length()==9 && Integer.parseInt(color) > -1) {
                          colorValidated = true;
                      }


                      Material base = null;
                      boolean materialValidated = false;

                      for(Material element : Material.values()) {
                          if(element.toString().equalsIgnoreCase(material)) {
                              base = element;
                              materialValidated = true;
                          }
                      }


                      if(materialValidated && damageValidated && colorValidated) {

                          int particleSize =2;

                          float shotSoundPitch = 1.0f;
                          int shotVolume = 5;
                          String shotSound = "minecraft:blaster.blasterpistol";

                          if(args.length>=6) {
                              particleSize = Integer.parseInt(args[5]);
                          }
                          if(args.length>=7) {
                              shotVolume = Integer.parseInt(args[6]);
                          }
                          if(args.length>=8) {
                              shotSoundPitch = Float.parseFloat(args[7]);
                          }

                          if(args.length>=9) {
                              shotSound = args[8];
                          }

                          ItemStack blaster = new ItemStack(base);
                          ItemMeta blasterMeta = blaster.getItemMeta();
                          blasterMeta.setCustomModelData(modelData);

                          String intString = String.valueOf(damageWhenShot);
                          if(intString.length()==1) { intString = "0" + intString;}

                          String particleSizeString = String.valueOf(particleSize);

                          if(particleSizeString.length()<2) {
                              particleSizeString = "0" + particleSize;
                          }


                          String soundVolume = String.valueOf(shotVolume);
                          if(soundVolume.length()==1) {
                              soundVolume = "0" + soundVolume;
                          }
                          if(soundVolume.length()>2) {
                              soundVolume = "01";
                          }
                          if(soundVolume.length()<1) {
                              soundVolume = "01";
                          }

                          String soundPitch = String.valueOf(shotSoundPitch);
                          if(soundPitch.length()==1) {
                              soundPitch = "00" + soundVolume;
                          }
                          if(soundPitch.length()==2) {
                              soundPitch = "0" + soundVolume;
                          }
                          if(soundPitch.length()>3) {
                              soundPitch = "1.0";
                          }
                          if(soundPitch.length()<3) {
                              soundPitch = "1.0";
                          }

                          String localizedName = "blaster" + intString + color + particleSizeString + soundVolume + soundPitch + shotSound;

                          ArrayList<String> lore = new ArrayList<>();

                          lore.add(ChatColor.GRAY + "Right Click to Shoot");
                          lore.add("");
                          lore.add(ChatColor.GRAY + "When Shot:");
                          lore.add(ChatColor.BLUE + " Take " + damageWhenShot+ " Damage");

                          blasterMeta.setLore(lore);
                          blasterMeta.setDisplayName(ChatColor.GOLD + "Blaster");
                          blasterMeta.setLocalizedName(localizedName);
                          blasterMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES,ItemFlag.HIDE_UNBREAKABLE,ItemFlag.HIDE_DYE,ItemFlag.HIDE_POTION_EFFECTS,ItemFlag.HIDE_ENCHANTS);

                          blaster.setItemMeta(blasterMeta);
                          player.getInventory().addItem(blaster);


                      } else {
                          player.sendMessage(ChatColor.RED + "Incorrect syntax, usage:");
                          player.sendMessage(ChatColor.RED + "/getitem blaster <damage on shot> <color> <material> <custom model data> [laser particle size] [shot volume] [shot sound pitch] [shot sound]");

                          if(!damageValidated) {
                              player.sendMessage(ChatColor.RED + "Damage cannot be more than 99");
                          }
                          if(!colorValidated) {
                              player.sendMessage(ChatColor.RED + "Colour is incorrect, please specify 9 digit RGB code");
                          }
                          if(!materialValidated) {
                              player.sendMessage("Material does not exist");
                          }
                      }
                  }
              } else {
                  player.sendMessage(ChatColor.RED + "Incorrect syntax, usage:");
                  player.sendMessage(ChatColor.RED + "/getitem blaster <damage on shot> <color> <material> <custom model data> [laser particle size] [shot volume] [shot sound pitch] [shot sound]");

                  if(args.length==3) {
                      player.sendMessage(ChatColor.RED + "displaying list of materials");

                      for(Material element : Material.values()) {
                          player.sendMessage(ChatColor.RED + " " + element.toString().toLowerCase(Locale.ROOT));
                      }
                  }
              }





            } else if(args.length==1) {
                if(args[0].equalsIgnoreCase("record_book")) {
                    ItemStack book = new ItemStack(Material.BOOK);
                    ItemMeta bookMeta = book.getItemMeta();
                    bookMeta.setLocalizedName("record_book");
                    bookMeta.setDisplayName(ChatColor.WHITE + "Record Book");
                    book.setItemMeta(bookMeta);
                    player.getInventory().addItem(book);
                } else if(args[0].equalsIgnoreCase("chocolate_frog_card")) {
                    player.sendMessage(ChatColor.AQUA + "You have been given a chocolate frog card");
                    player.getInventory().addItem(GetItem.getChocolateFrogCard(new ArrayList<>()));
                } else if(args[0].equalsIgnoreCase("spawn_stone")) {
                    ItemStack spawnStone = new ItemStack(Material.NETHERITE_INGOT);
                    ItemMeta spawnStoneMeta = spawnStone.getItemMeta();
                    spawnStoneMeta.setDisplayName(ChatColor.GOLD + "Spawn Stone");
                    ArrayList<String> lore = new ArrayList<>();
                    lore.add(ChatColor.GOLD.toString() + ChatColor.ITALIC + "Legendary Item");
                    lore.add("");
                    lore.add(ChatColor.GRAY + "Right click when holding this item in your hand");
                    lore.add(ChatColor.GRAY + "to teleport to your latest spawn point.");
                    lore.add("");
                    lore.add(ChatColor.GRAY + "Can only be used once every half hour");
                    spawnStoneMeta.setLocalizedName("spawn_stone");
                    spawnStoneMeta.setCustomModelData(2);
                    spawnStoneMeta.setLore(lore);
                    spawnStone.setItemMeta(spawnStoneMeta);
                    player.getInventory().addItem(spawnStone);
                } else if(args[0].equalsIgnoreCase("disguise_potion_steve")) {
                    ItemStack item = new ItemStack(Material.POTION);
                    PotionMeta itemMeta = (PotionMeta) item.getItemMeta();
                    itemMeta.setLocalizedName("disguise-steve-1");
                    itemMeta.setDisplayName(ChatColor.WHITE + "Disguise Potion");
                    itemMeta.setColor(Color.GREEN);
                    ArrayList<String> lore = new ArrayList<>();
                    lore.add(ChatColor.BLUE + "Disguise I ∞");
                    itemMeta.setLore(lore);
                    itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
                    item.setItemMeta(itemMeta);
                    player.getInventory().addItem(item);
                } else if(args[0].equalsIgnoreCase("teleport_scroll")) {
                    player.sendMessage(ChatColor.RED + "This teleport scroll does not exist.");
                    player.sendMessage(ChatColor.RED + "List of teleport scrolls:");
                    for (String string : rpg.getConfig().getConfigurationSection("teleport_locations").getKeys(false)) {
                        player.sendMessage(ChatColor.RED + "- " + string);
                    }


                    //GUNS AND BLASTERS
                } else if(args[0].equalsIgnoreCase("gun")) {
                    player.sendMessage("GUN");
                    ItemStack staff = new ItemStack(Material.BOW);
                    ItemMeta staffMeta = staff.getItemMeta();
                    staffMeta.setCustomModelData(6);
                    staffMeta.setDisplayName(ChatColor.GOLD + "Lasgun");
                    staffMeta.setLocalizedName("GUN");
                    staffMeta.setLore(Collections.singletonList(ChatColor.GRAY + "Right click to shoot"));
                    staff.setItemMeta(staffMeta);
                    player.getInventory().addItem(staff);
                } else if(args[0].equalsIgnoreCase("gun1")) {
                    player.sendMessage("GUN");
                    ItemStack staff = new ItemStack(Material.CROSSBOW);
                    CrossbowMeta staffMeta = (CrossbowMeta) staff.getItemMeta();
                    staffMeta.setCustomModelData(8);
                    staffMeta.setDisplayName(ChatColor.GOLD + "Lasgun");
                    staffMeta.setLocalizedName("GUN");
                    staffMeta.addItemFlags(ItemFlag.HIDE_DYE);
                    staffMeta.setChargedProjectiles(Collections.singletonList(new ItemStack(Material.ARROW)));
                    staffMeta.setLore(Collections.singletonList(ChatColor.GRAY + "Right click to shoot"));
                    staff.setItemMeta(staffMeta);
                    player.getInventory().addItem(staff);
                } else if(args[0].equalsIgnoreCase("gun2")) {
                    player.sendMessage("GUN");
                    ItemStack staff = new ItemStack(Material.CROSSBOW);
                    CrossbowMeta staffMeta = (CrossbowMeta) staff.getItemMeta();
                    staffMeta.setCustomModelData(8);
                    staffMeta.setDisplayName(ChatColor.GOLD + "Blaster");
                    staffMeta.addItemFlags(ItemFlag.HIDE_DYE);
                    staffMeta.setLocalizedName("GUN1");
                    staffMeta.setChargedProjectiles(Collections.singletonList(new ItemStack(Material.ARROW)));
                    staffMeta.setLore(Collections.singletonList(ChatColor.GRAY + "Right click to shoot"));
                    staff.setItemMeta(staffMeta);
                    player.getInventory().addItem(staff);
                } else if(args[0].equalsIgnoreCase("gun3")) {
                    player.sendMessage("GUN");
                    ItemStack staff = new ItemStack(Material.BOW);
                    ItemMeta staffMeta = staff.getItemMeta();
                    staffMeta.setCustomModelData(9);
                    staffMeta.setDisplayName(ChatColor.GOLD + "Blaster");
                    staffMeta.setLocalizedName("GUN1");
                    staffMeta.setLore(Collections.singletonList(ChatColor.GRAY + "Right click to shoot"));
                    staff.setItemMeta(staffMeta);
                    player.getInventory().addItem(staff);

                    //SPELL STAVES
                } else if(args[0].equalsIgnoreCase("staff")) {
                    player.sendMessage("You have been given the Staff of Fireballs");
                    ItemStack staff = new ItemStack(Material.BLAZE_ROD);
                    ItemMeta staffMeta = staff.getItemMeta();
                    staffMeta.setDisplayName(ChatColor.GOLD + "Fire Staff");
                    staffMeta.setLocalizedName("SPELL_FIREBALL");
                    staffMeta.setLore(Collections.singletonList(ChatColor.GRAY + "Right click to fire fireball"));
                    staff.setItemMeta(staffMeta);
                    player.getInventory().addItem(staff);
                } else if(args[0].equalsIgnoreCase("stupefy_wand")) {
                    player.sendMessage("Stupefy Wand");
                    ItemStack staff = new ItemStack(Material.STICK);
                    ItemMeta staffMeta = staff.getItemMeta();
                    staffMeta.setDisplayName(ChatColor.GOLD + "Wand [Stupefy]");
                    staffMeta.setLocalizedName("SPELL_STUPEFY");
                    staffMeta.setLore(Collections.singletonList(ChatColor.GRAY + "Right click to fire stupefy spell"));
                    staff.setItemMeta(staffMeta);
                    player.getInventory().addItem(staff);
                } else if(args[0].equalsIgnoreCase("wingardium_wand")) {
                    player.sendMessage("Wingardium Lebiosa Wand");
                    ItemStack staff = new ItemStack(Material.STICK);
                    ItemMeta staffMeta = staff.getItemMeta();
                    staffMeta.setDisplayName(ChatColor.GOLD + "Wand [Wingardium Leviosa]");
                    staffMeta.setLocalizedName("SPELL_WINGARDIUM");
                    staffMeta.setLore(Collections.singletonList(ChatColor.GRAY + "Right click to fire wingardium leviosa spell"));
                    staff.setItemMeta(staffMeta);
                    player.getInventory().addItem(staff);


                    //BASIC WAND
                } else if(args[0].equalsIgnoreCase("wand")) {
                    OldWand wand = new OldWand();
                    wand.giveWand(player);


                    //ADVANCED WAND: WANDBOX
                } else if(args[0].equalsIgnoreCase("wandbox")) {
                    GetWand.GetWandBox(player);


                    //SPELL SCROLL LIST
                } else if(args[0].equalsIgnoreCase("spell_scroll")) {
                    player.sendMessage(ChatColor.RED + "Usage: /getitem spell_scroll <spell>");
                    player.sendMessage("");
                    player.sendMessage(ChatColor.RED + "Spell list:");
                    for(SpellList spell : SpellList.values()) {
                        player.sendMessage(ChatColor.RED + "- " + spell.name().toLowerCase(Locale.ROOT));
                    }
                }


                //TELEPORTATION SCROLL
            } else if(args.length==2) {
                 if(args[0].equalsIgnoreCase("teleport_scroll")) {
                     boolean found = false;
                     for (String string : rpg.getConfig().getConfigurationSection("teleport_locations").getKeys(false)) {
                         if (args[1].equalsIgnoreCase(string)) {
                             String locVal = "teleport_locations." + string + ".name";
                             String val = ChatColor.translateAlternateColorCodes('&',rpg.getConfig().getString(locVal));
                             ItemStack scroll = new ItemStack(Material.BOOK);
                             ItemMeta scrollMeta = scroll.getItemMeta();
                             scrollMeta.setDisplayName(ChatColor.DARK_PURPLE + val + " Teleportation Scroll");
                             scrollMeta.setCustomModelData(rpg.getConfig().getInt("teleport_locations." + string + ".model_data"));
                             ArrayList<String> lore = new ArrayList<>();
                             lore.add(ChatColor.DARK_PURPLE.toString() + ChatColor.ITALIC + "Exotic Item");
                             lore.add("");
                             lore.add(ChatColor.GRAY + "Right click when holding this item");
                             lore.add(ChatColor.GRAY + "in your hand to teleport to " + val);
                             lore.add("");
                             lore.add(ChatColor.GRAY + "Consumed on use.");

                             if(rpg.getConfig().contains("teleport_locations." + string + ".level")) {
                                 int reqLevel = rpg.getConfig().getInt("teleport_locations." + string + ".level");

                                 if(reqLevel > 1) {
                                     lore.add("");
                                     lore.add(ChatColor.GRAY + "Requires level " + rpg.getConfig().getInt("teleport_locations." + string + ".level"));
                                 }
                             }

                             scrollMeta.setLore(lore);
                             scrollMeta.setLocalizedName("tp_scroll_" + string);
                             found = true;
                             scroll.setItemMeta(scrollMeta);
                             player.getInventory().addItem(scroll);
                         }
                     }
                     if (!found) {
                         player.sendMessage(ChatColor.RED + "This teleport scroll does not exist.");
                         player.sendMessage(ChatColor.RED + "List of teleport scrolls:");
                         for (String string : rpg.getConfig().getConfigurationSection("teleport_locations").getKeys(false)) {
                            player.sendMessage(ChatColor.RED + "- " + string);
                         }
                     }

                     //SPELL SCROLL
                 } else if(args[0].equalsIgnoreCase("spell_scroll")) {

                     String scrollName = args[1];

                     boolean found = false;

                     for(SpellList spell : SpellList.values()) {
                         if(scrollName.toUpperCase(Locale.ROOT).equalsIgnoreCase(spell.name())) {
                             found = true;

                             ItemStack scroll = new ItemStack(Material.BOOK);
                             ItemMeta scrollMeta = scroll.getItemMeta();
                             scrollMeta.setDisplayName(ChatColor.LIGHT_PURPLE+"Spell Scroll: " + ChatColor.LIGHT_PURPLE + spell.getDisplay() + ChatColor.DARK_PURPLE);
                             scrollMeta.setLore(Collections.singletonList(ChatColor.GRAY + "Right click when holding to learn spell"));
                             scrollMeta.setLocalizedName("SPELLSCROLL" + spell.name());
                             scrollMeta.setCustomModelData(2);
                             scroll.setItemMeta(scrollMeta);
                             player.getInventory().addItem(scroll);
                             player.sendMessage("You have been given " + ChatColor.AQUA + spell.getDisplay() + ChatColor.RESET + " spell scroll");
                         }
                     }

                     if(!found) {
                         player.sendMessage(ChatColor.RED + "Usage: /getitem spell_scroll <spell>");
                         player.sendMessage("");
                         player.sendMessage(ChatColor.RED + "Spell list:");
                         for(SpellList spell : SpellList.values()) {
                             player.sendMessage(ChatColor.RED + "- " + spell.name().toLowerCase(Locale.ROOT));
                         }
                     }
                 }
            }
        }

        return false;
    }
}
