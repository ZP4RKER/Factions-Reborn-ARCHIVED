package com.zp4rker.freborn.commands;

import com.zp4rker.freborn.FactionsReborn;
import org.bukkit.command.CommandSender;

public class Reload {

    public static boolean command(CommandSender sender, String[] arguments, FactionsReborn plugin) {

        String[] args = new String[arguments.length - 1];

        for (int i = 1; i < arguments.length; i++) {
            args[i - 1] = arguments[i];
        }

        if (args.length != 0) {
            return invalidArgs(sender);
        }

        if (!sender.hasPermission("freborn.admin")) {
            return plugin.m.invalidPerms(sender);
        }

        plugin.getPluginLoader().disablePlugin(plugin);
        plugin.getPluginLoader().enablePlugin(plugin);
        sender.sendMessage("§2zNexusFactions reloaded!");
        return true;

    }

    private static boolean invalidArgs(CommandSender sender) {
        sender.sendMessage("§4Invalid Arguments!");
        sender.sendMessage("§2/factions reload");
        return true;
    }

}
