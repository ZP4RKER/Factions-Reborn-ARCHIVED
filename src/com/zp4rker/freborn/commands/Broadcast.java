package com.zp4rker.freborn.commands;

import java.util.UUID;

import com.zp4rker.freborn.FactionsReborn;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.zp4rker.freborn.api.Faction;

public class Broadcast {

    public static boolean command(Player player, String[] arguments, FactionsReborn plugin) {

        String[] args = new String[arguments.length - 1];

        for (int i = 1; i < arguments.length; i++) {
            args[i - 1] = arguments[i];
        }

        if (!(args.length > 1)) {
            return invalidPerms(player);
        }

        if (!player.hasPermission("freborn.broadcast")) {
            return plugin.m.invalidPerms(player);
        }

        if (!plugin.m.isInFaction(player)) {
            player.sendMessage("§4You are not in a faction!");
            return true;
        }

        Faction faction = plugin.m.getFaction(player);

        for (String uuid : faction.getPlayers()) {

            Player target = Bukkit.getPlayer(UUID.fromString(uuid));

            if (target != null) {

                StringBuilder sb = new StringBuilder();

                for (int i = 0; i < args.length; i++) {

                    sb.append(args[i]);

                }

                target.sendMessage("§2Faction Broadcast: §r" + sb.toString());

            }

        }

        return true;

    }

    private static boolean invalidPerms(Player player) {
        player.sendMessage("§4Invalid Arguments!");
        player.sendMessage("§2/factions broadcast <MESSAGE>");
        return true;
    }

}