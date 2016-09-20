package com.zp4rker.freborn.commands;

import com.zp4rker.freborn.FactionsReborn;
import org.bukkit.entity.Player;

import com.zp4rker.freborn.api.Faction;

public class Disband {

    public static boolean command(Player player, String[] arguments, FactionsReborn plugin) {

        String[] args = new String[arguments.length - 1];

        for (int i = 1; i < arguments.length; i++) {
            args[i - 1] = arguments[i];
        }

        if (args.length != 0) {
            return invalidArgs(player);
        }

        if (!player.hasPermission("freborn.disband")) {
            player.sendMessage("§4You do not have permission to do that!");
            return true;
        }

        if (plugin.m.isInFaction(player)) {
            if (plugin.m.isOwner(player)) {
                Faction faction = plugin.m.getFaction(player);
                faction.disband(player);
                FactionsReborn.getConfig("factions").set(faction.getName(), null);
                FactionsReborn.getConfig("factions").saveConfig();
            } else {
                player.sendMessage("§4You must be the owner of the faction!");
            }
            return true;
        } else {
            player.sendMessage("§4You are not in a faction!");
            return true;
        }

    }

    private static boolean invalidArgs(Player player) {
        player.sendMessage("§4Invalid Arguments!");
        player.sendMessage("§2/factions disband");
        return true;
    }

}