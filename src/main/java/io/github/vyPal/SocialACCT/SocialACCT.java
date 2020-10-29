package io.github.vyPal.SocialACCT;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public final class SocialACCT extends JavaPlugin {
    
	
	@Override
    public void onEnable() {
		this.saveDefaultConfig();
		PlayerYAML.folderPPY(this.getDataFolder());
		int pluginId = 9245; // <-- Replace with the id of your plugin!
        @SuppressWarnings("unused")
		Metrics metrics = new Metrics(this, pluginId);
    }
    
    @Override
    public void onDisable() {
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    	if (cmd.getName().equalsIgnoreCase("social")) { // If the player typed /basic then do the following, note: If you only registered this executor for one command, you don't need this
    		if (!(sender instanceof Player)) {
    			sender.sendMessage("This command can only be run by a player.");
    		} else {
    			Player player = null;
    			if(args.length == 1) {
    				player = Bukkit.getPlayer(args[0]);
    			}else {
    				player = (Player) sender;
    			}
    			if (sender.hasPermission("scacct.social")) {
    				if(!PlayerYAML.existsPPY(this.getDataFolder(), sender.getName())) {
    					PlayerYAML.createPPY(this.getDataFolder(), sender.getName());
    				}
    				FileConfiguration cfg = PlayerYAML.loadPPY(this.getDataFolder(), player.getName());
    				
    				//FIND YOUTUBE CONNECTIONS OF USER
    				String ytlink = cfg.getString(player.getName()+".youtube.link");
    				if(ytlink != null) {
    					player.sendMessage(this.getConfig().getString("messages.youtube-linked") + " Link: " + ytlink);
    				}else {
    					player.sendMessage(this.getConfig().getString("messages.youtube-not-linked"));
    				}
    				
    				//FIND TWITCH CONNECTIONS OF USER
    				String twlink = cfg.getString(player.getName()+".twitch.link");
    				if(twlink != null) {
    					player.sendMessage(this.getConfig().getString("messages.twitch-linked") + " Link: " + twlink);
    				}else {
    					player.sendMessage(this.getConfig().getString("messages.twitch-not-linked"));
    				}
    			} else {
    				player.sendMessage(this.getConfig().getString("messages.no-permission.social"));
    			}
    		}
    		return true;
    	}
    	if (cmd.getName().equalsIgnoreCase("link")) { // If the player typed /basic then do the following, note: If you only registered this executor for one command, you don't need this
    		if (!(sender instanceof Player)) {
    			sender.sendMessage("This command can only be run by a player.");
    		} else {
    			Player player = (Player) sender;
    			if (player.hasPermission("scacct.link")) {
    				File playerFile = new File(this.getDataFolder().getAbsolutePath()+File.separator+"players"+File.separator+sender.getName()+".yml");
    				if(!PlayerYAML.existsPPY(this.getDataFolder(), player.getName())) {
    					PlayerYAML.createPPY(this.getDataFolder(), player.getName());
    				}
    				FileConfiguration cfg = PlayerYAML.loadPPY(this.getDataFolder(), player.getName());
    				if (args[0].equals("youtube")) {
    					cfg.set(player.getName()+".youtube.link",args[1]);
    					saveCustomYml(cfg, playerFile);
    					player.sendMessage(this.getConfig().getString("messages.social-linked"));
    				}else if (args[0].equals("twitch")) {
    					cfg.set(player.getName()+".twitch.link",args[1]);
    					saveCustomYml(cfg, playerFile);
    					player.sendMessage(this.getConfig().getString("messages.social-linked"));
    				}else {
    					player.sendMessage("no link submitted");
    				}
    			} else {
    				player.sendMessage(this.getConfig().getString("messages.no-permission.link"));
    			}
    		}
    		return true;
    	}
    	if (cmd.getName().equalsIgnoreCase("stream")) { // If the player typed /basic then do the following, note: If you only registered this executor for one command, you don't need this
    		if (!(sender instanceof Player)) {
    			sender.sendMessage("This command can only be run by a player.");
    		} else {
    			Player player = (Player) sender;
    			FileConfiguration cfg = PlayerYAML.loadPPY(this.getDataFolder(), player.getName());
    			if(player.hasPermission("scacct.stream")) {
    				if(args.length == 1) {
    					if(args[0].equals("youtube")) {
    						Bukkit.broadcastMessage("Player " + player.getName() + " is streaming on his youtube channel: " + cfg.getString(player.getName() + ".youtube.link"));
    					}else if(args[0].equals("twitch")){
							Bukkit.broadcastMessage("Player " + player.getName() + " is streaming on his twitch channel: " + cfg.getString(player.getName() + ".twitch.link"));					
    					}
    				}else {
    					Bukkit.broadcastMessage("Player " + player.getName() + " is streaming on his channels: " + cfg.getString(player.getName() + ".youtube.link") + " and " + cfg.getString(player.getName() + ".twitch.link"));
    				}
    			}
    		}
    		return true;
    	}
    	if (cmd.getName().equalsIgnoreCase("video")) { // If the player typed /basic then do the following, note: If you only registered this executor for one command, you don't need this
    		if (!(sender instanceof Player)) {
    			sender.sendMessage("This command can only be run by a player.");
    		} else {
    			Player player = (Player) sender;
    			FileConfiguration cfg = PlayerYAML.loadPPY(this.getDataFolder(), player.getName());
    			if(player.hasPermission("scacct.video")) {
    				if(args.length == 1) {
    					if(args[0].equals("youtube")) {
    						Bukkit.broadcastMessage("Player " + player.getName() + " posted a new vid on his youtube channel: " + cfg.getString(player.getName() + ".youtube.link"));
    					}else if(args[0].equals("twitch")){
							Bukkit.broadcastMessage("Player " + player.getName() + " posted a new vid on his twitch channel: " + cfg.getString(player.getName() + ".twitch.link"));					
    					}
    				}else {
    					Bukkit.broadcastMessage("Player " + player.getName() + " posted a new vid on his channels: " + cfg.getString(player.getName() + ".youtube.link") + " and " + cfg.getString(player.getName() + ".twitch.link"));
    				}
    			}
    		}
    		return true;
    	}
    	return false; 
    }
    @Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		if(command.getName().equalsIgnoreCase("link")) {
			if(args.length == 1) {
				List<String> otc = new ArrayList<>();
				otc.add("youtube");
				otc.add("twitch");
				return otc;
			}
		}
		if(command.getName().equalsIgnoreCase("stream")) {
			if(args.length == 1) {
				List<String> otc = new ArrayList<>();
				otc.add("youtube");
				otc.add("twitch");
				return otc;
			}
		}
		if(command.getName().equalsIgnoreCase("video")) {
			if(args.length == 1) {
				List<String> otc = new ArrayList<>();
				otc.add("youtube");
				otc.add("twitch");
				return otc;
			}
		}
		return null;
	}
    public void saveCustomYml(FileConfiguration cfg, File cfgFile) {
    	try {
    		cfg.save(cfgFile);
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }
}
