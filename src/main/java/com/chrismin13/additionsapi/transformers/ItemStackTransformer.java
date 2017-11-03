package com.chrismin13.additionsapi.transformers;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.yamakaja.runtimetransformer.annotation.Inject;
import me.yamakaja.runtimetransformer.annotation.InjectionType;
import me.yamakaja.runtimetransformer.annotation.Transform;

@Transform(ItemStack.class)
public abstract class ItemStackTransformer {

    @Inject(InjectionType.INSERT)
    public void _init_(int type, int amount, short damage) {
        Bukkit.broadcastMessage("ItemStack of type " + type + ":" + damage + " x " + amount + " created");
    }

    @Inject(InjectionType.INSERT)
    public void setDurability(short amount) {
        Bukkit.broadcastMessage("Durability on ItemStack of type " + this.getType().name() + " has been set to " + String.valueOf(amount) + "!");
    }

    public abstract Material getType();

}