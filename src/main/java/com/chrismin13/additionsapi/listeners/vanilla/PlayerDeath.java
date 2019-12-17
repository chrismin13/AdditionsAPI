package com.chrismin13.additionsapi.listeners.vanilla;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.chrismin13.additionsapi.AdditionsAPI;
import com.chrismin13.additionsapi.items.CustomItem;
import com.chrismin13.additionsapi.utils.Debug;
import com.chrismin13.additionsapi.utils.LangFileUtils;

public class PlayerDeath implements Listener {

	// TODO: Check if it works when you get some internet
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerDeath(PlayerDeathEvent event) {
		String msg = event.getDeathMessage();
		if(msg!=null) {
			Debug.saySuper("Original Death Message: " + msg);
			for (CustomItem cItem : AdditionsAPI.getAllCustomItems()) {
				if (msg.contains(cItem.getDisplayName())) {
					msg.replaceFirst(LangFileUtils.get("death_message").replaceFirst("CustomItem", cItem.getDisplayName()),
							"");
					Debug.saySuper("Replaced " + cItem.getDisplayName());
					break;
				}
			}
			Debug.saySuper("Final Death Message: " + msg);
			event.setDeathMessage(msg);
		}	
	}

	// TODO: Make the same for every entity that dies e.g. Wolf

}
