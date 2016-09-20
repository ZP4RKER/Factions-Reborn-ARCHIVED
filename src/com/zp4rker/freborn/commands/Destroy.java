package com.zp4rker.freborn.commands;

import com.zp4rker.freborn.FactionsReborn;
import com.zp4rker.freborn.api.Faction;
import org.bukkit.command.CommandSender;

public class Destroy {
	
	public static boolean command(CommandSender sender, String[] arguments, FactionsReborn plugin) {
		
		String[] args = new String[arguments.length - 1];

        for (int i = 1; i < arguments.length; i++) {
            args[i - 1] = arguments[i];
        }
        
        if (args.length != 1) {
        	return invalidArgs(sender);
        }
        
        if (sender.hasPermission("freborn.admin")) {
        	return plugin.m.invalidPerms(sender);
        }
        
        Faction faction = plugin.m.getFaction(args[0]);
        
        if (faction == null) {
        	
        	sender.sendMessage("§4That faction does not exist!");
        	return true;
        	
        } else {
        	
        	faction.disband(sender);
        	return true;
        	
        }
		
	}
	
	private static boolean invalidArgs(CommandSender sender) {
        sender.sendMessage("§4Invalid Arguments!");
        sender.sendMessage("§2/factions disband");
        return true;
    }

}