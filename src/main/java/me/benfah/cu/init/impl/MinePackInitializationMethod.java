package me.benfah.cu.init.impl;

import java.io.File;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.chrismin13.additionsapi.AdditionsAPI;
import com.chrismin13.additionsapi.utils.Debug;

import us.fihgu.toolbox.resourcepack.ResourcePackManager;

public class MinePackInitializationMethod {

	public static String resourcePack;
	public static String resourcePackHash;

	public static void uploadResourcePack(File f) {
		if (ResourcePackManager.neededRebuild) {
			Debug.saySuper(org.apache.http.conn.ssl.SSLConnectionSocketFactory.class.getProtectionDomain()
					.getCodeSource().getLocation().getPath());
			WebDriver driver = new HtmlUnitDriver();

			driver.get("https://minepack.net");

			WebElement element = driver.findElement(By.name("resourcepack"));
			element.sendKeys(f.getAbsolutePath());

			WebElement element2 = driver.findElement(By.name("submit"));
			element2.submit();

			List<WebElement> wel = driver.findElements(By.className("select"));

			try {
				resourcePack = wel.get(0).getAttribute("value").replace("https", "http");
				resourcePackHash = wel.get(1).getAttribute("value");
			} catch (IndexOutOfBoundsException e) {
				if (wel.isEmpty()) {
					Debug.saySuper("Resource Pack already exists on Minepack!");
					resourcePack = "http://download.nodecdn.net/containers/nodecraft/minepack/"
							+ ResourcePackManager.resourcePackMd5 + ".zip";
					resourcePackHash = ResourcePackManager.resourcePackSha1;
				}
				AdditionsAPI.getInstance().getConfig().set("resource-pack.sha1",
						MinePackInitializationMethod.resourcePackHash);
				AdditionsAPI.getInstance().getConfig().set("resource-pack.link",
						MinePackInitializationMethod.resourcePack);
			}
		} else {
			resourcePack = AdditionsAPI.getInstance().getConfig().getString("resource-pack.link");
			resourcePackHash = AdditionsAPI.getInstance().getConfig().getString("resource-pack.sha1");
		}
	}

}
