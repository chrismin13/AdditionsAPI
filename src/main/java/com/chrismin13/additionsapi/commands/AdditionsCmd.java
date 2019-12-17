package com.chrismin13.additionsapi.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.chrismin13.additionsapi.AdditionsAPI;
import com.chrismin13.additionsapi.items.CustomItem;
import com.chrismin13.additionsapi.items.CustomItemStack;
import com.chrismin13.additionsapi.listeners.DurabilityBar;
import com.chrismin13.additionsapi.utils.NumberUtils;

import net.md_5.bungee.api.ChatColor;

public class AdditionsCmd implements CommandExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 0) {
			// === No Arguements === //
			sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "===--- Additions API by chrismin13 ---===");
			sender.sendMessage(ChatColor.GREEN + "           ===---=== Commands ===---====");
			sender.sendMessage("");
			sender.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "/additions give");
			sender.sendMessage("  Give the Custom Item with the Specified ID Name.");
			sender.sendMessage("");
			sender.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "/additions repair");
			sender.sendMessage("  Repair the item you are currently holding.");
			return true;
		}
		if (args[0].equals("give") && sender.hasPermission("additionsapi.give")) {

			if (args.length == 1) {
				// === Give Subcommand - No Extra Arguements === //
				sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "===--- Additions API by chrismin13 ---===");
				sender.sendMessage(ChatColor.GREEN + "             ---=== Give Command ===---");
				sender.sendMessage("");
				sender.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "Available Arguements:");
				sender.sendMessage(" - " + ChatColor.BOLD + "Player Name: " + ChatColor.RESET + "Any online player.");
				sender.sendMessage(" - " + ChatColor.BOLD + "ID Name: " + ChatColor.RESET + "plugin_name:item_name");
				sender.sendMessage(
						" - " + ChatColor.BOLD + "Amount: " + ChatColor.RESET + "Only valid if the item is stackable.");
				sender.sendMessage(" - " + ChatColor.BOLD + "Durability: " + ChatColor.RESET
						+ "Only applicable for items with Fake Durability.");
				return true;
			} else if (args.length == 2) {
				// === Give Subcommand - 1 Extra Arguement === //
				String secondArg = args[1];

				if (!AdditionsAPI.isCustomItem(secondArg))
					return notCustomItemMsg(sender);

				if (!(sender instanceof Player))
					return notPlayerMsg(sender);

				return giveCommand((Player) sender, secondArg, sender);
			} else if (args.length == 3) {
				// === Give Subcommand - 2 Extra Arguements === //
				String secondArg = args[1];
				String thirdArg = args[2];

				Player player = null;
				String idName = null;
				Integer amount = null;

				if (AdditionsAPI.isCustomItem(secondArg) && NumberUtils.isInteger(thirdArg)) {
					if (!(sender instanceof Player))
						return notPlayerMsg(sender);

					player = (Player) sender;
					idName = secondArg;
					amount = Integer.parseInt(thirdArg);
				} else if (Bukkit.getPlayer(secondArg) != null && AdditionsAPI.isCustomItem(thirdArg)) {

					player = Bukkit.getPlayer(secondArg);
					idName = thirdArg;

				} else {
					return notValidArgMsg(sender);
				}

				return giveCommand(player, idName, amount, sender);

			} else if (args.length == 4) {
				// === Give Subcommand - 3 Extra Arguements === //
				String secondArg = args[1];
				String thirdArg = args[2];
				String fourthArg = args[3];

				Player player = null;
				String idName = null;
				Integer amount = null;
				Integer durability = null;

				if (AdditionsAPI.isCustomItem(secondArg) && NumberUtils.isInteger(thirdArg)
						&& NumberUtils.isInteger(fourthArg)) {
					if (!(sender instanceof Player))
						return notPlayerMsg(sender);

					player = (Player) sender;
					idName = secondArg;
					amount = Integer.parseInt(thirdArg);
					durability = Integer.parseInt(fourthArg);
				} else if (Bukkit.getPlayer(secondArg) != null && AdditionsAPI.isCustomItem(thirdArg)
						&& NumberUtils.isInteger(fourthArg)) {

					player = Bukkit.getPlayer(secondArg);
					idName = thirdArg;
					amount = Integer.parseInt(fourthArg);

				} else {
					return notValidArgMsg(sender);
				}

				return giveCommand(player, idName, amount, durability, sender);
			} else if (args.length == 5) {
				// === Give Subcommand - 4 Extra Arguements === //
				String secondArg = args[1];
				String thirdArg = args[2];
				String fourthArg = args[3];
				String fifthArg = args[4];

				Player player = null;
				String idName = null;
				Integer amount = null;
				Integer durability = null;

				if (Bukkit.getPlayer(secondArg) != null && AdditionsAPI.isCustomItem(thirdArg)
						&& NumberUtils.isInteger(fourthArg) && NumberUtils.isInteger(fifthArg)) {

					player = Bukkit.getPlayer(secondArg);
					idName = thirdArg;
					amount = Integer.parseInt(fourthArg);
					durability = Integer.parseInt(fifthArg);

				} else {
					return notValidArgMsg(sender);
				}

				return giveCommand(player, idName, amount, durability, sender);
			} else {

				return notValidArgMsg(sender);

			}
		}

		if (args[0].equals("repair") && sender.hasPermission("additionsapi.repair")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "===--- Additions API by chrismin13 ---===");
				sender.sendMessage(ChatColor.GREEN + "             ---=== Repair Command ===---");
				sender.sendMessage("");
				sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "You are not an online player!");
				return true;
			}

			Player player = (Player) sender;
			ItemStack item = player.getInventory().getItemInMainHand();

			if (item == null || item.getType().equals(Material.AIR)) {
				sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "===--- Additions API by chrismin13 ---===");
				sender.sendMessage(ChatColor.GREEN + "             ---=== Repair Command ===---");
				sender.sendMessage("");
				sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Invalid Item in Main Hand!");
				return true;
			}

			if (AdditionsAPI.isCustomItem(item)) {
				CustomItemStack cStack = new CustomItemStack(item);
				CustomItem cItem = cStack.getCustomItem();

				if (!cItem.hasFakeDurability()) {
					if (cItem.isUnbreakable()) {
						sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "===--- Additions API by chrismin13 ---===");
						sender.sendMessage(ChatColor.GREEN + "             ---=== Repair Command ===---");
						sender.sendMessage("");
						sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "This item is unbreakable!");
						return true;
					}

					item.setDurability((short) 0);
				} else {
					cStack.setFakeDurability(cStack.getMaxFakeDurability());
				}
			} else if (item.getItemMeta().isUnbreakable() || item.getType().getMaxDurability() == (short) 0) {
				sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "===--- Additions API by chrismin13 ---===");
				sender.sendMessage(ChatColor.GREEN + "             ---=== Repair Command ===---");
				sender.sendMessage("");
				sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "This item is unbreakable!");
				return true;
			} else {
				item.setDurability((short) 0);
			}

            DurabilityBar.sendDurabilityBossBar(player, player.getInventory().getItemInMainHand(), EquipmentSlot.HAND);
			sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "===--- Additions API by chrismin13 ---===");
			sender.sendMessage(ChatColor.GREEN + "             ---=== Repair Command ===---");
			sender.sendMessage("");
			sender.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "Item Repaired!");
			return true;

		}

		sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "===--- Additions API by chrismin13 ---===");
		sender.sendMessage("");
		sender.sendMessage(ChatColor.GREEN + "Unknown Subcommand!");
		sender.sendMessage(ChatColor.GREEN + "Please type '/additions' to see all available commands!");
		return true;
	}

	public static boolean giveCommand(Player player, String idName, CommandSender sender) {
		return giveCommand(player, idName, null, null, sender);
	}

	public static boolean giveCommand(Player player, String idName, Integer amount, CommandSender sender) {
		return giveCommand(player, idName, amount, null, sender);
	}

	/**
	 * @param player
	 *            Can't be null
	 * @param idName
	 *            Can't be null
	 * @param amount
	 *            Can be null
	 * @param durability
	 *            Can be null - Uses the Vanilla Style durability adjustments.
	 * @return
	 */
	public static boolean giveCommand(Player player, String idName, Integer amount, Integer durability, CommandSender sender) {
		if (player == null || idName == null)
			return false;

		PlayerInventory inv = player.getInventory();
		CustomItemStack cStack = new CustomItemStack(idName);

		if (amount != null && !amount.equals(1)) {
			sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "===--- Additions API by chrismin13 ---===");
			sender.sendMessage(ChatColor.GREEN + "             ---=== Give Command ===---");
			sender.sendMessage("");
			sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "No Custom Items can be stacked.");
			sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Any amount of over 1 will be denied.");
			return true;
		}

		if (durability != null)
			cStack.setFakeDurability(cStack.getMaxFakeDurability() - durability);

		inv.addItem(cStack.getItemStack());

		sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "===--- Additions API by chrismin13 ---===");
		sender.sendMessage(ChatColor.GREEN + "             ---=== Give Command ===---");
		sender.sendMessage("");
		sender.sendMessage("Gave Custom Item: " + ChatColor.BOLD + idName);
		sender.sendMessage(
				"Amount: " + ChatColor.BOLD + cStack.getCustomItem().getAmount() + ChatColor.RESET + " Durability: "
						+ ChatColor.BOLD + cStack.getFakeDurability() + " / " + cStack.getMaxFakeDurability());

		return true;
	}

	public static boolean notCustomItemMsg(CommandSender sender) {
		sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "===--- Additions API by chrismin13 ---===");
		sender.sendMessage(ChatColor.GREEN + "             ---=== Give Command ===---");
		sender.sendMessage("");
		sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Invalid Item ID Name!");
		sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Press Tab to see all available IDs.");
		return true;
	}

	public static boolean notPlayerMsg(CommandSender sender) {
		sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "===--- Additions API by chrismin13 ---===");
		sender.sendMessage(ChatColor.GREEN + "             ---=== Give Command ===---");
		sender.sendMessage("");
		sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "You are not an online player!");
		sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Please specify a valid player.");
		return true;
	}

	public static boolean notValidArgMsg(CommandSender sender) {
		sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "===--- Additions API by chrismin13 ---===");
		sender.sendMessage("");
		sender.sendMessage(ChatColor.GREEN + "Unknown Subcommand!");
		sender.sendMessage(ChatColor.GREEN + "Please type '/additions' to see all available commands!");
		return true;
	}

}
