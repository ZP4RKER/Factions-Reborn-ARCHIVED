package com.zp4rker.freborn.api;

import com.zp4rker.core.Config;
import com.zp4rker.freborn.FactionsReborn;
import org.bukkit.Location;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class Nexus {

    private Location location;
    private EnderCrystal nexus;
    public boolean hasVault = false;

    public Nexus(Location location, boolean created) {
        this.location = location;
        if (!created) {
            Config config = FactionsReborn.getConfig("config");
            nexus = (EnderCrystal) location.getWorld().spawnEntity(location.clone().add(.5, .75, .5), EntityType.ENDER_CRYSTAL);
        } else {
            for (Entity entity : location.getWorld().getEntities()) {
                if (entity instanceof EnderCrystal && entity.getLocation().clone().subtract(.5, .75, .5) == location) {
                    nexus = (EnderCrystal) entity;
                }
            }
        }
    }

    public Location getLocation() {
        return location;
    }

    public Vault createVault(Player player) {
        return new Vault(this, player);
    }

    public EnderCrystal getNexus() {
        return nexus;
    }

}