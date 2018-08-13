package us.fihgu.toolbox.resourcepack;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent.Status;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import com.chrismin13.additionsapi.AdditionsAPI;
import com.chrismin13.additionsapi.files.ConfigFile;
import com.chrismin13.additionsapi.utils.Debug;
import com.chrismin13.additionsapi.utils.LangFileUtils;

import me.benfah.cu.init.impl.MinePackInitializationMethod;

public class ResourcePackListener implements Listener {

	@EventHandler
	public void onLogin(PlayerLoginEvent event) {
		sendResourcePack(event.getPlayer(), event.getAddress().getHostAddress());
	}

	public static void sendResourcePack(Player player, String hostAddress) {
		Bukkit.getServer().getScheduler().runTaskLater(AdditionsAPI.getInstance(), () -> {
			if (ResourcePackManager.hasResource()
					&& ConfigFile.getInstance().getConfig().getBoolean("resource-pack.send-to-player")
					&& !player.hasPermission(
							new Permission("additionsapi.resourcepack.disable", PermissionDefault.FALSE))) {
				String link;
				if (!ConfigFile.getInstance().getConfig().getBoolean("resource-pack.use-minepack")) {
					if (hostAddress != null && hostAddress.equals("127.0.0.1")) {
						link = "http://" + ResourcePackServer.localhost + ":" + ResourcePackServer.port
								+ ResourcePackServer.path;
					} else {
						link = "http://" + ResourcePackServer.host + ":" + ResourcePackServer.port
								+ ResourcePackServer.path;
					}
				} else {
					link = MinePackInitializationMethod.resourcePack;
				}
				if (player != null && player.isOnline())
					if (ResourcePackManager.hasSendWithHash)
						try {
							player.setResourcePack(link, ResourcePackManager.resourcePackSha1Byte);
						} catch (NoSuchMethodError e) {
							ResourcePackManager.hasSendWithHash = false;
							player.setResourcePack(link);
						}
					else
						player.setResourcePack(link);

				Debug.saySuper("Sending Resource Pack Link to Player: " + link);
			}
		}, 20L);
	}

	@EventHandler
	public void onResourcestatusChange(PlayerResourcePackStatusEvent event) {
		if (ResourcePackManager.getForceResourcePack()) {
			Status status = event.getStatus();
			switch (status) {
			case DECLINED:
			case FAILED_DOWNLOAD:
				final Player player = event.getPlayer();
				if (!player.hasPermission(new Permission("additionsapi.resourcepack.bypass", PermissionDefault.FALSE)))
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
