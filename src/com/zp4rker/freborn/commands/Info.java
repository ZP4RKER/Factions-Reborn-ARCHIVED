package com.zp4rker.freborn.commands;

import com.zp4rker.freborn.api.Faction;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.zp4rker.freborn.FactionsReborn;

public class Info {

    public static boolean command(CommandSender sender, String[] arguments, FactionsReborn plugin) {

        String[] args = new String[arguments.length - 1];

        for (int i = 1; i < arguments.length; i++) {
            args[i - 1] = arguments[i];
        }

        if (args.length != 1) {
            return invalidArgs(sender);
        }

        if (!sender.hasPermission("freborn.info")) {
            return plugin.m.invalidPerms(sender);
        }

        Faction faction = plugin.m.getFaction(args[0]);

        if (faction != null) {

            sender.sendMessage("§1Faction Info");
            sender.sendMessage(String.format("§6Name: §2%s", faction.getName()));
            sender.sendMessage(String.format("§6Tag: §2%s", faction.getTag()));
            sender.sendMessage(String.format("§6Owner: §2%s", faction.getOwner().getName()));
            sender.sendMessage(String.format("§6Players: §2%s", faction.getPlayers().size()));
            return true;

        } else {

            @SuppressWarnings("deprecation")
			OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[0]);

            if (offlinePlayer != null) {

                sender.sendMessage(String.format("§6Name: §2%s", offlinePlayer.getName()));

                Player player = Bukkit.getPlayer(offlinePlayer.getUniqueId());

                if (player != null) {

                    sender.sendMessage(String.format("§6DisplayName: §2%s", player.getDisplayName()));
                    sender.sendMessage(String.format("§6Last Login: §2%s", "Online"));

                } else {

                    sender.sendMessage(String.format("§6Last Login: §2l", offlinePlayer.getLastPlayed()));

                }

                sender.sendMessage(String.format("§6Faction: §2%s", plugin.m.getFaction(offlinePlayer) == null ?
                "None" : plugin.m.getFaction(player).getName()));

                return true;

            } else {

                sender.sendMessage("§4No player or faction exists with that name!");

                return true;

            }

        }

    }

    private static boolean invalidArgs(CommandSender sender) {
        sender.sendMessage("§4Invalid Arguments!");
        sender.sendMessage("§2/factions info <FACTION>");
        return true;
    }

}