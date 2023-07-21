// 2023. Author: S.Frynas (XpKitty), e-mail: sebastian.frynas@outlook.com, licence: GNU GPL v3

package com.xpkitty.rpgplugin.manager.spells.spell_ui;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.data.player_data.PlayerSpellFile;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class SpellHotbarManager {
    Rpg rpg;

    HashMap<UUID, ArrayList<ItemStack>> items = new HashMap<>();
    HashMap<UUID, Integer> startSlot = new HashMap<>();

    public SpellHotbarManager(Rpg rpg) {
        this.rpg=rpg;
    }

    public Cache<UUID, Long> hotbarCooldown = CacheBuilder.newBuilder().expireAfterWrite(75, TimeUnit.MILLISECONDS).build();

    public HashMap<UUID, HotbarType> hotbarType = new HashMap<>();


    public void toggleHotbarStatus(Player player) {
        if(!hotbarCooldown.asMap().containsKey(player.getUniqueId())) {
            if (getCurrentHotbar(player).equals(HotbarType.DEFAULT)) {
                activateSpellHotbar(player, SpellHotbarManager.getCurrentSpellHotbarNumber(rpg, player));
            } else if (getCurrentHotbar(player).equals(HotbarType.SPELL_HOTBAR)) {
                deadctivateSpellHotbar(player);
            }
            hotbarCooldown.put(player.getUniqueId(), System.currentTimeMillis() + 75);
        }
    }



    public void fastActivateSpellHotbar(Player player, int hotbar) {

        if(getCurrentHotbar(player).equals(HotbarType.SPELL_HOTBAR)) {
            ItemStack wand = player.getInventory().getItemInMainHand();


            for (int i = 0; i < 9; i++) {
                player.getInventory().setItem(i, getItemFromSpellHotbarWithNoDisplayName(player, i, SpellHotbarManager.getCurrentSpellHotbarNumber(rpg, player)));
            }

            player.getInventory().setItem(8, wand);
            player.getInventory().setHeldItemSlot(8);
        }
    }

    public void activateSpellHotbar(Player player, int hotbar) {

        if(getCurrentHotbar(player).equals(HotbarType.DEFAULT)) {
            hotbarType.put(player.getUniqueId(), HotbarType.SPELL_HOTBAR);

            ArrayList<ItemStack> hotbarItems = new ArrayList<>();

            ItemStack wand = player.getInventory().getItemInMainHand();

            for (int i = 0; i < 9; i++) {
                hotbarItems.add(i, player.getInventory().getItem(i));
            }
            items.put(player.getUniqueId(), hotbarItems);
            startSlot.put(player.getUniqueId(), player.getInventory().getHeldItemSlot());

            for (int i = 0; i < 9; i++) {
                player.getInventory().setItem(i, getItemFromSpellHotbarWithNoDisplayName(player, i, SpellHotbarManager.getCurrentSpellHotbarNumber(rpg, player)));
            }

            player.getInventory().setItem(8, wand);
            player.getInventory().setHeldItemSlot(8);
        }
    }

    public void deadctivateSpellHotbar(Player player) {
        if(getCurrentHotbar(player).equals(HotbarType.SPELL_HOTBAR)) {
            ItemStack wand = player.getInventory().getItemInMainHand();

            int slot = 8;

            if (startSlot.containsKey(player.getUniqueId())) {
                slot = startSlot.get(player.getUniqueId());
            }

            if (getCurrentHotbar(player).equals(HotbarType.SPELL_HOTBAR)) {
                hotbarType.put(player.getUniqueId(), HotbarType.DEFAULT);

                ArrayList<ItemStack> itemList = items.get(player.getUniqueId());

                for (int i = 0; i < 9; i++) {

                    ItemStack is = itemList.get(i);
                    player.getInventory().setItem(i, is);

                }
            }
            if (slot > -1 && slot < 9) {
                player.getInventory().setHeldItemSlot(slot);
            }
            player.getInventory().setItemInMainHand(wand);
        }
    }




    public void hotBarUp(Player player) {
        if(getCurrentHotbar(player).equals(HotbarType.SPELL_HOTBAR)) {
            int hotBar = getCurrentSpellHotbarNumber(rpg, player);
            int hotBarCount = rpg.getConnectListener().getPlayerSpellFile(player).getHotbarCount();

            int newBar = hotBar + 1;

            if (newBar > hotBarCount) {
                newBar = 1;
            }
            rpg.getConnectListener().getPlayerSpellFile(player).setHotBarNum(newBar);

            fastActivateSpellHotbar(player,newBar);
            player.playSound(player.getLocation(), Sound.ITEM_BOOK_PAGE_TURN,1.0f,1.0f);

            //player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§b§lActivated hotbar §6§l" + newBar));
        }
    }

    public void hotBarDown(Player player) {
        if(getCurrentHotbar(player).equals(HotbarType.SPELL_HOTBAR)) {
            int hotBar = getCurrentSpellHotbarNumber(rpg, player);
            int hotBarCount = rpg.getConnectListener().getPlayerSpellFile(player).getHotbarCount();

            int newBar = hotBar - 1;

            if (newBar < 1) {
                newBar = hotBarCount;
            }
            rpg.getConnectListener().getPlayerSpellFile(player).setHotBarNum(newBar);

            player.playSound(player.getLocation(), Sound.ITEM_BOOK_PAGE_TURN,1.0f,1.0f);

            fastActivateSpellHotbar(player,newBar);
            //player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§b§lActivated hotbar §6§l" + newBar));
        }
    }




    public ItemStack getItemFromSpellHotbar(Player player, int slot, int hotbar) {
        PlayerSpellFile playerSpellFile = rpg.getConnectListener().getPlayerSpellFile(player);
        YamlConfiguration yamlConfiguration = playerSpellFile.getModifySpellFile();

        for(String element : yamlConfiguration.getConfigurationSection("spells").getKeys(false)) {
            String path = "spells." + element + ".";
            int bar = yamlConfiguration.getInt(path + "bar");

            if(bar==hotbar) {
                int barSlot = yamlConfiguration.getInt(path+"slot");

                if(barSlot == slot) {
                    String incantation = element.replace("_"," ");

                    ItemStack texture = SpellIcon.getSpellIconItem(rpg,incantation);
                    return texture;
                }
            }
        }

        ItemStack is = new ItemStack(Material.PEONY);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(" ");
        im.setCustomModelData(99);
        is.setItemMeta(im);

        return is;
    }


    public ItemStack getItemFromSpellHotbarWithWandDisplayName(Player player, int slot, int hotbar) {
        ItemStack item = getItemFromSpellHotbar(player,slot,hotbar);
        ItemMeta meta = item.getItemMeta();
        if(meta != null) {
            meta.setDisplayName(ChatColor.GOLD + "Wand");
            item.setItemMeta(meta);
        }

        return item;
    }


    public ItemStack getItemFromSpellHotbarWithNoDisplayName(Player player, int slot, int hotbar) {
        ItemStack item = getItemFromSpellHotbar(player,slot,hotbar);
        ItemMeta meta = item.getItemMeta();
        if(meta!=null) {
            meta.setDisplayName(" ");
            item.setItemMeta(meta);
        }
            return item;
    }


    public void removePlayer(Player player) {
        hotbarType.remove(player.getUniqueId());
    }

    public HotbarType getCurrentHotbar(Player player) {
        if(!hotbarType.containsKey(player.getUniqueId())) {
            hotbarType.put(player.getUniqueId(), HotbarType.DEFAULT);
        }

        return hotbarType.get(player.getUniqueId());
    }

    public static int getCurrentSpellHotbarNumber(Rpg rpg, Player player) {
        PlayerSpellFile playerSpellFile = rpg.getConnectListener().getPlayerSpellFile(player);

        return playerSpellFile.getActiveSpellHotbar();
    }

}
