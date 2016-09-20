package com.zp4rker.freborn.listeners;

import com.zp4rker.freborn.api.Faction;
import com.zp4rker.freborn.events.EnterFactionEvent;
import com.zp4rker.freborn.FactionsReborn;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {

    FactionsReborn plugin;

    public PlayerMoveListener(FactionsReborn plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {

        Player player = e.getPlayer();

        if (plugin.m.inFactionBase(player.getLocation().getBlock().getLocation())) {
            Faction faction = plugin.m.getFactionAtLocation(player.getLocation().getBlock().getLocation());
            if (!plugin.v.inBase.contains(player)) {

                // Create Event
                EnterFactionEvent event = new EnterFactionEvent(player, faction);
                // Call Event
                plugin.getServer().getPluginManager().callEvent(event);
                // Run Default Code
                if (!event.isCancelled()) {

                    event.getPlayer().sendMessage(event.getMessage());

                }

                plugin.v.inBase.add(player);

            }
        } else {
            if (plugin.v.inBase.contains(player)) {
                plugin.v.inBase.remove(player);
            }
        }

    }

}
