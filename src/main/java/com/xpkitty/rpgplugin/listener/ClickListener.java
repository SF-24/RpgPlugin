// 2023. Author: S.Frynas (XpKitty), e-mail: sebastian.frynas@outlook.com, licence: GNU GPL v3

package com.xpkitty.rpgplugin.listener;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.MiscPlayerManager;
import com.xpkitty.rpgplugin.manager.data.player_data.PlayerDataManager;
import com.xpkitty.rpgplugin.manager.PlayerLevelManager;
import com.xpkitty.rpgplugin.manager.StringManager;
import com.xpkitty.rpgplugin.manager.item.PlayerHand;
import com.xpkitty.rpgplugin.manager.item.lightsaber.ExtendableWeaponManager;
import com.xpkitty.rpgplugin.manager.item.lightsaber.LightsaberManager;
import com.xpkitty.rpgplugin.manager.item.lightsaber.LightsaberSoundList;
import com.xpkitty.rpgplugin.manager.player_class.abilities.ClickType;
import com.xpkitty.rpgplugin.manager.spells.enum_list.SpellList;
import com.xpkitty.rpgplugin.manager.spells.spell_learning.SpellLearnManager;
import com.xpkitty.rpgplugin.manager.spells.spells_old.ColorLaserSpell.LaserSpellRed;
import com.xpkitty.rpgplugin.manager.spells.spells_old.CustomLaserSpell.LaserSpellCustom;
import com.xpkitty.rpgplugin.manager.spells.spells_old.FireballSpell;
import com.xpkitty.rpgplugin.manager.spells.spells_old.LaserSpell.LaserSpell;
import com.xpkitty.rpgplugin.manager.spells.hardcoded_hp_spells.StupefySpell.StupefySpell;
import com.xpkitty.rpgplugin.manager.spells.hardcoded_hp_spells.WingardiumLeviosaSpell.WingariumLeviosaSpell;
import com.xpkitty.rpgplugin.manager.spells.wand.GetWand;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_19_R2.entity.CraftCat;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class ClickListener implements Listener {

    private Cache<UUID, Long> cooldownSpawnStone = CacheBuilder.newBuilder().expireAfterWrite(30, TimeUnit.MINUTES).build();

    private Cache<UUID, Long> cooldownFireStaff = CacheBuilder.newBuilder().expireAfterWrite(2, TimeUnit.SECONDS).build();

    private Cache<UUID, Long> cooldownTeleportScroll = CacheBuilder.newBuilder().expireAfterWrite(3, TimeUnit.SECONDS).build();


    Rpg rpg;

    public ClickListener(Rpg rpg) {
        this.rpg = rpg;
    }

    @EventHandler
    public void onItemUse(PlayerInteractEvent e) {
        Player player = e.getPlayer();

        testAction(player, e);

        ConnectListener connectListener = rpg.getConnectListener();
        PlayerDataManager playerDataManager = connectListener.getPlayerDataInstance(player);
        YamlConfiguration yamlConfiguration = playerDataManager.getModifyYaml();
        String path = "skills.";
        String path2 = ".level";
        int mining = Integer.parseInt(yamlConfiguration.getString(path + "mining" + path2));
        int combat = Integer.parseInt(yamlConfiguration.getString(path + "combat" + path2));
        int trading = Integer.parseInt(yamlConfiguration.getString(path + "trading" + path2));
        int farming = Integer.parseInt(yamlConfiguration.getString(path + "farming" + path2));
        int building = Integer.parseInt(yamlConfiguration.getString(path + "building" + path2));
        int crafting = Integer.parseInt(yamlConfiguration.getString(path + "crafting" + path2));

        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
        if((itemInMainHand.getType() != Material.AIR) && itemInMainHand.getItemMeta() != null) {
            if(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
                if(itemInMainHand.getItemMeta().getLocalizedName().contains("HP_WAND")) {
                    int id = rpg.getConnectListener().getPlayerSpellFile(player).getActiveSpell();
                    SpellLearnManager.loadCastTest(rpg,player,id,true);
                }
            }

            if (e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getLocalizedName().equals("record_book") && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
                ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
                BookMeta bookMeta = (BookMeta) book.getItemMeta();
                bookMeta.setTitle("book");
                bookMeta.setAuthor("author");
                int average = Math.round((mining + combat + trading + farming + building + crafting) / 6);
                bookMeta.setPages(player.getName() + "\n\n" +
                        "average skill level: " + average + "\n\n" +
                        "mining " + mining + "%\n" +
                        "combat " + combat + "%\n" +
                        "trading " + trading + "%\n" +
                        "farming " + farming + "%\n" +
                        "building " + building + "%\n" +
                        "crafting " + crafting + "%\n");
                book.setItemMeta(bookMeta);
                ItemStack handItem = itemInMainHand;
                player.getInventory().setItemInMainHand(book);
                player.openBook(book);
                player.getInventory().setItemInMainHand(handItem);
                e.setCancelled(true);
            } else {

                if(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
                    boolean isLightsaberSound = false;
                    boolean isDarkSaberSound = false;

                    if(ExtendableWeaponManager.isExtendableWeapon(itemInMainHand) && ExtendableWeaponManager.isExtended(itemInMainHand)) {
                        if (ExtendableWeaponManager.getType(itemInMainHand).isDarksaberSound()) {
                            isDarkSaberSound=true;
                        } else if(ExtendableWeaponManager.getType(itemInMainHand).isLightsaberSound()) {
                            isLightsaberSound=true;
                        }
                    }
                    if(LightsaberManager.isLightsaber(itemInMainHand) && LightsaberManager.isExtended(itemInMainHand)) {
                        isLightsaberSound=true;
                    }

                    if(isDarkSaberSound) {
                        LightsaberManager.playSound(player, LightsaberSoundList.DARKSABER_SWING,ExtendableWeaponManager.getType(itemInMainHand).getSoundPitch());
                    } else if(isLightsaberSound) {
                        LightsaberManager.playSound(player, LightsaberSoundList.SABER_SWING, 1.0f);
                    }
                }

                if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {
                    if(e.getItem()!=null) {

                        if(ExtendableWeaponManager.isExtendableWeapon(itemInMainHand) && MiscPlayerManager.getLightsaberRightClickToggleSetting(rpg,player)) {
                            ExtendableWeaponManager.toggleFoldingWeapon(rpg, player, PlayerHand.MAIN_HAND);
                            e.setCancelled(true);
                        }
                        if(LightsaberManager.isLightsaber(itemInMainHand) && MiscPlayerManager.getLightsaberRightClickToggleSetting(rpg,player)) {
                            LightsaberManager.toggleLightsaber(rpg, player, PlayerHand.MAIN_HAND);
                            e.setCancelled(true);
                        }

                        if(itemInMainHand.getItemMeta().getLocalizedName().equalsIgnoreCase("SPELL_FIREBALL")) {
                            //new SpellManager().launch(player,1.0f,10,Fireball.class,rpg,6.50f,1);
                            new FireballSpell(rpg, SpellList.FIREBALL,player.getUniqueId());
                        } else if(itemInMainHand.getItemMeta().getLocalizedName().equalsIgnoreCase("SPELL_STUPEFY")) {
                            //new SpellManager().launch(player,1.0f,10,Fireball.class,rpg,6.50f,1);
                            new StupefySpell(rpg,SpellList.STUPEFY,player.getUniqueId(),player.getLocation().getDirection().normalize());
                        } else if(itemInMainHand.getItemMeta().getLocalizedName().equalsIgnoreCase("SPELL_WINGARDIUM")) {
                            //new SpellManager().launch(player,1.0f,10,Fireball.class,rpg,6.50f,1);
                            new WingariumLeviosaSpell(rpg,SpellList.WINGARDIUM_LEVIOSA,player.getUniqueId(),player.getLocation().getDirection().normalize());
                        } else if(itemInMainHand.getItemMeta().getLocalizedName().equalsIgnoreCase("gun")) {
                        new LaserSpell(rpg,SpellList.STUPEFY,player.getUniqueId(),10f);
                        e.setCancelled(true);
                        } else if(itemInMainHand.getItemMeta().getLocalizedName().equalsIgnoreCase("gun1")) {
                            new LaserSpellRed(rpg,SpellList.STUPEFY,player.getUniqueId(),10f);
                            e.setCancelled(true);
                        } else if(itemInMainHand.getItemMeta().getLocalizedName().equalsIgnoreCase("gun2")) {
                            new LaserSpell(rpg,SpellList.STUPEFY,player.getUniqueId(),5f);
                            e.setCancelled(true);
                        } else if(itemInMainHand.getItemMeta().getLocalizedName().equalsIgnoreCase("gun3")) {
                            new LaserSpellRed(rpg,SpellList.STUPEFY,player.getUniqueId(),5f);
                            e.setCancelled(true);
                        } else if(itemInMainHand.getItemMeta().getLocalizedName().equalsIgnoreCase("gun4")) {
                            new LaserSpell(rpg,SpellList.STUPEFY,player.getUniqueId(),7.5f);
                            e.setCancelled(true);
                        } else if(itemInMainHand.getItemMeta().getLocalizedName().equalsIgnoreCase("gun5")) {
                            new LaserSpellRed(rpg,SpellList.STUPEFY,player.getUniqueId(),7.5f);
                            e.setCancelled(true);
                        } else if(itemInMainHand.getItemMeta().getLocalizedName().equalsIgnoreCase("gun6")) {
                            new LaserSpell(rpg,SpellList.STUPEFY,player.getUniqueId(),15f);
                            e.setCancelled(true);
                        } else if(itemInMainHand.getItemMeta().getLocalizedName().equalsIgnoreCase("gun7")) {
                            new LaserSpellRed(rpg,SpellList.STUPEFY,player.getUniqueId(),15f);
                            e.setCancelled(true);
                        } else if(itemInMainHand.getItemMeta().getLocalizedName().equalsIgnoreCase("gun8")) {
                            new LaserSpell(rpg,SpellList.STUPEFY,player.getUniqueId(),3f);
                            e.setCancelled(true);
                        } else if(itemInMainHand.getItemMeta().getLocalizedName().equalsIgnoreCase("gun9")) {
                            new LaserSpellRed(rpg,SpellList.STUPEFY,player.getUniqueId(),3f);
                            e.setCancelled(true);
                        } else if(itemInMainHand.getItemMeta().getLocalizedName().equalsIgnoreCase("WANDBOX")) {
                            GetWand.GetWand(player,true);
                            e.setCancelled(true);

                        } else if(itemInMainHand.getItemMeta().getLocalizedName().contains("HP_WAND")) {
                            player.playSound(player.getLocation(), Sound.UI_TOAST_IN, 1.0F, 1.0F);
                            rpg.getSpellHotbarManager().toggleHotbarStatus(player);
                            e.setCancelled(true);

                        } else if(itemInMainHand.getItemMeta().getLocalizedName().contains("blaster")) {
                            String localizedName = itemInMainHand.getItemMeta().getLocalizedName();
                            String start = localizedName.substring(0,7);
                            e.setCancelled(true);
                            int particleSize=2;

                            if(localizedName.length()>16) {
                                if(localizedName.length() >= 20) {
                                    particleSize = Integer.parseInt(localizedName.substring(18,20));
                                    //player.sendMessage(String.valueOf(particleSize));
                                }
                            }

                            if(start.equalsIgnoreCase("blaster")) {
                                Integer num = Integer.valueOf(localizedName.substring(7,9));
                                String colorString = localizedName.substring(9,18);

                                int r = Integer.parseInt(colorString.substring(0,3));
                                int g = Integer.parseInt(colorString.substring(3,6));
                                int b = Integer.parseInt(colorString.substring(6,9));

                                String volumeString = "5.0";
                                String pitchString = "1.0";
                                String sound = "minecraft:blaster.blasterpistol";

                                if(localizedName.length()>21) {
                                    volumeString = localizedName.substring(20,22);
                                } else {

                                }
                                if(localizedName.length()>24) {
                                    pitchString = localizedName.substring(22,25);
                                }
                                if(localizedName.length()>25) {
                                    sound = localizedName.substring(25);
                                }

                                Color colour = Color.fromRGB(r,g,b);

                                if(colour != null) {
                                    e.setCancelled(true);
                                    new LaserSpellCustom(rpg,SpellList.STUPEFY,player.getUniqueId(),num,colour,particleSize,Float.parseFloat(volumeString),Float.parseFloat(pitchString),sound);
                                }
                            }
                        }

                        if(e.getClickedBlock()!=null) {
                            if(((itemInMainHand.getType().equals(Material.MINECART) && itemInMainHand.getItemMeta().getLocalizedName().contains("HP_WAND")))
                                    && e.getClickedBlock().getType().toString().contains("RAIL")) {
                                e.setCancelled(true);
                            } if(((itemInMainHand.getType().equals(Material.MINECART) && itemInMainHand.getItemMeta().getLocalizedName().contains("CARD")))
                                    && e.getClickedBlock().getType().toString().contains("RAIL")) {
                                e.setCancelled(true);
                            } if(((itemInMainHand.getType().equals(Material.MINECART) && itemInMainHand.getItemMeta().getLocalizedName().contains("FROG")))
                                    && e.getClickedBlock().getType().toString().contains("RAIL")) {
                                e.setCancelled(true);
                            } else if((itemInMainHand.getType().equals(Material.LAVA_BUCKET) && itemInMainHand.getItemMeta().getLocalizedName().contains("HP_WAND"))) {
                                e.setCancelled(true);
                            }
                        }



                        switch (e.getItem().getType()) {
                            case NETHERITE_INGOT:
                                ItemMeta handItemMeta = itemInMainHand.getItemMeta();

                                if(handItemMeta.getLocalizedName().contains("spawn_stone")) {

                                    if(!cooldownSpawnStone.asMap().containsKey(player.getUniqueId())) {
                                        player.sendMessage(ChatColor.AQUA + "You have been transported to your spawn point");
                                        new MiscPlayerManager().teleportPlayerToSpawn(player,rpg);
                                        cooldownSpawnStone.put(player.getUniqueId(), System.currentTimeMillis() + 1800000);

                                    } else {
                                        long distance = cooldownSpawnStone.asMap().get(player.getUniqueId()) - System.currentTimeMillis();
                                        player.sendMessage(ChatColor.RED + "You must wait " + TimeUnit.MILLISECONDS.toMinutes(distance) + " minutes to use this");

                                    }
                                }

                            case BOOK:
                                String localizedName = e.getItem().getItemMeta().getLocalizedName();
                                if (localizedName.contains("tp_scroll_")) {
                                    if(!cooldownTeleportScroll.asMap().containsKey(player.getUniqueId())) {
                                        cooldownTeleportScroll.put(player.getUniqueId(),System.currentTimeMillis() + 3000);

                                        String locationString = localizedName.substring(10);
                                        FileConfiguration fileConfiguration = rpg.getConfig();
                                        String dataString = "teleport_locations." + locationString + ".";

                                        if(player.hasPermission("rpgpl.debug")) {
                                            new StringManager().debugMessage(player, "Location string: " + locationString + ", data string: " + dataString);
                                        }

                                        if (rpg.getConfig().contains("teleport_locations." + locationString)) {
                                            if (!fileConfiguration.contains(dataString + "level")) {
                                                MiscPlayerManager miscPlayerManager = new MiscPlayerManager();
                                                miscPlayerManager.teleportPlayer(rpg,player, dataString, fileConfiguration,true,true);

                                            } else {
                                                int requiredLevel = fileConfiguration.getInt(dataString + "level");
                                                PlayerLevelManager playerLevelManager = new PlayerLevelManager(rpg);
                                                if (playerLevelManager.getPlayerLevel(player) >= requiredLevel) {
                                                    MiscPlayerManager miscPlayerManager = new MiscPlayerManager();
                                                    miscPlayerManager.teleportPlayer(rpg,player, dataString, fileConfiguration,true,true);
                                                } else {
                                                    player.sendMessage(ChatColor.RED + "You do not meet the level requirement to use this item.");
                                                }
                                            }

                                        } else {
                                            player.sendMessage(ChatColor.RED + "Cannot teleport to " + locationString + " because it does not exist.");
                                        }
                                    }
                                } else if(localizedName.contains("SPELLSCROLL")) {
                                    String spellName = String.valueOf(localizedName.substring(11));

                                    for(SpellList spell : SpellList.values()) {
                                        if(spell.name().equalsIgnoreCase(spellName)) {
                                            MiscPlayerManager.learnSpell(player,rpg,spell,true);
                                        }
                                    }
                                }
                                break;
                                default:
                                return;
                        }
                    }

                }
            }

        }




    }

    private void testAction(Player player, PlayerInteractEvent e) {

        if(MiscPlayerManager.getSneakAbilitySetting(rpg,player)) {
            Action action = e.getAction();

            if (player.isSneaking() && !player.getInventory().getItemInMainHand().equals(Material.AIR) && player.getInventory().getItemInMainHand().getItemMeta() != null && (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE)) {

                if ((!player.getInventory().getItemInMainHand().getItemMeta().getLocalizedName().contains("WAND")) &&
                        (!player.getInventory().getItemInMainHand().getItemMeta().getLocalizedName().contains("gun")) &&
                        (!player.getInventory().getItemInMainHand().getItemMeta().getLocalizedName().contains("GUN")) &&
                        (!player.getInventory().getItemInMainHand().getItemMeta().getLocalizedName().contains("spawn_stone")) &&
                        (!player.getInventory().getItemInMainHand().getItemMeta().getLocalizedName().contains("tp_scroll_")) &&
                        (!player.getInventory().getItemInMainHand().getItemMeta().getLocalizedName().contains("SPELLSCROLL")) &&
                        (!player.getInventory().getItemInMainHand().getItemMeta().getLocalizedName().contains("spellscroll")) &&
                        (!player.getInventory().getItemInMainHand().getItemMeta().getLocalizedName().contains("SPELL")) &&
                        (!player.getInventory().getItemInMainHand().getItemMeta().getLocalizedName().contains("spell")) &&
                        (!player.getInventory().getItemInMainHand().getItemMeta().getLocalizedName().contains("card")) &&
                        (!player.getInventory().getItemInMainHand().getItemMeta().getLocalizedName().contains("frog")) &&
                                (!player.getInventory().getItemInMainHand().getItemMeta().getLocalizedName().contains("blaster")) &&
                !ExtendableWeaponManager.isExtendableWeapon(player.getInventory().getItemInMainHand()) &&
                !LightsaberManager.isLightsaber(player.getInventory().getItemInMainHand())) {


                    boolean canClick = false;

                    if (rpg.getClickManager().getKeys(player).size() > 0 && rpg.getClickManager().getKeys(player) != null) {
                        if (rpg.getClickManager().getKeys(player).get(0) == ClickType.RIGHT) {
                            canClick = true;
                        } else if (rpg.getClickManager().getKeys(player).get(0) == ClickType.LEFT) {
                            canClick = true;
                        }
                    }

                    if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
                        if (canClick) {
                            rpg.getClickManager().rightClick(player);
                        } else if (!canClick) {
                            if (!(player.getInventory().getItemInMainHand().getType().equals(Material.BOW)) && !(player.getInventory().getItemInMainHand().getType().equals(Material.CROSSBOW))) {
                                rpg.getClickManager().rightClick(player);
                            }
                        } else {
                            player.sendMessage(ChatColor.RED + "ERROR! canClick cannot be true and false at the same time");
                        }
                    }
                    if (action == Action.LEFT_CLICK_BLOCK || action == Action.LEFT_CLICK_AIR) {
                        rpg.getClickManager().leftClick(player);
                    }

                }
            }
        }
    }

    private void saveYaml(Player player) {
        try {
            rpg.getConnectListener().getPlayerDataInstance(player).getModifyYaml().save(rpg.getConnectListener().getPlayerDataInstance(player).getYamlFile());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @EventHandler
    public void onEntityClick(PlayerInteractEntityEvent e) {
        //e.getPlayer().sendMessage("Click!");
        if(e.getRightClicked().getType() == EntityType.CAT) {
            //e.getPlayer().sendMessage("Click2!");
            if(e.getPlayer().getInventory().getItemInMainHand().getType() == Material.POTION && e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getLocalizedName().equalsIgnoreCase("cat_medicine")) {
                //e.getPlayer().sendMessage("Click3!");
                e.getPlayer().getInventory().setItemInMainHand(new ItemStack(Material.GLASS_BOTTLE));
                CraftCat cat = (CraftCat) e.getRightClicked();
                e.getRightClicked().setGlowing(false);
                cat.setGlowing(false);

                cat.setSilent(false);
                cat.setSitting(false);
            }

        }
    }
}
