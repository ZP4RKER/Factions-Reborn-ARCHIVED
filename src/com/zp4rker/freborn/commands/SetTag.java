package com.zp4rker.freborn.commands;

import com.zp4rker.freborn.FactionsReborn;
import com.zp4rker.freborn.api.Faction;
import org.bukkit.entity.Player;

public class SetTag {

    public static boolean command(Player player, String[] arguments, FactionsReborn plugin) {

        String[] args = new String[arguments.length - 1];

        for (int i = 1; i < arguments.length; i++) {
            args[i - 1] = arguments[i];
        }

        if (args.length == 0) {
            return invalidArgs(player);
        }

        Faction faction = plugin.m.getFaction(player);

        if (faction.getOwner().getUniqueId() != player.getUniqueId()) {
            player.sendMessage("§4You have to be the owner of the faction!");
            return true;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(args[0]);

        for (int i = 1; i < args.length; i++) {
            sb.append(" " + args[i]);
        }

        String tag = sb.toString();

        FactionsReborn.getConfig("factions").set(faction.getName() + ".tag", tag);
        FactionsReborn.getConfig("factions").saveConfig();

        player.sendMessage("§6Set your factions tag to §2\"" + tag + "\"§6.");

        return true;

    }

    private static boolean invalidArgs(Player player) {
        player.sendMessage("§4Invalid Arguments!");
        player.sendMessage("§2/factions tag <TAG>");
        return true;
    }

}
