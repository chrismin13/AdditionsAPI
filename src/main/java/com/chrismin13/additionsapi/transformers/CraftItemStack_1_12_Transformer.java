package com.chrismin13.additionsapi.transformers;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;

import me.yamakaja.runtimetransformer.annotation.Inject;
import me.yamakaja.runtimetransformer.annotation.InjectionType;
import me.yamakaja.runtimetransformer.annotation.Transform;

@Transform(CraftItemStack.class)
public abstract class CraftItemStack_1_12_Transformer {

	@Inject(InjectionType.INSERT)
	public void setDurability(final short durability) {
		Bukkit.broadcastMessage("Durability was set to: " + Short.toString(durability));
	}
}