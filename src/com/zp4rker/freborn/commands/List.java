package com.zp4rker.freborn.commands;

import com.zp4rker.freborn.FactionsReborn;
import com.zp4rker.freborn.api.Faction;
import org.bukkit.command.CommandSender;

public class List {

	public static boolean command(CommandSender sender, String[] arguments, FactionsReborn plugin) {

		String[] args = new String[arguments.length - 1];

		for (int i = 1; i < arguments.length; i++) {
			args[i - 1] = arguments[i];
		}

		if (args.length > 1) {
			return invalidArgs(sender);
		}

		if (sender.hasPermission("freborn.list")) {
			return plugin.m.invalidPerms(sender);
		}
		
		sender.sendMessage("§6Factions: ");
		int pageNumber = 0;

		if (args.length == 0) {
			
			pageNumber = 1;
			
			for (Faction faction : plugin.m.getPage(0)) {
				
				sender.sendMessage("§6- §1" + faction.getName());
				
			}
			
		} else {
			
			try {
				pageNumber = Integer.parseInt(args[0]);
				
			} catch (NumberFormatException e) {
				
				return invalidArgs(sender);
				
			}
			
			for (Faction faction : plugin.m.getPage(pageNumber - 1)) {
				
				sender.sendMessage("§6- §1" + faction.getName());
				
			}
			
		}
		
		sender.sendMessage("§6Page §1" + pageNumber + " §6of " + plugin.m.getPages());

		return true;

	}

	private static boolean invalidArgs(CommandSender sender) {
		sender.sendMessage("§4Invalid Arguments!");
		sender.sendMessage("§2/factions list <PAGE>");
		return true;
	}

}
