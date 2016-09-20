package com.zp4rker.freborn.api;

import com.zp4rker.freborn.FactionsReborn;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class Base {

    private Vault vault;
    private List<Location> area = new ArrayList<>();
    private Plugin plugin = FactionsReborn.getPlugin();

    public Base(Vault vault, Player player) {
        this.vault = vault;
        create(player);
    }

    public Base(Vault vault, int players) {
        this.vault = vault;
        setArea(players);
    }

    public List<Location> getArea() {
        return area;
    }

    public Vault getVault() {
        return vault;
    }

    public Nexus getNexus() {
        return vault.getNexus();
    }

    public void create(Player player) {

        // Get Point One
        Location pointOne = getVault().getNexus().getLocation()
                .clone().add(6, -1, 6);
        // Get Point Two
        Location pointTwo = getVault().getNexus().getLocation()
                .clone().subtract(6, 1, 6);

        List<Location> outline = new ArrayList<>();

        // Loop through X of pointTwo to pointOne
        for (int x = pointTwo.getBlockX(); x <= pointOne.getBlockX(); x++) {

            int y = pointTwo.getBlockY();
            int z = pointTwo.getBlockZ();
            World world = pointTwo.getWorld();

            // Add the location to outline
            outline.add(new Location(world, x, y, z));

        }
        // Loop through X of pointOne to pointTwo
        for (int x = pointOne.getBlockX(); x >= pointTwo.getBlockX(); x--) {

            int y = pointOne.getBlockY();
            int z = pointOne.getBlockZ();
            World world = pointOne.getWorld();

            // Add the location to outline
            outline.add(new Location(world, x, y, z));

        }

        // Loop through Z of pointTwo to pointOne
        for (int z = pointTwo.getBlockZ(); z <= pointOne.getBlockZ(); z++) {

            int x = pointTwo.getBlockX();
            int y = pointTwo.getBlockY();
            World world = pointTwo.getWorld();

            // Add the location to outline
            outline.add(new Location(world, x, y, z));

        }
        // Loop through Z of pointOne to pointTwo
        for (int z = pointOne.getBlockZ(); z >= pointTwo.getBlockZ(); z--) {

            int x = pointOne.getBlockX();
            int y = pointOne.getBlockY();
            World world = pointOne.getWorld();

            // Add the location to outline
            outline.add(new Location(world, x, y, z));

        }

        // Loop through each block in outline
        for (int i = 0; i < outline.size(); i++) {
            final int I = i;
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                public void run() {
                    // Set each block to stone
                    outline.get(I).getBlock().setType(Material.STONE);
                }
            }, (i == 0 ? getVault().getVault().size() : 0) + i * 1);
        }

        List<Location> area = new ArrayList<>();

        // Set Y for pointOne and pointTwo
        pointOne.add(0, 12, 0);
        pointTwo.subtract(0, 12, 0);

        // Loop through all blocks in between pointOne and pointTwo
        for (int x = pointTwo.getBlockX(); x <= pointOne.getBlockX(); x++) {
            for (int y = pointTwo.getBlockY(); y <= pointOne.getBlockY(); y++) {
                for (int z = pointTwo.getBlockZ(); z <= pointOne.getBlockZ(); z++) {
                    // Add the location to area
                    area.add(new Location(pointOne.getWorld(), x, y, z));
                }
            }
        }

        // Set Base's area to local variable area
        this.area = area;

    }

    public void setArea(int players) {

        // Get Point One
        Location pointOne = getNexus().getLocation().clone().subtract((2 * players) + 4, (2 * players) + 4, (2 * players) + 4);
        // Get Point Two
        Location pointTwo = getNexus().getLocation().clone().add((2 * players) + 4, (2 * players) + 4, (2 * players) + 4);

        // Loop through all blocks between the points, including the points
        for (int x = pointOne.getBlockX(); x <= pointTwo.getBlockX(); x++) {
            for (int y = pointOne.getBlockY(); y <= pointTwo.getBlockY(); y++) {
                for (int z = pointOne.getBlockZ(); z <= pointTwo.getBlockZ(); z++) {
                    area.add(new Location(pointOne.getWorld(), x, y, z));
                }
            }
        }

    }

    public List<Location> getOutline(int players) {

        List<Location> list = new ArrayList<>();
        // Get Point One
        Location pointOne = vault.getNexus().getLocation().clone()
                .subtract((2 * players) + 4, 1, (2 * players) + 4);
        // Get Point Two
        Location pointTwo = vault.getNexus().getLocation().clone()
                .add((2 * players) + 4, -1, (2 * players) + 4);

        // Loop through all blocks between the points, including the points
        for (int x = pointOne.getBlockX(); x <= pointTwo.getBlockX(); x++) {
            for (int y = pointOne.getBlockY(); y <= pointTwo.getBlockY(); y++) {
                for (int z = pointOne.getBlockZ(); z <= pointTwo.getBlockZ(); z++) {
                    list.add(new Location(pointOne.getWorld(), x, y, z));
                }
            }
        }

        return list;

    }

}
