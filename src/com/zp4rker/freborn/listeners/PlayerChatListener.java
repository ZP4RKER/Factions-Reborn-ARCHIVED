package com.zp4rker.freborn.listeners;

import java.util.UUID;

import com.zp4rker.freborn.FactionsReborn;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.zp4rker.freborn.api.Faction;

public class PlayerChatListener implements Listener {

    private FactionsReborn plugin;

    public PlayerChatListener(FactionsReborn plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {

        Player player = event.getPlayer();

        if (plugin.m.isInFaction(player)) {
            String prefix = "§2[" + plugin.m.getFaction(player).getName() + "]";

            event.setFormat(prefix + event.getFormat());

            if (plugin.v.factionChat.contains(player)) {

                event.setCancelled(true);

                Faction faction = plugin.m.getFaction(player);

                for (String uuid : faction.getPlayers()) {

                    Player target = Bukkit.getPlayer(UUID.fromString(uuid));

                    if (target != null) {

                        target.sendMessage("§2" + player.getName() + ": §r" + event.getMessage());

                    }

                }

            }
        }

    }

}
