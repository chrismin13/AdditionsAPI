package us.fihgu.toolbox.resourcepack;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.function.Function;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.bukkit.plugin.java.JavaPlugin;

import com.chrismin13.additionsapi.AdditionsAPI;
import com.chrismin13.additionsapi.files.ConfigFile;
import com.chrismin13.additionsapi.items.CustomItem;
import com.chrismin13.additionsapi.utils.Debug;

import us.fihgu.toolbox.file.FileUtils;
import us.fihgu.toolbox.item.DamageableItem;
import us.fihgu.toolbox.item.ModelInjector;
import us.fihgu.toolbox.json.JsonUtils;
import us.fihgu.toolbox.reflection.ReflectionUtils;
import us.fihgu.toolbox.resourcepack.model.ItemModel;
import us.fihgu.toolbox.resourcepack.model.OverrideEntry;
import us.fihgu.toolbox.resourcepack.model.Predicate;

/**
 * This class is in charge of A generated server resource pack
 * <p>
 * the resource pack is automatically hosted on an embedded http server. (See
 * {@link ResourcePackServer})
 * <p>
 * Port settings may be changed though configuration.
 */
public class ResourcePackManager {
	/**
	 * True if player will be forced to download the resource pack from server.<br>
	 * Players that refuse to download the resource pack will be kicked.
	 */
	private static boolean force = false;

	/**
	 * It stores the name and version of plugin that uses this resource manager.
	 */
	public static final HashMap<String, String> resourceUsers = new HashMap<>();

	/**
	 * The file path for saving the final resource pack.
	 */
	private static File resourceFile = new File(
			AdditionsAPI.getInstance().getDataFolder() + "/resource-pack/resource.zip");

	/**
	 * A list of resources that needs to be merged to the final resource pack. <br>
	 * Each resource has to be a zip file, the content will be unzipped and included
	 * in the final resource pack.
	 */
	private static LinkedList<InputStream> resources = new LinkedList<>();

	/**
	 * Whether the Resource Pack ended up being rebuilt or not.
	 */
	public static boolean neededRebuild = false;

	/**
	 * The md5 of the current resource pack, used to check resource pack changes.
	 */
	public static String resourcePackMd5;

	public static String resourcePackSha1;
	
	public static byte[] resourcePackSha1Byte;

	/**
	 * Register a resource pack to be combined into server resource pack.<br>
	 * <p>
	 * You may only use this method inside your onEnable() method, else the resource
	 * will not be registered correctly.<br>
	 * When a new resource being registered the first time, the server cache will be
	 * reconstructed.<br>
	 * </p>
	 */
	public static void registerResource(JavaPlugin plugin, InputStream source) throws IOException {
		registerResourceUser(plugin);
		resources.add(source);
	}

	/**
	 * register the given plugin as a resource pack user, if the version or presence
	 * of the plugin change, a new resource pack will be build on server start up.
	 */
	public static void registerResourceUser(JavaPlugin plugin) {
		resourceUsers.put(plugin.getName(), plugin.getDescription().getVersion());
	}

	public static void buildResourcePack() throws IOException {
		// initialize work space
		File work = new File(AdditionsAPI.getInstance().getDataFolder() + "/resource-pack/work/");
		FileUtils.deleteFolder(work);
		// a temporary file used for downloading resource pack files.
		File temp = new File(AdditionsAPI.getInstance().getDataFolder() + "/resource-pack/download/temp.zip");
		FileUtils.createFileAndPath(temp);
		// check and download server's original resource pack.
		downloadResourcePack(work, temp);
		// process plugin resource packs.
		processPluginResource(work, temp);
		// inject custom item model into work space
		injectCustomItemModels(work);
		// copy resource pack.meta and logo.png
		FileUtils.copyResource(AdditionsAPI.getInstance(), "resource/pack.mcmeta", new File(work, "pack.mcmeta"));
		FileUtils.copyResource(AdditionsAPI.getInstance(), "resource/pack.png", new File(work, "pack.png"));

		// pack up result resource pack.
		Debug.sayTrue("Packing complete resource pack.");
		FileUtils.zip(work, resourceFile);
		Debug.sayTrue("Resource pack has been constructed.");

		// remove temporary folder.
		FileUtils.deleteFolder(work);
		FileUtils.deleteFolder(temp.getParentFile());

		// change last modified date to avoid different md5 for the same contents
		resourceFile.setLastModified(1500135786000L);

		FileInputStream fisRP = new FileInputStream(resourceFile);

		// md5Hex converts an array of bytes into an array of characters representing
		// the hexadecimal values of each byte in order.
		// The returned array will be double the length of the passed array, as it takes
		// two characters to represent any given byte.

		byte[] isByte = IOUtils.toByteArray(fisRP);
		resourcePackMd5 = DigestUtils.md5Hex(isByte);
		resourcePackSha1 = DigestUtils.sha1Hex(isByte);
		resourcePackSha1Byte = DatatypeConverter.parseHexBinary(resourcePackSha1);
		
		fisRP.close();

		String currentMd5 = AdditionsAPI.getInstance().getConfig().getString("resource-pack.md5");
		if (currentMd5 == null || !currentMd5.equals(resourcePackMd5)) {
			neededRebuild = true;
			Debug.say("Changes were detected in the Resource Pack, so it has been rebuilt.");
			int build = AdditionsAPI.getInstance().getConfig().getInt("resource-pack.build", 0);
			build++;
			AdditionsAPI.getInstance().getConfig().set("resource-pack.build", build);
			AdditionsAPI.getInstance().getConfig().set("resource-pack.md5", resourcePackMd5);
		} else {
			Debug.sayTrue("No resource pack change, using cached resource pack.");
		}

		// close all stream
		for (InputStream in : resources)
			if (in != null)
				in.close();

		resources.clear();
	}

	/**
	 * release plugin resource into work space
	 */
	private static void processPluginResource(File work, File temp) {
		for (InputStream in : resources) {
			try {
				// download each plugin resource
				FileOutputStream fileOut = new FileOutputStream(temp);

				FileUtils.copyStreams(in, fileOut);

				// extract each plugin resource
				FileUtils.unzip(temp, work);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * download and release original server resource pack into work space
	 */
	private static void downloadResourcePack(File work, File temp) {
		String urlStr = getServerResourcePack();
		ConfigFile.getInstance().getConfig().set("resource-pack.lastServerResourcePack", urlStr);
		if (urlStr != null && !urlStr.equals("") && !urlStr.equals("null")) {
			Debug.sayTrue("Found server resource pack setting.");
			try {
				Debug.sayTrue("downloading server resource pack");
				URL url = new URL(urlStr);
				FileUtils.copyURLtoFile(url, temp);
				Debug.sayTrue("unpacking server resource pack");
				FileUtils.unzip(temp, work);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * inject custom item models into the work space
	 */
	private static void injectCustomItemModels(File work) {
		// the models that's going to be injected into the resource pack.
		final HashMap<DamageableItem, ItemModel> itemModels = new HashMap<>();

		// temporary list of custom items
		ArrayList<CustomItem> tempList = new ArrayList<>();

		// Adding to the temp list every Custom Item that has a Model
		if (AdditionsAPI.getAllCustomItems() != null)
			for (CustomItem item : AdditionsAPI.getAllCustomItems())
				if (item instanceof ModelInjector)
					tempList.add(item);

		Collections.sort(tempList);

		for (CustomItem item : tempList) {
			DamageableItem baseItem = DamageableItem.getDamageableItem(item.getMaterial());
			ModelInjector injector = (ModelInjector) item;
			ItemModel model = getOrCreateItemModel(itemModels, baseItem);
			injector.inject(model, work);
			Collections.sort(model.getOverrides());
		}

		// process all modified base item models
		if (!itemModels.isEmpty()) {
			for (DamageableItem baseItem : itemModels.keySet()) {
				ItemModel model = itemModels.get(baseItem);

				// add override entry to preserver base item
				preserveBaseModel(model, baseItem);

				// write base model files
				File modelFile = new File(work, "/assets/minecraft/models/item/" + baseItem.getModelName() + ".json");
				if (modelFile.exists()) {
					String message = "The following file already exists! This may cause issues. If you see any textures not working correctly, this is the cause. Directory:"
							+ "/assets/minecraft/models/item/" + baseItem.getModelName() + ".json" + ". Item Material: "
							+ baseItem.getMaterial().toString();
					if (ConfigFile.getInstance().getConfig().getBoolean("resource-pack.overwrite-files")) {
						Debug.sayTrue(message);
					} else {
						Debug.sayError(message);
						Debug.sayError(
								"Since the config specifies to not overwrite the file, it will not be overwritten!");
						return;
					}
				}
				JsonUtils.toFile(modelFile, model);
			}
		}
	}

	/**
	 * Add override entry to preserver base model
	 */
	private static void preserveBaseModel(ItemModel model, DamageableItem baseItem) {
		ItemModel baseModel = baseItem.getDefaultItemModel();
		// preserve base item model
		model.addOverride(
				new OverrideEntry(new Predicate().setDamaged(1).setDamage(0d), "item/" + baseItem.getModelName()));
		if (baseModel.getOverrides() != null) {
			for (OverrideEntry entry : baseModel.getOverrides()) {
				// preserve all override entry that came from base item.
				OverrideEntry preserveEntry = entry.clone();
				preserveEntry.getPredicate().setDamaged(1).setDamage(0d);
				model.addOverride(preserveEntry);
			}
		}
	}

	private static ItemModel getOrCreateItemModel(HashMap<DamageableItem, ItemModel> itemModels,
			final DamageableItem baseItem) {
		return itemModels.computeIfAbsent(baseItem, new Function<DamageableItem, ItemModel>() {
			@Override
			public ItemModel apply(DamageableItem k) {
				return baseItem.getDefaultItemModel();
			}
		});
	}

	/**
	 * See {@link #force}
	 */
	public static void setForceResourcePack() {
		ResourcePackManager.force = true;
	}

	/**
	 * @return See {@link #force}
	 */
	public static boolean getForceResourcePack() {
		return ResourcePackManager.force;
	}

	/**
	 * @return whatever the user entered as resource pack in the server.property
	 *         file, used for importing user defined resource pack.
	 */
	public static String getServerResourcePack() {
		try {
			Class<?> minecraftServerClass = ReflectionUtils.getNMSClass("MinecraftServer");
			Object minecraftServer = minecraftServerClass.getMethod("getServer").invoke(null);
			return minecraftServerClass.getMethod("getResourcePack").invoke(minecraftServer).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}

	/**
	 * @return true if there is a need to host resource server.
	 */
	public static boolean hasResource() {
		// check if any plugin uses resource
		if (resourceUsers.size() > 0) {
			return true;
		}

		// check if any custom item requires model injection
		for (CustomItem item : AdditionsAPI.getAllCustomItems()) {
			if (item instanceof ModelInjector) {
				return true;
			}
		}

		return false;
	}
}
