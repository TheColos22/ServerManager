package me.ryan7745.servermanager;

import java.util.ArrayList;
import java.util.List;

import me.ryan7745.servermanager.commands.BackCommand;
import me.ryan7745.servermanager.commands.BanCommand;
import me.ryan7745.servermanager.commands.ClearInventoryCommand;
import me.ryan7745.servermanager.commands.GamemodeCommand;
import me.ryan7745.servermanager.commands.GodCommand;
import me.ryan7745.servermanager.commands.HealthCommand;
import me.ryan7745.servermanager.commands.HomeCommand;
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
import me.ryan7745.servermanager.commands.WorldCommand;
import me.ryan7745.servermanager.gui.ServerManagerGUI;
import me.ryan7745.servermanager.listeners.PlayerListener;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/*
 * TODO
 * Add invisibility command
 * God mode
 * More basic commands
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
		pm.registerEvents(new PlayerListener(this), this);
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
		getCommand("gamemode").setExecutor(new GamemodeCommand(this));
		getCommand("god").setExecutor(new GodCommand(this));
		getCommand("heal").setExecutor(new HealthCommand(this));
		getCommand("home").setExecutor(new HomeCommand(this));
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
		getCommand("world").setExecutor(new WorldCommand(this));
		getCommand("weather").setExecutor(new WeatherCommand(this));
		getCommand("tp").setExecutor(new TeleportCommand(this));
		getCommand("tphere").setExecutor(new TeleportCommand(this));
		getCommand("tpall").setExecutor(new TeleportCommand(this));
		getCommand("top").setExecutor(new TopCommand(this));
		getCommand("servermanager").setExecutor(new ServerManagerCommand(this));
	}

	public boolean hasPermission(String name, String perm) {
		Player player = Bukkit.getPlayer(name);
		return hasPermission(player, perm);
	}
	public boolean hasPermission(Player player, String perm) {
		Util.debug("Checking permission for player: " + player.getName() + " and perm: " + "servermanager." + perm);
		if(player.hasPermission("servermanager." + perm) || player.hasPermission("servermanager.*")){
			return true;
		}
		return false;
	}
}
