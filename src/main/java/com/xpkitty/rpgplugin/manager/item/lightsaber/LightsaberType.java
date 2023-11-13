// 2023. Author: S.Frynas (XpKitty), e-mail: sebastian.frynas@outlook.com, licence: GNU GPL v3

package com.xpkitty.rpgplugin.manager.item.lightsaber;

public enum LightsaberType {
    SINGLE(1,1,1.75f,false),
    DOUBLE(1,1,1.75f,true),
    CROSSGUARD(1.5f,1.5f,1,false);

    private final float baseKnockback;
    private final float basePower;
    private final float baseSpeed;
    private final boolean slashing;

    LightsaberType(float baseKnockback, float basePower, float baseSpeed, boolean slashing) {
        this.baseKnockback=baseKnockback;
        this.basePower=basePower;
        this.baseSpeed=baseSpeed;
        this.slashing=slashing;
    }

    public boolean isSlashing() {return slashing;}
    public float getBaseKnockback() {return baseKnockback;}
    public float getBasePower() {return basePower;}
    public float getBaseSpeed() {return baseSpeed;}
}
