package com.zp4rker.freborn.listeners;

import java.util.ArrayList;
import java.util.List;

import com.zp4rker.freborn.FactionsReborn;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import com.zp4rker.freborn.api.Base;
import com.zp4rker.freborn.api.Faction;
import com.zp4rker.freborn.api.Nexus;
import com.zp4rker.freborn.api.Vault;
import com.zp4rker.freborn.events.CreateFactionEvent;

public class BlockPlaceListener implements Listener {

    private FactionsReborn plugin;

    public BlockPlaceListener(FactionsReborn plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
    	//Check if the player is holding a nexus
        if (isNexus(e.getBlock(), e.getPlayer())) {
        	// Get the location of the placed block
            Location block = e.getBlock().getLocation();
            // Get the player
            Player player = e.getPlayer();
            // Get the name of the faction to create
            String name = plugin.v.makingFaction.get(player).keySet().toArray()[0].toString();
            // Get the tag of the faction to create
            String tag = plugin.v.makingFaction.get(player).get(name);

            // Make Event
            CreateFactionEvent event = new CreateFactionEvent(name, tag, player, block);
            // Call Event
            plugin.getServer().getPluginManager().callEvent(event);

            // Run Default Code
            if (!event.isCancelled()) {
            	// Check if there is a faction within 9 blocks
                if (plugin.m.getNearestFaction(player, 9) != null) {
                    player.sendMessage("§4You cannot create a faction here!");
                    return;
                }

                // Remove Block
                block.getBlock().setType(Material.AIR);
                // Make Nexus
                Nexus nexus = new Nexus(block, false);
                // Make Vault
                Vault vault = nexus.createVault(e.getPlayer());
                // Make Base
                Base base = vault.createBase(e.getPlayer());
                List<String> players = new ArrayList<>();
                players.add(player.getUniqueId().toString());
                // Make Faction
                Faction faction = new Faction(event.getName(), event.getTag(), players, base, players, true);
                // Add to config file
                plugin.m.createFaction(faction);
                // Send Message to console
                plugin.getLogger().info("Added Faction " + faction.getName() + " to database!");
                // Send Message to player
                player.sendMessage(event.getMessage());
                // Remove player from ArrayList
                plugin.v.makingFaction.remove(player);

            }

        }

    }

    public boolean isNexus(Block block, Player player) {
        return (block.getType() == Material.EMERALD_BLOCK &&
                player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§5Nexus"));
    }

}