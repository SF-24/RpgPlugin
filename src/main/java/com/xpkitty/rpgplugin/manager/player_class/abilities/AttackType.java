// 2023. Author: S.Frynas (XpKitty), e-mail: sebastian.frynas@outlook.com, licence: GNU GPL v3

package com.xpkitty.rpgplugin.manager.player_class.abilities;

import org.bukkit.Particle;

public enum AttackType {

    REND(true,2.0f,0.0f,1.0f,1, Particle.REDSTONE),
    ANVIL_STRIKE(true,2.0f,3.0f,0.0f,0, Particle.CRIT),
    SAP(true,1.25f,1.0f,2.0f,2, Particle.ASH);

    Particle particle;
    float damageMultiplier, knockbackPower, stunTime;
    int stunWeakenPower;
    boolean autoCrit;

    AttackType(boolean autoCrit, float damageMultiplier, float knockbackPower, float stunTime, int stunWeakenPower, Particle particle) {
        this.damageMultiplier = damageMultiplier;
        this.knockbackPower = knockbackPower;
        this.stunTime = stunTime;
        this.stunWeakenPower = stunWeakenPower;
        this.particle = particle;
        this.autoCrit = autoCrit;
    }

    public float getDamageMultiplier() {return damageMultiplier;}
    public float getKnockbackPower() {return knockbackPower;}
    public float getStunTime() {return stunTime;}
    public int getStunWeakenPower() {return stunWeakenPower;}
    public Particle getParticle() {return particle;}
    public boolean getAutoCrit() {return autoCrit;}
}
