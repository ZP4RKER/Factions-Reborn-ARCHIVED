package com.zp4rker.freborn.api;

import com.zp4rker.freborn.FactionsReborn;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class Vault {

    private Nexus nexus;
    private List<Location> vault = new ArrayList<>();
    private Plugin plugin = FactionsReborn.getPlugin();

    public Vault(Nexus nexus, Player player) {
        this.nexus = nexus;
        create(player);
    }

    public Vault(Nexus nexus) {
        this.nexus = nexus;
        setArea();
    }

    public List<Location> getVault() {
        return vault;
    }

    public Nexus getNexus() {
        return nexus;
    }

    public void create(Player player) {

        Location nexus = this.nexus.getLocation();

        if (!this.nexus.hasVault) {

            List<Location> wholeVault = new ArrayList<>();
            final List<Location> vault = new ArrayList<>();

            int maxX, maxZ, maxY, minX, minY, minZ;
            maxX = nexus.getBlockX() + 3;
            maxZ = nexus.getBlockZ() + 3;
            maxY = nexus.getBlockY() + 3;

            for (minX = nexus.getBlockX() - 3; minX <= maxX; minX++) {
                for (minY = nexus.getBlockY() - 1; minY <= maxY; minY++) {
                    for (minZ = nexus.getBlockZ() - 3; minZ <= maxZ; minZ++) {
                        wholeVault.add(new Location(nexus.getWorld(), minX, minY, minZ));
                    }
                }
            }

            for (Location loc : wholeVault) {
                vault.add(loc);
            }

            maxX -= 1;
            maxY -= 1;
            maxZ -= 1;

            for (minX = nexus.getBlockX() - 2; minX <= maxX; minX++) {
                for (minY = nexus.getBlockY(); minY <= maxY; minY++) {
                    for (minZ = nexus.getBlockZ() - 2; minZ <= maxZ; minZ++) {
                        vault.remove(new Location(nexus.getWorld(), minX, minY, minZ));
                    }
                }
            }

            // Door Ways
            vault.remove(nexus.clone().add(3, 0, 0));
            vault.remove(nexus.clone().add(3, 1, 0));
            vault.remove(nexus.clone().add(-3, 0, 0));
            vault.remove(nexus.clone().add(-3, 1, 0));
            vault.remove(nexus.clone().add(0, 0, 3));
            vault.remove(nexus.clone().add(0, 1, 3));
            vault.remove(nexus.clone().add(0, 0, -3));
            vault.remove(nexus.clone().add(0, 1, -3));

            for (int i = 0; i < vault.size(); i++) {
                final int I = i;
                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                    public void run() {
                        vault.get(I).getBlock().setType(Material.STONE);
                    }
                }, i * 1);
            }

            this.vault = wholeVault;

        }

    }

    public Base createBase(Player player) {
        return new Base(this, player);
    }

    public void setArea() {

        Location nexus = this.nexus.getLocation();

        List<Location> wholeVault = new ArrayList<>();

        int maxX, maxZ, maxY, minX, minY, minZ;
        maxX = nexus.getBlockX() + 3;
        maxZ = nexus.getBlockZ() + 3;
        maxY = nexus.getBlockY() + 3;

        for (minX = nexus.getBlockX() - 3; minX <= maxX; minX++) {
            for (minY = nexus.getBlockY() - 1; minY <= maxY; minY++) {
                for (minZ = nexus.getBlockZ() - 3; minZ <= maxZ; minZ++) {
                    wholeVault.add(new Location(nexus.getWorld(), minX, minY, minZ));
                }
            }
        }

        this.vault = wholeVault;

    }
}
