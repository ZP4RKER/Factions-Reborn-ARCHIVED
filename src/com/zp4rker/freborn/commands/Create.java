package com.zp4rker.freborn.commands;


import com.zp4rker.freborn.FactionsReborn;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class Create {

    public static boolean command(Player player, String[] arguments, FactionsReborn plugin) {

        String[] args = new String[arguments.length - 1];

        for (int i = 1; i < arguments.length; i++) {
            args[i - 1] = arguments[i];
        }

        if (!(args.length >= 2)) {
            return invalidArgs(player);
        }

        if (!player.hasPermission("freborn.create")) {
            return plugin.m.invalidPerms(player);
        }
        StringBuilder sb = new StringBuilder();

        for (int i = 1; i < args.length; i++) {
            sb.append(args[i] + " ");
        }

        String tag = sb.toString();

        int nameMax = plugin.getConfig().getInt("maximum-faction-name-length", 0);
        int tagMax = plugin.getConfig().getInt("maximum-faction-tag-length", 0);

        if (nameMax != 0 && args[0].length() < nameMax) {
            if (tagMax != 0 && tag.length() < tagMax) {
                if (plugin.m.factionExists(args[0])) {
                    player.sendMessage("§4That Faction already exists!");
                    return true;
                } else {
                    Map<String, String> factionData = new HashMap<>();
                    factionData.put(args[0], tag);
                    plugin.v.makingFaction.put(player, factionData);
                    plugin.m.giveNexus(player);
                    return true;
                }
            }
        }

        return false;

    }

    private static boolean invalidArgs(Player player) {
        player.sendMessage("§4Invalid Arguments!");
        player.sendMessage("§2/factions create <NAME> <TAG>");
        return true;
    }

}