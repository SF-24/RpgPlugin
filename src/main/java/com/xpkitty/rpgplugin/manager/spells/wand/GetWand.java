// 2023. Author: S.Frynas (XpKitty), e-mail: sebastian.frynas@outlook.com, licence: GNU GPL v3

package com.xpkitty.rpgplugin.manager.spells.wand;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GetWand {

    public static void GetWandBox(Player player) {
        ItemStack wandbox = new ItemStack(Material.MINECART);
        ItemMeta wandboxMeta = wandbox.getItemMeta();
        wandboxMeta.setDisplayName(ChatColor.WHITE + "Wandbox");
        wandboxMeta.setLore(Collections.singletonList(ChatColor.GRAY + "Right click when holding in main hand to open"));
        wandboxMeta.setCustomModelData(10);
        wandboxMeta.setLocalizedName("WANDBOX");
        wandbox.setItemMeta(wandboxMeta);

        player.getInventory().addItem(wandbox);
    }


    public static void GetWand(Player player, boolean ReplaceWandBoxInHand) {
        WandCore wandCore;
        WandWood wandWood;
        WandType wandType;
        ItemStack wandItem;


        //GENERATE WAND CORE
        wandCore = getRandomWandCore(1,1);


        // GENERATE WAND WOOD
        wandWood = getRandomWandWood();


        // GENERATE WAND TEXTURE
        wandType = getRandomWandTexture(wandWood);

        // CREATE WAND ITEM
        wandItem = new ItemStack(wandType.getMaterial());

        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.GOLD + wandCore.getDisplay() + " and " + wandWood.getDiplay());

        ItemMeta wandMeta = wandItem.getItemMeta();
        wandMeta.setDisplayName(ChatColor.GOLD + "Wand");
        wandMeta.setLore(lore);
        wandMeta.setLocalizedName("HP_WAND" + wandCore.getId());
        wandMeta.setCustomModelData(wandType.getCustomModelData());
        wandItem.setItemMeta(wandMeta);


        // GIVE WAND ITEM TO PLAYER
        if(ReplaceWandBoxInHand) {
            player.getInventory().setItemInMainHand(wandItem);
        } else {
            player.getInventory().addItem(wandItem);
        }
    }



    public static void GetCustomWand(Player player, boolean ReplaceWandBoxInHand, WandCore wandCore, WandWood wandWood, WandType wandTexture) {

        ItemStack wandItem = new ItemStack(wandTexture.getMaterial());

        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.GOLD + wandCore.getDisplay() + " and " + wandWood.getDiplay());

        ItemMeta wandMeta = wandItem.getItemMeta();
        wandMeta.setDisplayName(ChatColor.GOLD + "Wand");
        wandMeta.setLore(lore);
        wandMeta.setLocalizedName("HP_WAND" + wandCore.getId());
        wandMeta.setCustomModelData(wandTexture.getCustomModelData());
        wandItem.setItemMeta(wandMeta);


        if(ReplaceWandBoxInHand) {
            player.getInventory().setItemInMainHand(wandItem);
        } else {
            player.getInventory().addItem(wandItem);
        }
    }



    // CREATE RANDOM WAND CORE
    public static WandCore getRandomWandCore(int minRating, int maxRating) {
        Random random = new Random();
        WandCore wandCore;

        ArrayList<WandCore> cores = new ArrayList<>();

        wandCore = WandCore.UNICORN_HAIR;

        for(WandCore element : WandCore.values()) {
            if(element.getRating()>=minRating && element.getRating()<=maxRating) {
                cores.add(element);
            }
        }

        int coreNum = random.nextInt(cores.size()) + 1;
        int i = 0;

        for(WandCore element : cores) {
            i++;

            if(i==coreNum) {
                wandCore = element;
                break;
            }
        }

        return wandCore;
    }


    // CREATE RANDOM WAND WOOD
    public static WandWood getRandomWandWood() {
        int i;
        WandWood wandWood;
        Random random = new Random();

        ArrayList<WandWood> woods = new ArrayList<>();

        for(WandWood element : WandWood.values()) {
            for(i=0; i<element.getWeight(); i++) {
                woods.add(element);
            }
        }

        int wandWoodNum = random.nextInt(woods.size()-1);
        wandWood = woods.get(wandWoodNum);
        return wandWood;
    }


    // CREATE RANDOM WAND TEXTURE
    public static WandType getRandomWandTexture(WandWood wandWood) {
        WandType wandType;
        Random random = new Random();

        ArrayList<WandType> wandTextures = new ArrayList<>();

        for(WandType element : WandType.values()) {
            if(element.getLimitedWoodType() && element.getWood().contains(wandWood)) {
                for(int x = 0; x<element.getWeight(); x++)
                    wandTextures.add(element);
            } else if(!element.getLimitedWoodType()) {
                for(int x = 0; x<element.getWeight(); x++)
                    wandTextures.add(element);
            }
        }


        if(wandTextures.size()>=1) {
            int wandTypeNum = random.nextInt(wandTextures.size());
            wandType = wandTextures.get(wandTypeNum);
        } else {
            wandType = WandType.CURVED_BIRCH;
        }

        return wandType;
    }
}
