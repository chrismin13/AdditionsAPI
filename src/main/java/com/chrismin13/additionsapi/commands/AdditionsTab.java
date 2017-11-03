package com.chrismin13.additionsapi.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import com.chrismin13.additionsapi.AdditionsAPI;
import com.chrismin13.additionsapi.utils.StringUtils;

public class AdditionsTab implements TabCompleter {

	public static final List<String> SUBCOMMANDS = Arrays.asList("give");

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
		if (args.length == 1) {
			String start = args[0];
			return StringUtils.startsWith(SUBCOMMANDS, start);
		}

		if (args.length == 2) {
			String firstArg = args[0];

			if (firstArg.equals("give")) {
				return null;
			}
		}
		
		if (args.length == 3) {
			String firstArg = args[0];
			String secondArg = args[1];
			String start = args[2];

			if (firstArg.equals("give") && Bukkit.getPlayer(secondArg) != null)
				return StringUtils.startsWith(AdditionsAPI.getAllCustomItemIdNames(), start);
		}
		
		if (args.length == 4) {
			String firstArg = args[0];
			String secondArg = args[1];
			String thirdArg = args[2];
			String start = args[3];

			if (firstArg.equals("give") && Bukkit.getPlayer(secondArg) != null && AdditionsAPI.isCustomItem(thirdArg))
				return StringUtils.startsWith(Arrays.asList("1"), start);
		}
		
		if (args.length == 5) {
			String firstArg = args[0];
			String secondArg = args[1];
			String thirdArg = args[2];
			String fourthArg = args[3];
			String start = args[4];

			if (firstArg.equals("give") && Bukkit.getPlayer(secondArg) != null && AdditionsAPI.isCustomItem(thirdArg) && fourthArg.equals("1"))
				return StringUtils.startsWith(Arrays.asList("0"), start);
		}
		
		return new ArrayList<String>();
	}

}