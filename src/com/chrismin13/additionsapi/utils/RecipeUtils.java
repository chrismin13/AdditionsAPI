package com.chrismin13.additionsapi.utils;

import org.bukkit.inventory.Recipe;

import com.google.gson.JsonObject;

public class RecipeUtils {

	public static JsonObject getRecipeJson(Recipe recipe) {
		JsonObject json = new JsonObject();
		
		JsonObject object=new JsonObject();
		  object.addProperty("number", 1);
		  object.addProperty("latitude", 2);
		  object.addProperty("longitude", 3);
		  object.addProperty("text", 4);
		
		return json;
	}
	
}
