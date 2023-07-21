// 2023. Author: S.Frynas (XpKitty), e-mail: sebastian.frynas@outlook.com, licence: GNU GPL v3

package com.xpkitty.rpgplugin.manager;

import java.util.Random;

public class DiceRollManager {

    public static int getSpellDiceRoll() {
        Random random = new Random();
        int n = random.nextInt(10)+1;
        int m = random.nextInt(10)+1;

        return n+m;
    }

    public static int getSpellLearnDiceRoll() {
        Random random = new Random();
        int n = random.nextInt(20)+1;
        return n;
    }

}
