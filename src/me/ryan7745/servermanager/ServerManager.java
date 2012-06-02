package me.ryan7745.servermanager;

import me.ryan7745.servermanager.commands.BackCommand;
import me.ryan7745.servermanager.commands.BanCommand;
import me.ryan7745.servermanager.commands.ClearInventoryCommand;
import me.ryan7745.servermanager.commands.EnchantCommand;
import me.ryan7745.servermanager.commands.FlyCommand;
import me.ryan7745.servermanager.commands.GamemodeCommand;
import me.ryan7745.servermanager.commands.GodCommand;
import me.ryan7745.servermanager.commands.HealthCommand;
import me.ryan7745.servermanager.commands.HomeCommand;
<<<<<<< HEAD
=======
import me.ryan7745.servermanager.commands.InvisCommand;
>>>>>>> Update
import me.ryan7745.servermanager.commands.ItemCommand;
import me.ryan7745.servermanager.commands.KickCommand;
import me.ryan7745.servermanager.commands.NicknameCommand;
import me.ryan7745.servermanager.commands.ServerManagerCommand;
import me.ryan7745.servermanager.commands.SpawnCommand;
import me.ryan7745.servermanager.commands.TeleportCommand;
import me.ryan7745.servermanager.commands.TimeCommand;
import me.ryan7745.servermanager.commands.TopCommand;
import me.ryan7745.servermanager.commands.WarpCommand;
import me.ryan7745.servermanager.commands.WeatherCommand;
import me.ryan7745.servermanager.commands.WhoCommand;
import me.ryan7745.servermanager.gui.ServerManagerGUI;
import me.ryan7745.servermanager.listeners.ActionPlayerListener;
import me.ryan7745.servermanager.listeners.GeneralPlayerListener;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/*
 * TODO
 * Add invisibility command
 * More basic commands
 * Spawn mobs
 * Give command
 * visit/visitme commands
<<<<<<< HEAD
 * 
=======
 * Admin/Op chat with '.'
>>>>>>> Update
 */

public class ServerManager extends JavaPlugin {
	public YamlConfiguration mainConf;
	public YamlConfiguration warpConf;
	
	public boolean debug = false;
	
	public final ConfigUtil configUtil;
	public final Util util;
	
	public ServerManagerGUI gui;

	
	public ServerManager() {	
        configUtil = new ConfigUtil(this);
        util = new Util(this);
    }
	
	@Override
	public void onDisable() {
		Util.log(Util.pdfFile.getName() + " has been disabled!");
		
	}
	
	@Override
	public void onEnable() { //enable
		Util.pdfFile = getDescription();
		Util.log(Util.pdfFile.getName() + " Version " + Util.pdfFile.getVersion() + " Has been enabled!");
		
		
		ConfigUtil.loadConfig("config", "config");
		mainConf = ConfigUtil.getConfig("config");
		ConfigUtil.loadConfig("warps", "warps");
		warpConf = ConfigUtil.getConfig("warps");
		
		debug = mainConf.getBoolean("debug", false);
		
		GeoIP geoip = new GeoIP(this);
		geoip.load();
		
		if(mainConf.getBoolean("gui.enabled")){
			gui = new ServerManagerGUI(this);
		}
		
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new GeneralPlayerListener(this), this);
		pm.registerEvents(new ActionPlayerListener(this), this);
		pm.registerEvents(geoip, this);
		
		registerCommands();
		
		Util.debug("Succesfully loaded commands, config and events");
		Util.log("Succesfully loaded");
	}
	
	private void registerCommands(){
		getCommand("ban").setExecutor(new BanCommand(this));
		getCommand("unban").setExecutor(new BanCommand(this));
		getCommand("back").setExecutor(new BackCommand(this));
		getCommand("clear").setExecutor(new ClearInventoryCommand(this));
		getCommand("enchant").setExecutor(new EnchantCommand(this));
		getCommand("fly").setExecutor(new FlyCommand(this));
		getCommand("gamemode").setExecutor(new GamemodeCommand(this));
		getCommand("god").setExecutor(new GodCommand(this));
		getCommand("heal").setExecutor(new HealthCommand(this));
		getCommand("home").setExecutor(new HomeCommand(this));
<<<<<<< HEAD
=======
		getCommand("invis").setExecutor(new InvisCommand(this));
>>>>>>> Update
		getCommand("sethome").setExecutor(new HomeCommand(this));
		getCommand("feed").setExecutor(new HealthCommand(this));
		getCommand("item").setExecutor(new ItemCommand(this));
		getCommand("kick").setExecutor(new KickCommand(this));
		getCommand("kickall").setExecutor(new KickCommand(this));
		getCommand("nickname").setExecutor(new NicknameCommand(this));
		getCommand("spawn").setExecutor(new SpawnCommand(this));
		getCommand("setspawn").setExecutor(new SpawnCommand(this));
		getCommand("time").setExecutor(new TimeCommand(this));
		getCommand("warp").setExecutor(new WarpCommand(this));
		getCommand("who").setExecutor(new WhoCommand(this));
		getCommand("weather").setExecutor(new WeatherCommand(this));
		getCommand("tp").setExecutor(new TeleportCommand(this));
		getCommand("tphere").setExecutor(new TeleportCommand(this));
		getCommand("tpall").setExecutor(new TeleportCommand(this));
		getCommand("tpworld").setExecutor(new TeleportCommand(this));
		getCommand("top").setExecutor(new TopCommand(this));
		getCommand("servermanager").setExecutor(new ServerManagerCommand(this));
	}

	String basePerm = "servermanager";
	
	public boolean hasPermission(String name, String perm) {
		Player player = Bukkit.getPlayer(name);
		return hasPermission(player, perm);
	}
	public boolean hasPermission(Player player, String perm) {
		Util.debug("Checking permission for player: " + player.getName() + " and perm: " + basePerm + "." + perm);
		if(player.hasPermission(basePerm + "." + perm) || player.hasPermission(basePerm + ".*")){
			return true;
		}
		return false;
	}
}
