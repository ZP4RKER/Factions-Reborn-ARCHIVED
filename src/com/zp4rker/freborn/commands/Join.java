package com.zp4rker.freborn.commands;

import com.zp4rker.freborn.FactionsReborn;
import org.bukkit.entity.Player;

import com.zp4rker.freborn.api.Faction;

public class Join {

    public static boolean command(Player player, String[] arguments, FactionsReborn plugin) {

        String[] args = new String[arguments.length - 1];

        for (int i = 1; i < arguments.length; i++) {
            args[i - 1] = arguments[i];
        }

        if (args.length != 1) {
            return invalidArgs(player);
        }

        if (!player.hasPermission("freborn.join")) {
            return plugin.m.invalidPerms(player);
        }

        Faction faction = plugin.m.getFaction(args[0]);
        if (faction != null) {
            if (faction.isOpen()) {
                faction.addPlayer(player);
                FactionsReborn.getConfig("factions").set(faction.getName() + ".players", faction.getPlayers());
                FactionsReborn.getConfig("factions").saveConfig();
                return true;
            } else {
                if (faction.getInvited().contains(player.getUniqueId().toString())) {
                    faction.addPlayer(player);
                    FactionsReborn.getConfig("factions").set(faction.getName() + ".players", faction.getPlayers());
                    FactionsReborn.getConfig("factions").saveConfig();
                    return true;
                } else {
                    player.sendMessage("§4You are not invited to that faction!");
                    return true;
                }
            }
        } else {
            player.sendMessage("§4That faction does not exist!");
            return true;
        }

    }

    private static boolean invalidArgs(Player player) {
        player.sendMessage("§4Invalid Arguments!");
        player.sendMessage("§2/factions join <NAME>");
        return true;
    }

}