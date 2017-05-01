package com.chrismin13.moreminecraft.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.chrismin13.moreminecraft.MoreMinecraft;
import com.chrismin13.moreminecraft.utils.CustomItemUtils;

import net.md_5.bungee.api.ChatColor;
import us.fihgu.toolbox.resourcepack.ResourcePackServer;

public class Additions implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args[0].equals("reload") && sender.hasPermission("additionsapi.reload")) {
			ResourcePackServer.stopServer();
			CustomItemUtils.clearAll();
			MoreMinecraft.load();
			sender.sendMessage(ChatColor.GREEN + "Reload Complete!");
			return true;
		}
		return false;
	}

}
