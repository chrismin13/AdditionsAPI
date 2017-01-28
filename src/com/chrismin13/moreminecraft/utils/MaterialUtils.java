package com.chrismin13.moreminecraft.utils;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;

public class MaterialUtils {

	private static List<Material> instantlyBreakable = Arrays.asList(Material.WATER_LILY, Material.CARROT,
			Material.DEAD_BUSH, Material.FIRE, Material.FLOWER_POT, Material.LONG_GRASS, Material.MELON_STEM,
			Material.BROWN_MUSHROOM, Material.RED_MUSHROOM, Material.NETHER_WARTS, Material.POTATO,
			Material.PUMPKIN_STEM, Material.REDSTONE_COMPARATOR_OFF, Material.REDSTONE_COMPARATOR_ON,
			Material.DIODE_BLOCK_ON, Material.DIODE_BLOCK_OFF, Material.REDSTONE_TORCH_OFF, Material.REDSTONE_TORCH_ON,
			Material.REDSTONE_WIRE, Material.SAPLING, Material.SLIME_BLOCK, Material.SUGAR_CANE_BLOCK, Material.TNT,
			Material.TORCH, Material.TRIPWIRE, Material.TRIPWIRE_HOOK, Material.SEEDS, Material.WHEAT,
			Material.BEETROOT_BLOCK, Material.END_ROD, Material.DOUBLE_PLANT);

	public static boolean isInstantlyBreakable(Material material) {
		if (instantlyBreakable.contains(material) || isFlower(material))
			return true;
		return false;
	}

	@SuppressWarnings("deprecation")
	public static boolean isFlower(Material material) {
		int id = material.getId();
		if (id == 37 || id == 38)
			return true;
		return false;
	}

	private static List<Material> willDamageShears = Arrays.asList(Material.WEB, Material.LEAVES, Material.LEAVES_2,
			Material.WOOL, Material.VINE);

	public static boolean willDamageShears(Material material) {
		if (willDamageShears.contains(material) || isInstantlyBreakable(material))
			return true;
		return false;
	}

}
