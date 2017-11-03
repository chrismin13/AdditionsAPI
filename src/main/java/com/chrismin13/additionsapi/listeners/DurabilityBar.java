package com.chrismin13.additionsapi.listeners;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.chrismin13.additionsapi.AdditionsAPI;
import com.chrismin13.additionsapi.files.BossBarConfig;
import com.chrismin13.additionsapi.files.ConfigFile;
import com.chrismin13.additionsapi.items.CustomItemStack;

import com.chrismin13.additionsapi.utils.LangFileUtils;

import net.md_5.bungee.api.ChatColor;

public class DurabilityBar implements Listener {

	private static final HashMap<UUID, BossBar> playersBarsMain = new HashMap<UUID, BossBar>();
	private static final HashMap<UUID, BossBar> playersBarsOff = new HashMap<UUID, BossBar>();

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onItemSwitch(PlayerItemHeldEvent event) {
		if (event.isCancelled())
			return;
		Player player = event.getPlayer();
		Bukkit.getScheduler().scheduleSyncDelayedTask(AdditionsAPI.getInstance(), () -> {
			sendDurabilityBossBar(player, player.getInventory().getItemInMainHand(), EquipmentSlot.HAND);
			sendDurabilityBossBar(player, player.getInventory().getItemInOffHand(), EquipmentSlot.OFF_HAND);
		});
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onHandSwitch(PlayerSwapHandItemsEvent event) {
		if (event.isCancelled())
			return;
		sendDurabilityBossBar(event.getPlayer(), event.getMainHandItem(), EquipmentSlot.HAND);
		sendDurabilityBossBar(event.getPlayer(), event.getOffHandItem(), EquipmentSlot.OFF_HAND);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onItemDamage(PlayerItemDamageEvent event) {
		if (event.isCancelled())
			return;
		Player player = event.getPlayer();
		Bukkit.getScheduler().scheduleSyncDelayedTask(AdditionsAPI.getInstance(), () -> {
			sendDurabilityBossBar(player, player.getInventory().getItemInMainHand(), EquipmentSlot.HAND);
			sendDurabilityBossBar(player, player.getInventory().getItemInOffHand(), EquipmentSlot.OFF_HAND);
		});
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onItemBreak(PlayerItemBreakEvent event) {
		ItemStack broken = event.getBrokenItem();
		Player player = event.getPlayer();
		PlayerInventory inv = player.getInventory();
		if (broken.equals(inv.getItemInMainHand()))
			hideDurabilityBossBar(player, EquipmentSlot.HAND);
		else if (broken.equals(inv.getItemInOffHand()))
			hideDurabilityBossBar(player, EquipmentSlot.OFF_HAND);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onItemChange(InventoryClickEvent event) {
		if (event.isCancelled())
			return;
		Bukkit.getScheduler().scheduleSyncDelayedTask(AdditionsAPI.getInstance(), () -> {
			sendDurabilityBossBar((Player) event.getWhoClicked(),
					event.getWhoClicked().getInventory().getItemInMainHand(), EquipmentSlot.HAND);
			sendDurabilityBossBar((Player) event.getWhoClicked(),
					event.getWhoClicked().getInventory().getItemInOffHand(), EquipmentSlot.OFF_HAND);
		});
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onDeath(PlayerDeathEvent event) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(AdditionsAPI.getInstance(), () -> {
			sendDurabilityBossBar(event.getEntity(), event.getEntity().getInventory().getItemInMainHand(),
					EquipmentSlot.HAND);
			sendDurabilityBossBar(event.getEntity(), event.getEntity().getInventory().getItemInOffHand(),
					EquipmentSlot.OFF_HAND);
		});
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onXpChange(PlayerExpChangeEvent event) {
		sendDurabilityBossBar(event.getPlayer(), event.getPlayer().getInventory().getItemInMainHand(),
				EquipmentSlot.HAND);
		sendDurabilityBossBar(event.getPlayer(), event.getPlayer().getInventory().getItemInOffHand(),
				EquipmentSlot.OFF_HAND);
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		removeAllDurabilityBars(event.getPlayer());
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		sendDurabilityBossBar(player, player.getInventory().getItemInMainHand(), EquipmentSlot.HAND);
		sendDurabilityBossBar(player, player.getInventory().getItemInOffHand(), EquipmentSlot.OFF_HAND);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerTeleport(PlayerTeleportEvent event) {
		if (event.isCancelled())
			return;
		Player player = event.getPlayer();
		Bukkit.getScheduler().scheduleSyncDelayedTask(AdditionsAPI.getInstance(), () -> {
			sendDurabilityBossBar(player, player.getInventory().getItemInMainHand(), EquipmentSlot.HAND);
			sendDurabilityBossBar(player, player.getInventory().getItemInOffHand(), EquipmentSlot.OFF_HAND);
		});
	}

	public static void sendDurabilityBossBar(Player player, ItemStack item, EquipmentSlot slot) {
		BossBarConfig config = ConfigFile.getInstance().getBossBarConfig();
		if (!config.show())
			return;
		UUID uuid = player.getUniqueId();
		BossBar bar;
		HashMap<UUID, BossBar> playersBars;
		String title;
		if (slot.equals(EquipmentSlot.HAND)) {
			title = LangFileUtils.get("item_durability_main_hand");
			playersBars = playersBarsMain;
		} else if (slot.equals(EquipmentSlot.OFF_HAND)) {
			title = LangFileUtils.get("item_durability_off_hand");
			playersBars = playersBarsOff;
		} else {
			return;
		}
		if (!playersBars.containsKey(uuid)) {
			bar = Bukkit.createBossBar(title, BarColor.GREEN, BarStyle.SOLID);
			bar.addPlayer(player);
			playersBars.put(uuid, bar);
		} else {
			bar = playersBars.get(uuid);
		}
		if (item == null || item.getType() == Material.AIR) {
			bar.setVisible(false);
			bar.setProgress(1.0D);
			return;
		}
		int durability = 0;
		int durabilityMax = 0;
		if (AdditionsAPI.isCustomItem(item)) {
			if (!config.showCustomItems()) {
				bar.setVisible(false);
				bar.setProgress(1.0D);
				return;
			}
			CustomItemStack cStack = new CustomItemStack(item);
			if (cStack.getCustomItem().hasFakeDurability()) {
				durability = cStack.getFakeDurability();
				durabilityMax = cStack.getCustomItem().getFakeDurability();
			} else if (cStack.getCustomItem().isUnbreakable()) {
				bar.setVisible(false);
				bar.setProgress(1.0D);
				return;
			}
		} else if (item.getType().getMaxDurability() != 0) {
			if (!config.showVanillaItems()) {
				bar.setVisible(false);
				bar.setProgress(1.0D);
				return;
			}
			durabilityMax = item.getType().getMaxDurability();
			durability = durabilityMax - item.getDurability();
		} else {
			bar.setVisible(false);
			bar.setProgress(1.0D);
			return;
		}
		double progress = (double) durability / (double) durabilityMax;
		if (progress > 1) {
			progress = 1;
		} else if (progress < 0) {
			progress = 0;
		}
		bar.setVisible(true);
		if (progress < 0)
			progress = 0;
		else if (progress > 1)
			progress = 1;
		bar.setProgress(progress);
		if (progress >= 0.5) {
			bar.setColor(BarColor.GREEN);
			bar.setTitle(title + ChatColor.GREEN + durability + " / " + durabilityMax);
		} else if (progress >= 0.25) {
			bar.setColor(BarColor.YELLOW);
			bar.setTitle(title + ChatColor.YELLOW + durability + " / " + durabilityMax);
		} else {
			bar.setColor(BarColor.RED);
			bar.setTitle(title + ChatColor.RED + durability + " / " + durabilityMax);
		}
	}

	public static void hideDurabilityBossBar(Player player, EquipmentSlot slot) {
		UUID uuid = player.getUniqueId();
		BossBar bar;
		HashMap<UUID, BossBar> playersBars;
		if (slot.equals(EquipmentSlot.HAND)) {
			playersBars = playersBarsMain;
		} else if (slot.equals(EquipmentSlot.OFF_HAND)) {
			playersBars = playersBarsOff;
		} else {
			return;
		}
		if (!playersBars.containsKey(uuid)) {
			return;
		} else {
			bar = playersBars.get(uuid);
		}

		bar.setVisible(false);
		bar.setProgress(1.0D);
		bar.setColor(BarColor.GREEN);
	}

	public static void removeAllDurabilityBars() {
		for (BossBar bar : playersBarsMain.values()) {
			bar.removeAll();
			bar.setVisible(false);
		}
		playersBarsMain.clear();
		for (BossBar bar : playersBarsOff.values()) {
			bar.removeAll();
			bar.setVisible(false);
		}
		playersBarsOff.clear();
	}

	public static void removeAllDurabilityBars(Player player) {
		UUID uuid = player.getUniqueId();
		playersBarsMain.remove(uuid);
		playersBarsOff.remove(uuid);

	}

}
