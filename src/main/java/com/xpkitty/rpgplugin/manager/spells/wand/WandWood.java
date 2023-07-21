// 2023. Author: S.Frynas (XpKitty), e-mail: sebastian.frynas@outlook.com, licence: GNU GPL v3

package com.xpkitty.rpgplugin.manager.spells.wand;

public enum WandWood {

    ACACIA("Acacia", 5),
    ALDER("Alder", 5),
    APPLE("Apple", 5),
    ASH("Ash", 5),
    ASPEN("Aspen", 5),
    BEECH("Beech", 5),
    BLACKTHORN("Blackthorn", 5),
    BLACK_WALNUT("Black Walnut", 5),
    CEDAR("Cedar", 5),
    CHERRY("Cherry", 5),
    CYPRESS("Cypress", 5),
    DOGWOOD("Dogwood", 5),
    EBONY("Ebony", 5),
    ENGLISH_OAK("English Oak", 5),
    ELDER("Elder", 1),
    ELM("Elm", 5),
    FIR("Fir", 5),
    HAWTHORN("Hawthorn", 5),
    HAZEL("Hazel", 5),
    HOLLY("Holly", 5),
    HORNBEAM("Hornbeam", 5),
    LARCH("Larch", 5),
    LAUREL("Laurel", 5),
    MAPLE("Maple", 5),
    PEAR("Pear", 5),
    PINE("Pine", 5),
    POPLAR("Poplar", 5),
    RED_OAK("Red Oak", 5),
    REDWOOD("Redwood", 5),
    REED("Reed", 5),
    ROSEWOOD("Rosewood", 5),
    ROWAN("Rowan", 5),
    SILVER_LIME("Silver Lime", 5),
    SPRUCE("Spruce", 5),
    SNAKEWOOD("Snakewood", 5),
    SUGAR_MAPLE("Sugar Maple", 5),
    SYCAMORE("Sycamore", 5),
    TAMARACK("Tamarack", 5),
    VINE("Vine", 5),
    WALNUT("Walnut", 5),
    WILLOW("Willow", 5),
    YEW("Yew", 5);

    String diplay;
    int weight;

    WandWood(String display, int weight) {
        this.diplay = display;
        this.weight = weight;
    }

    public String getDiplay() { return diplay; }
    public int getWeight() { return weight; }
}
