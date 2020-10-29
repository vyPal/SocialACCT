package io.github.vyPal.SocialACCT;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class PlayerYAML {
	
	public static void folderPPY(File file) {
		File folder = new File(file.getAbsolutePath()+File.separator+"players");
		if(!folder.exists()) {
			folder.mkdirs();
			return;
		}else if(folder.exists()) {
			return;
		}
	}
	
	public static void createPPY(File plugindir, String playername) {
		File playerFile = new File(plugindir.getAbsolutePath()+File.separator+"players"+File.separator+playername+".yml");
		try {
			playerFile.createNewFile();
			return;
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}
	
	public static boolean existsPPY(File plugindir, String playername) {
		File playerFile = new File(plugindir.getAbsolutePath()+File.separator+"players"+File.separator+playername+".yml");
		if(playerFile.exists()) {
			return true;
		}else {
			return false;
		}
	}
	
	public static FileConfiguration loadPPY(File plugindir, String playername) {
		File playerFile = new File(plugindir.getAbsolutePath()+File.separator+"players"+File.separator+playername+".yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(playerFile);
		return config;
	}
}