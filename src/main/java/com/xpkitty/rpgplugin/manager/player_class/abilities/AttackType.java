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
