package com.chrismin13.moreminecraft;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.chrismin13.moreminecraft.listeners.custom.CustomItemBlockBreak;
import com.chrismin13.moreminecraft.listeners.custom.CustomItemBlockIgnite;
import com.chrismin13.moreminecraft.listeners.custom.CustomItemFish;
import com.chrismin13.moreminecraft.listeners.custom.CustomItemPlayerInteract;
import com.chrismin13.moreminecraft.listeners.custom.CustomItemShearEntity;
import com.chrismin13.moreminecraft.listeners.custom.CustomShieldEntityDamageByEntity;
import com.chrismin13.moreminecraft.listeners.custom.EntityDamageByPlayerUsingCustomItem;
import com.chrismin13.moreminecraft.listeners.custom.EntityShootCustomBow;
import com.chrismin13.moreminecraft.listeners.custom.PlayerCustomItemDamage;
import com.chrismin13.moreminecraft.listeners.vanilla.AnvilEvent;
import com.chrismin13.moreminecraft.listeners.vanilla.BlockBreak;
import com.chrismin13.moreminecraft.listeners.vanilla.BlockIgnite;
import com.chrismin13.moreminecraft.listeners.vanilla.CraftingTableEvent;
import com.chrismin13.moreminecraft.listeners.vanilla.EnchantmentListener;
import com.chrismin13.moreminecraft.listeners.vanilla.EntityDamageByEntity;
import com.chrismin13.moreminecraft.listeners.vanilla.EntityShootBow;
import com.chrismin13.moreminecraft.listeners.vanilla.PlayerFish;
import com.chrismin13.moreminecraft.listeners.vanilla.PlayerInteract;
import com.chrismin13.moreminecraft.listeners.vanilla.PlayerItemDamage;
import com.chrismin13.moreminecraft.listeners.vanilla.PlayerShearEntity;

public class MoreMinecraft extends JavaPlugin {

	public void onEnable() {
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(new EnchantmentListener(), this);
		pm.registerEvents(new AnvilEvent(), this);
		pm.registerEvents(new CraftingTableEvent(), this);
		pm.registerEvents(new BlockBreak(), this);
		pm.registerEvents(new PlayerItemDamage(), this);
		pm.registerEvents(new CustomItemBlockBreak(), this);
		pm.registerEvents(new EntityDamageByEntity(), this);
		pm.registerEvents(new EntityDamageByPlayerUsingCustomItem(), this);
		pm.registerEvents(new PlayerCustomItemDamage(), this);
		pm.registerEvents(new PlayerInteract(), this);
		pm.registerEvents(new CustomItemPlayerInteract(), this);
		pm.registerEvents(new PlayerShearEntity(), this);
		pm.registerEvents(new CustomItemShearEntity(), this);
		pm.registerEvents(new PlayerFish(), this);
		pm.registerEvents(new CustomItemFish(), this);
		pm.registerEvents(new BlockIgnite(), this);
		pm.registerEvents(new CustomItemBlockIgnite(), this);
		pm.registerEvents(new EntityShootBow(), this);
		pm.registerEvents(new EntityShootCustomBow(), this);
		pm.registerEvents(new CustomShieldEntityDamageByEntity(), this);
	}
	
	public void onDisable() {
		
	}
	
}
