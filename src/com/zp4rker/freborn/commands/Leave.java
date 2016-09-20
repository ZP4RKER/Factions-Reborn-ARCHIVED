package com.zp4rker.freborn.commands;

import com.zp4rker.freborn.FactionsReborn;
import org.bukkit.entity.Player;

import com.zp4rker.freborn.api.Faction;

public class Leave {

    public static boolean command(Player player, String[] arguments, FactionsReborn plugin) {

        String[] args = new String[arguments.length - 1];

        for (int i = 1; i < arguments.length; i++) {
            args[i - 1] = arguments[i];
        }

        if (args.length != 0) {
            return invalidArgs(player);
        }

        if (!player.hasPermission("freborn.leave")) {
            return plugin.m.invalidPerms(player);
        }

        if (plugin.m.isInFaction(player)) {
            Faction faction = plugin.m.getFaction(player);
            faction.removePlayer(player, false);
            FactionsReborn.getConfig("factions").set(faction.getName() + ".players", faction.getPlayers());
            return true;
        } else {
            player.sendMessage("§4You are not in a faction!");
            return true;
        }

    }

    private static boolean invalidArgs(Player player) {
        player.sendMessage("§4Invalid Arguments!");
        player.sendMessage("§2/factions leave");
        return true;
    }

}
