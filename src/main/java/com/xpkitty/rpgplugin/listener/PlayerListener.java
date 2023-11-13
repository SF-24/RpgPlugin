// 2023. Author: S.Frynas (XpKitty), e-mail: sebastian.frynas@outlook.com, licence: GNU GPL v3

package com.xpkitty.rpgplugin.listener;

import com.jeff_media.armorequipevent.ArmorEquipEvent;
import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.AbilityScores;
import com.xpkitty.rpgplugin.manager.MiscPlayerManager;
import com.xpkitty.rpgplugin.manager.StringManager;
import com.xpkitty.rpgplugin.manager.data.player_data.PlayerDataManager;
import com.xpkitty.rpgplugin.manager.food.FoodContainer;
import com.xpkitty.rpgplugin.manager.item.PlayerHand;
import com.xpkitty.rpgplugin.manager.item.lightsaber.ExtendableWeaponManager;
import com.xpkitty.rpgplugin.manager.item.lightsaber.LightsaberManager;
import com.xpkitty.rpgplugin.manager.player_class.ArmourList;
import com.xpkitty.rpgplugin.manager.skin.ArmourSkinManager;
import com.xpkitty.rpgplugin.manager.skin.SkinChangeStatus;
import com.xpkitty.rpgplugin.manager.skin.Trigger;
import com.xpkitty.rpgplugin.manager.spells.spell_learning.SpellLearnManager;
import com.xpkitty.rpgplugin.manager.spells.spell_ui.HotbarType;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CrossbowMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

public class PlayerListener implements Listener {

    YamlConfiguration yamlConfiguration;
    File file;
    Rpg rpg;

    public PlayerListener(Rpg rpg) {
        this.rpg = rpg;
    }


    // player eat event PlayerEatEvent

    @EventHandler
    public void onConsume(PlayerItemConsumeEvent e) {
        Player player = e.getPlayer();

        if(e.getItem().getItemMeta()!=null) {
            if(!e.isCancelled()) {
                String locName = e.getItem().getItemMeta().getLocalizedName();
                ItemStack dropItem = null;
                FoodContainer container = null;
                EquipmentSlot hand = e.getHand();
                locName=locName.toLowerCase(Locale.ROOT);

                if(locName.contains("can")) {
                    container=FoodContainer.CAN;
                } else if(locName.contains("cup")) {
                    container=FoodContainer.COFFEE_CUP;
                } else if(locName.contains("paper_cup")) {
                    container=FoodContainer.PAPER_CUP;
                } else if(locName.contains("bowl")) {
                    container=FoodContainer.BOWL;
                } else if(locName.contains("coffee_cup")) {
                    container=FoodContainer.COFFEE_CUP;
                } else if(locName.contains("glass_mug")) {
                    container=FoodContainer.GLASS_MUG;
                }

                if(container!=null) {
                    dropItem = container.getItem();


                    ItemStack finalDropItem = dropItem;
                    rpg.getServer().getScheduler().runTaskLaterAsynchronously(rpg, () -> {
                        //player.sendMessage(String.valueOf(hand));
                        if (hand.equals(EquipmentSlot.HAND)) {
                            player.getInventory().setItemInMainHand(finalDropItem);
                        } else if (hand.equals(EquipmentSlot.OFF_HAND)) {
                            player.getInventory().setItemInOffHand(finalDropItem);
                        }
                    }, 1 / 40);
                }

            }
        }

        if(e.getItem().getType() == Material.POTION) {
            if (e.getItem().getItemMeta() != null) {
                if(e.getItem().getItemMeta().hasCustomModelData()) {

                    if (e.getItem().getItemMeta().getCustomModelData() == 1) {
                        StringManager.sendAchievement(rpg, player, "DRINK_ATHELAS", ChatColor.AQUA + "Healing drink!");

                    } else if (e.getItem().getItemMeta().getCustomModelData() == 2) {
                        StringManager.sendAchievement(rpg, player, "DRINK_MIRUVOR", ChatColor.AQUA + "Miruvor");

                    }
                }
                switch (e.getItem().getItemMeta().getLocalizedName()) {
                    case "absorb_2h":
                        if (player.getAbsorptionAmount() <= 2.0d) {
                            player.setAbsorptionAmount(2.0d);

                        }
                        break;
                    case "absorb_4h":
                        if (player.getAbsorptionAmount() <= 4.0d) {
                            player.setAbsorptionAmount(4.0d);

                        }
                        break;
                    case "absorb_5h":
                        if (player.getAbsorptionAmount() <= 5.0d) {
                            player.setAbsorptionAmount(5.0d);

                        }
                        break;
                    case "absorb_6h":
                        if (player.getAbsorptionAmount() <= 6.0d) {
                            player.setAbsorptionAmount(6.0d);

                        }
                        break;
                    case "absorb_10h":
                        if (player.getAbsorptionAmount() <= 10.0d) {
                            player.setAbsorptionAmount(10.0d);

                        }
                        break;
                    case "absorb_15h":
                        if (player.getAbsorptionAmount() <= 15.0d) {
                            player.setAbsorptionAmount(15.0d);

                        }
                        break;
                    case "absorb_20h":
                        if (player.getAbsorptionAmount() <= 20.0d) {
                            player.setAbsorptionAmount(20.0d);

                        }
                        break;
                    case "feed_20":
                        if (player.getFoodLevel() <= 20.0d) {
                            player.setFoodLevel(20);

                        }
                        break;
                    case "heal":
                        player.setHealth(player.getMaxHealth());
                        break;
                    default:
                        return;
                }

            }

        } else if(e.getItem().getType() == Material.SUSPICIOUS_STEW) {

            if(Objects.requireNonNull(e.getItem().getItemMeta()).getLocalizedName().equalsIgnoreCase("ent_draught_absorption")) {
                if(player.getAbsorptionAmount() <= 20.0d) {
                    player.setAbsorptionAmount(20.0d);

                }
            }

            if(e.getItem().getItemMeta().getCustomModelData() > 2 && e.getItem().getItemMeta().getCustomModelData()  < 11) {
                StringManager.sendAchievement(rpg, player,"EAT_ENT_STEW", ChatColor.AQUA + "Invigoration");

            } else if(e.getItem().getItemMeta().getCustomModelData() == 11) {
                StringManager.sendAchievement(rpg, player,"DRINK_ATHELAS", ChatColor.AQUA + "Athelas Brew!");
            }

            switch (e.getItem().getItemMeta().getLocalizedName()) {
                case "ent_draught_brown":
                    player.setFoodLevel(20);
                    player.setSaturation(20.0f);

                case "ent_draught_heal":
                    player.setHealth(player.getMaxHealth());

                default:
                    return;
            }
        }
        if(e.getItem().getType() == Material.MILK_BUCKET) {
                //player.setDisplayName(player.getName());
                //player.setPlayerListName(player.getName());
                player.setGlowing(false);
                player.setVisualFire(false);
            }

    }

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent e) {
        Player player = e.getPlayer();

        if((player.isSneaking())) {
            if(rpg.getClickManager().hasActiveClicks(player)) {
                rpg.getClickManager().clearClicks(player);

                StringManager.debugMessage(player, ChatColor.RED + "Clearing clicks");

                StringManager.sendActionBar(player, "§f");
            }
            if(player.getInventory().getItemInMainHand().getItemMeta() != null) {
                if(player.getInventory().getItemInMainHand().getItemMeta().hasCustomModelData()) {
                    if(player.getInventory().getItemInMainHand().getItemMeta().getCustomModelData() == 8 && player.getInventory().getItemInMainHand().getType().equals(Material.CROSSBOW)) {
                        CrossbowMeta blasterMeta = (CrossbowMeta) player.getInventory().getItemInMainHand().getItemMeta();
                        blasterMeta.setCustomModelData(7);
                        blasterMeta.setChargedProjectiles(null);
                        player.getInventory().getItemInMainHand().setItemMeta(blasterMeta);
                        player.removePotionEffect(PotionEffectType.SLOW);
                    }
                }
            }
        }

        if(!player.isSneaking()) {
            if(player.getInventory().getItemInMainHand().getItemMeta() != null) {
                if(player.getInventory().getItemInMainHand().getItemMeta().hasCustomModelData()) {
                    if(player.getInventory().getItemInMainHand().getItemMeta().getCustomModelData() == 7 && player.getInventory().getItemInMainHand().getType().equals(Material.CROSSBOW)) {
                        CrossbowMeta blasterMeta = (CrossbowMeta) player.getInventory().getItemInMainHand().getItemMeta();
                        blasterMeta.setCustomModelData(8);
                        blasterMeta.setChargedProjectiles(Collections.singletonList(new ItemStack(Material.ARROW)));
                        player.getInventory().getItemInMainHand().setItemMeta(blasterMeta);
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,1000000, 6, true, false, false));
                    }}
            }
        }
    }

    @EventHandler
    public void onItemChange(PlayerItemHeldEvent e) {
        Player player = e.getPlayer();

        if(player.isSneaking()) {
            e.setCancelled(true);
            if(player.getInventory().getItemInMainHand().getItemMeta() != null) {
                if(player.getInventory().getItemInMainHand().getItemMeta().hasCustomModelData()) {
                    if(player.getInventory().getItemInMainHand().getType().equals(Material.CROSSBOW) && player.getInventory().getItemInMainHand().getItemMeta().getCustomModelData() == 8) {
                        CrossbowMeta blasterMeta = (CrossbowMeta) player.getInventory().getItemInMainHand().getItemMeta();
                        blasterMeta.setCustomModelData(7);
                        blasterMeta.setChargedProjectiles(null);
                        player.getInventory().getItemInMainHand().setItemMeta(blasterMeta);
                        player.removePotionEffect(PotionEffectType.SLOW);
                    }
                }
            }

            e.setCancelled(false);
        }
    }


    @EventHandler
    public void playerOpenInventoryEvent(InventoryOpenEvent e) {
        rpg.getSpellHotbarManager().deadctivateSpellHotbar((Player) e.getPlayer());

        Player player = (Player) e.getPlayer();
        String inventoryTitle = ChatColor.translateAlternateColorCodes('&',e.getView().getTitle());

        if(inventoryTitle.equalsIgnoreCase(ChatColor.BLACK + "Menu") || inventoryTitle.contains("loadinvonclose") || inventoryTitle.contains("tempClear")) {
            com.xpkitty.rpgplugin.manager.MiscPlayerManager.tempClearInventory(rpg,player);
        }
    }

    @EventHandler
    public void playerCloseInventoryEvent(InventoryCloseEvent e) {
        Player player = (Player) e.getPlayer();
        ItemStack item = player.getInventory().getItemInOffHand();

        if(e.getInventory().getType().equals(InventoryType.CRAFTING)) {
            HashMap<SkinChangeStatus, ItemStack> map = ArmourSkinManager.TestArmourSkin(rpg, player, player.getInventory().getChestplate(), Trigger.CLOSE_INVENTORY);
            if (!map.containsKey(SkinChangeStatus.SKIN_UNCHANGED)) {
                ItemStack newItem = null;
                if(map.containsKey(SkinChangeStatus.SKIN_SET_NEW)) {
                    newItem=map.get(SkinChangeStatus.SKIN_SET_NEW);
                } else if(map.containsKey(SkinChangeStatus.SKIN_SET_DEFAULT)) {
                    newItem=map.get(SkinChangeStatus.SKIN_SET_DEFAULT);
                }

                if (newItem != null) {
                    if (newItem.getItemMeta() != null) {
                        player.getInventory().setChestplate(newItem);
                    }
                }
            }
        }
        String inventoryTitle = ChatColor.translateAlternateColorCodes('&',e.getView().getTitle());

        if(inventoryTitle.equalsIgnoreCase(ChatColor.BLACK + "Menu") || inventoryTitle.contains("loadinvonclose") || inventoryTitle.contains("tempClear")) {
            com.xpkitty.rpgplugin.manager.MiscPlayerManager.loadPlayerInventory(player,rpg);
        }

        if(item.getItemMeta() != null ) {
            if(item.getItemMeta().getLocalizedName().contains("hp_wand")) {
                if(com.xpkitty.rpgplugin.manager.MiscPlayerManager.hasAvaliableSlot(player)) {
                    player.getInventory().addItem(item);
                    player.getInventory().setItemInOffHand(new ItemStack(Material.AIR));
                } else {
                    player.getWorld().dropItem(player.getLocation(),item);
                    player.getInventory().setItemInOffHand(new ItemStack(Material.AIR));
                }
            }
        }
    }


    // OffHand swap event

    @EventHandler
    public void onOffhandSwap(PlayerSwapHandItemsEvent e) {
        ItemStack mainHandItem = e.getMainHandItem();
        ItemStack offHandItem = e.getOffHandItem();

        if(offHandItem != null) {
            if(offHandItem.getItemMeta() != null) {
                if(offHandItem.getItemMeta().getLocalizedName().contains("hp_wand") || offHandItem.getItemMeta().getLocalizedName().contains("spell_icon")) {
                    e.setCancelled(true);

                } else if(LightsaberManager.isLightsaber(offHandItem)) {

                } else if(ExtendableWeaponManager.isExtended(offHandItem)) {

                }
            }
        }
    }


    // HotBar slot switch event / HotBar slot change event

    @EventHandler
    public void onHotBarSlotSwitch(PlayerItemHeldEvent e) {

        ItemStack oldItem = e.getPlayer().getInventory().getItem(e.getPreviousSlot());

        if(oldItem!=null && !oldItem.getType().equals(Material.AIR)) {
            if(ExtendableWeaponManager.isExtendableWeapon(oldItem) && ExtendableWeaponManager.isExtended(oldItem)) {
                ExtendableWeaponManager.sheatheFoldingWeapon(rpg,e.getPlayer(), PlayerHand.MAIN_HAND);
            }
            if(LightsaberManager.isLightsaber(oldItem) && LightsaberManager.isExtended(oldItem)) {
                LightsaberManager.sheatheLightsaber(rpg,e.getPlayer(),PlayerHand.MAIN_HAND);
            }
        }

        if(rpg.getSpellHotbarManager().getCurrentHotbar(e.getPlayer()).equals(HotbarType.SPELL_HOTBAR)) {
            Player player = e.getPlayer();

            ItemStack newItem = player.getInventory().getItem(e.getNewSlot());

            if(newItem.getItemMeta()!=null) {
                if(newItem.getItemMeta().getLocalizedName().contains("spell_icon")) {
                    String spellId = newItem.getItemMeta().getLocalizedName().substring(10);

                    if(!player.isSneaking() || !com.xpkitty.rpgplugin.manager.MiscPlayerManager.getAutoCastSetting(rpg,player)) {
                        SpellLearnManager.loadCastTest(rpg,player, Integer.parseInt(spellId),true);
                    } else {
                        try {
                            Integer.parseInt(spellId);
                        } catch (NumberFormatException ex) {
                            player.sendMessage(ChatColor.RED + "[RpgPlugin] ERROR! SpellId cannot equal " + ChatColor.WHITE + spellId + ChatColor.RED + " please contact an administrator");
                            return;
                        }

                        rpg.getConnectListener().getPlayerSpellFile(player).setActiveSpell(Integer.parseInt(spellId));

                    }
                }
            }

            if(e.getNewSlot()!=8) {
                player.getInventory().setHeldItemSlot(8);
            }


        }

        if(rpg.getSpellHotbarManager().getCurrentHotbar(e.getPlayer()).equals(HotbarType.DEFAULT)) {
            ItemStack newItem = e.getPlayer().getInventory().getItem(e.getNewSlot());
            Player player = e.getPlayer();

            if(oldItem!=null && oldItem.getItemMeta()!=null) {
                if(oldItem.getItemMeta().getLocalizedName().contains("HP_WAND")) {
                    if(newItem!=null && newItem.getItemMeta()!=null) {
                        if(!newItem.getItemMeta().getLocalizedName().contains("HP_WAND")) {
                            // ITEM IS NOT A WAND
                            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(" "));
                        }
                    } else {
                        // ITEM HAS NO META
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(" "));
                    }
                }
            }
        }
    }
    
    // drop item event

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent e) {
        Player player = e.getPlayer();
        
        if(rpg.getSpellHotbarManager().getCurrentHotbar(player).equals(HotbarType.SPELL_HOTBAR)) {
            if(e.getItemDrop().getItemStack().getItemMeta()!=null) {
                String locName = e.getItemDrop().getItemStack().getItemMeta().getLocalizedName();
                
                if(locName.contains("spell_icon") || locName.contains("HP_WAND")) {
                    e.setCancelled(true);
                }
            }
        }
    }
    

    @EventHandler
    public void onTeleport(PlayerTeleportEvent e) {
        Player player = e.getPlayer();
        World world = e.getTo().getWorld();
        PlayerDataManager playerDataManager = rpg.getConnectListener().getPlayerDataInstance(player);
        com.xpkitty.rpgplugin.manager.MiscPlayerManager miscPlayerManager = new MiscPlayerManager();
        miscPlayerManager.testResourcePack(player,rpg,world,playerDataManager);
    }

    @EventHandler
    public void armourEquipEvent(ArmorEquipEvent e) {

        Player player = e.getPlayer();


        if(e.getNewArmorPiece() != null) {
            if(e.getNewArmorPiece().getItemMeta() != null) {
                ItemMeta itemMeta = e.getNewArmorPiece().getItemMeta();
                if(itemMeta.getLore()!=null) {
                    String armourType = (itemMeta.getLore()).get(0);
                    String armourTypeTranslated = (ChatColor.translateAlternateColorCodes('&',armourType));

                    int STR = com.xpkitty.rpgplugin.manager.MiscPlayerManager.getAbilityScore(rpg,player, AbilityScores.STR);
                    int CON = com.xpkitty.rpgplugin.manager.MiscPlayerManager.getAbilityScore(rpg,player, AbilityScores.CON);
                    int DEX = com.xpkitty.rpgplugin.manager.MiscPlayerManager.getAbilityScore(rpg,player, AbilityScores.DEX);

                    System.out.println("DEBUG: " + player.getName() + " STR, DEX, CON= " + STR + " " + DEX + " " + CON + " ; for " + player.getName());

                    if(armourTypeTranslated.equalsIgnoreCase(ChatColor.GRAY +"Medium Armour")) {
                        if(!(STR>= ArmourList.MEDIUM_ARMOUR.getStr() && CON>=ArmourList.MEDIUM_ARMOUR.getCon() && DEX>=ArmourList.MEDIUM_ARMOUR.getDex())) {
                            player.sendMessage(ChatColor.RED + "You currently cannot equip this armour");
                            e.setCancelled(true);
                        }
                    } else if(armourTypeTranslated.equalsIgnoreCase(ChatColor.GRAY +"Heavy Armour")) {
                        if(!(STR>=ArmourList.HEAVY_ARMOUR.getStr() && CON>=ArmourList.HEAVY_ARMOUR.getCon() && DEX>=ArmourList.HEAVY_ARMOUR.getDex())) {
                            player.sendMessage(ChatColor.RED + "You currently cannot equip this armour");
                            e.setCancelled(true);
                        }
                    } else if(armourTypeTranslated.equalsIgnoreCase(ChatColor.GRAY +"Light Armour")) {
                        if(!(STR>=ArmourList.LIGHT_ARMOUR.getStr() && CON>=ArmourList.LIGHT_ARMOUR.getCon() && DEX>=ArmourList.LIGHT_ARMOUR.getDex())) {
                            player.sendMessage(ChatColor.RED + "You currently cannot equip this armour");
                            e.setCancelled(true);
                        }
                    }
                }
            }
        }
    }
}
