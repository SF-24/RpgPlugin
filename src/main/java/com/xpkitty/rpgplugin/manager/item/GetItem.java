package com.xpkitty.rpgplugin.manager.item;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GetItem {

    public static ItemStack getChocolateFrogCard(ArrayList<ChocolateFrogCardType> types) {

        ItemStack itemStack = new ItemStack(Material.MINECART);
        ItemMeta itemMeta = itemStack.getItemMeta();

        ArrayList<ChocolateFrogCard> cards = new ArrayList<>();

        if(types!=null && !types.equals(new ArrayList<>())) {
            for(ChocolateFrogCard card : ChocolateFrogCard.values()) {
                if(types.contains(card.getType())) {
                    cards.add(card);
                }
            }
        }

        if(cards.size()<=0) {
            cards.addAll(Arrays.asList(ChocolateFrogCard.values()));
        }

        int cardValue = new Random().nextInt(cards.size());

        ChocolateFrogCard card = cards.get(cardValue);
        ArrayList<String> lore = new ArrayList<>();

        ArrayList<String> card_lore = card.getLore();

        card_lore.forEach((n) -> lore.add(ChatColor.GRAY+n));

        itemMeta.setDisplayName(ChatColor.WHITE + card.getDisplay());
        itemMeta.setLore(lore);
        itemMeta.setLocalizedName("CARD");
        itemMeta.setCustomModelData(card.getModelData());

        itemStack.setItemMeta(itemMeta);


        return itemStack;
    }

}
