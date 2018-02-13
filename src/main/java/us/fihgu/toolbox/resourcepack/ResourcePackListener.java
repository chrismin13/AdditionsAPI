package us.fihgu.toolbox.resourcepack;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent.Status;

import com.chrismin13.additionsapi.AdditionsAPI;
import com.chrismin13.additionsapi.files.ConfigFile;
import com.chrismin13.additionsapi.utils.LangFileUtils;

public class ResourcePackListener implements Listener {

	@EventHandler
	public void onLogin(PlayerLoginEvent event) {
		final Player player = event.getPlayer();
		Bukkit.getServer().getScheduler().runTask(AdditionsAPI.getInstance(), () -> {
			if (ResourcePackManager.hasResource()
					&& ConfigFile.getInstance().getConfig().getBoolean("resource-pack.send-to-player")) {
				String link;
				if (event.getAddress().getHostAddress().equals("127.0.0.1")) {
					link = "http://" + ResourcePackServer.localhost + ":" + ResourcePackServer.port
							+ ResourcePackServer.path;
				} else {
					link = "http://" + ResourcePackServer.host + ":" + ResourcePackServer.port
							+ ResourcePackServer.path;
				}
				if (player != null && player.isOnline())
					player.setResourcePack(link);
			}
		});
	}

	@EventHandler
	public void onResourcestatusChange(PlayerResourcePackStatusEvent event) {
		if (ResourcePackManager.getForceResourcePack()) {
			Status status = event.getStatus();
			switch (status) {
			case DECLINED:
			case FAILED_DOWNLOAD:
				final Player player = event.getPlayer();
				Bukkit.getServer().getScheduler().runTask(AdditionsAPI.getInstance(),
						() -> player.kickPlayer(LangFileUtils.get("resource_pack_kick")));
				break;
			case ACCEPTED:
			case SUCCESSFULLY_LOADED:
			default:
				break;
			}
		}
	}
}
