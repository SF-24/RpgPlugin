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
package com.xpkitty.rpgplugin.manager.item;

import java.util.ArrayList;
import java.util.Arrays;

public enum ChocolateFrogCard {

    ALBERIC_GRUINNION(112, "Alberic Grunnion",
            new ArrayList<>(Arrays.asList(
                    "Lived 1803-1882",
                    "Inventor of the Dungbomb"))
            , ChocolateFrogCardType.SILVER, -1),

    BERTIE_BOTT(111, "Bertie Bott",
            new ArrayList<>(Arrays.asList(
                    "Inventor of Bertie Bott’s", "Every-Flavour Beans"))
            , ChocolateFrogCardType.GOLD, -1),

    DUMBLEDORE(110, "Albus Dumbledore",
            new ArrayList<>(Arrays.asList(
                    "Currently Headmaster of Hogwarts.",
                    "Considered by many the greatest",
                    "wizard of modern times,",
                    "",
                    "Professor Dumbledore is particularly",
                    "famous for his defeat",
                    "of the dark wizard Grindelwald in 1945,",
                    "for the discovery of the twelve uses of",
                    "dragon’s blood and his work on alchemy",
                    "with his partner, Nicolas Flamel.",
                    "",
                    "Professor Dumbledore enjoys chamber music",
                    "and tenpin bowling"))
            , ChocolateFrogCardType.GOLD, 101);

    final int modelData, number;
    final String display;
    final ArrayList<String> lore;
    final ChocolateFrogCardType type;

    ChocolateFrogCard(int modelData, String display, ArrayList<String> lore, ChocolateFrogCardType type, int number) {
        this.modelData=modelData;
        this.display=display;
        this.lore=lore;
        this.type=type;
        this.number=number;
    }

    public int getModelData() { return modelData; }
    public String getDisplay() { return display; }
    public ArrayList<String> getLore() { return lore; }
    public ChocolateFrogCardType getType() {
        return type;
    }
    public int getNumber() {
        return number;
    }

}
