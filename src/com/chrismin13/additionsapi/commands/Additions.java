package com.chrismin13.additionsapi.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.chrismin13.additionsapi.AdditionsAPI;
import com.chrismin13.additionsapi.files.ConfigFile;
import com.chrismin13.additionsapi.files.DataFile;
import com.chrismin13.additionsapi.files.LangFile;

import net.md_5.bungee.api.ChatColor;
import us.fihgu.toolbox.resourcepack.ResourcePackServer;

public class Additions implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args[0].equals("reload") && sender.hasPermission("additionsapi.reload")) {
			ConfigFile.getInstance().reloadConfig();
			DataFile.getInstance().reloadData();
			LangFile.getInstance().reloadLang();
			ResourcePackServer.stopServer();
			AdditionsAPI.clearAll();
			AdditionsAPI.load();
			sender.sendMessage(ChatColor.GREEN + "Reload Complete!");
			return true;
		}
		return false;
	}

}
