package me.ryan7745.servermanager.listeners;

import java.io.File;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.ryan7745.servermanager.ConfigUtil;
import me.ryan7745.servermanager.ServerManager;
import me.ryan7745.servermanager.Util;
import me.ryan7745.servermanager.commands.BackCommand;
import me.ryan7745.servermanager.gui.TabPlayer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Instrument;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Note;
import org.bukkit.Note.Tone;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.server.ServerListPingEvent;

import com.maxmind.geoip.regionName;

public class GeneralPlayerListener implements Listener {
	
	ServerManager plugin;
	public GeneralPlayerListener(ServerManager instance){
		plugin = instance;
	}
	
	@EventHandler
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event){
		if(event.isCancelled()) return;
		Player player = event.getPlayer();
		Util.log("[PLAYER_COMMAND]: " + player.getName() + ": " + event.getMessage());
	}
	
	@EventHandler
    public void onQuit(PlayerQuitEvent event) {
		Util.debug("Update last seen for player: " + event.getPlayer().getName());
        ConfigUtil.setPValString(event.getPlayer(), new Date().toString(), "seen");
        if(plugin.gui != null)
        	plugin.gui.playerTab.listModel.removeElement(event.getPlayer().getName());
    }

    @EventHandler
    public void onKick(PlayerKickEvent event) {
    	Util.debug("Update last seen for player: " + event.getPlayer().getName());
        ConfigUtil.setPValString(event.getPlayer(), new Date().toString(), "seen");
        if(plugin.gui != null)
        	plugin.gui.playerTab.listModel.removeElement(event.getPlayer().getName());
    }
    
    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event){
    	Player player = event.getPlayer();
    	Location from = event.getFrom();
    	BackCommand.backdb.put(player.getName(), from);
    }
    
    @EventHandler
    public void onPlayerChat(PlayerChatEvent event){
    	if(event.isCancelled()) return;
    	if (event.getMessage() == null) return;
    	
    	String chat = String.valueOf(event.getMessage()).replaceAll("%", "%%");
    	Player player = event.getPlayer();
    	
    	//notification alerts
    	List<String> p_tagged = new ArrayList<String>();
    	
    	String[] notify_chat = chat.split(" ");
    	for(String s: notify_chat){
    		if(s.matches("@(.*)")){
    			String st = s.substring(1);
    			final Player notify_player = Bukkit.getPlayer(st);
    			if(notify_player != null && !p_tagged.contains(notify_player.getName())){
    				p_tagged.add(notify_player.getName());
    				notify_player.sendMessage("You have been tagged: ");
    				
    				final Block bLoc = notify_player.getLocation().getBlock().getRelative(BlockFace.NORTH);
    				final Block bLocUp = bLoc.getRelative(BlockFace.UP);
    				final Material old_mat = bLoc.getType();
    				final Material old_mat_up = bLocUp.getType();
    				bLoc.setType(Material.NOTE_BLOCK);
    				bLocUp.setType(Material.AIR);
    				
    				Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){

						@Override
						public void run() {
							notify_player.playNote(bLoc.getLocation(), Instrument.PIANO, Note.natural(1, Tone.C));
		    				bLoc.setType(old_mat);
		    				bLocUp.setType(old_mat_up);
						}
    					
    				});
    			}
    		}
    	}
    	
    	//chat formatting, custom chat support
    	String format = plugin.mainConf.getString("chat.format", "&7[{name}&7]: &f{msg}");
    	String name = player.getDisplayName();
    	if(player.isOp()) name = "&4" + name;
    	
    	format = format.replace("{name}", name);
    	format = format.replace("{msg}", chat);
    	
    	event.setFormat(format.replaceAll("(&([a-f0-9]))", "\u00A7$2"));
    }
    
    @EventHandler
    public void onEntityDamage(EntityDamageEvent event){
    	if(event.isCancelled()) return;
    	if(event.getEntity() instanceof Player){
    		Player player = (Player) event.getEntity();
    		if(ConfigUtil.getPVal(player, "god") != null && ConfigUtil.getPValBoolean(player, "god")){
    			event.setCancelled(true);
    		}
    	}
    }
    
    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerLoginHigh(PlayerLoginEvent event){
    	Player player = event.getPlayer();
    	if(event.getResult().equals(Result.KICK_BANNED)){
			event.setKickMessage(plugin.mainConf.getString("disconnect-messages.banned", "&7You are banned from this server!").replaceAll("(&([a-f0-9]))", "\u00A7$2"));
			return;
		} else if(event.getResult().equals(Result.KICK_WHITELIST)){
			event.setKickMessage(plugin.mainConf.getString("disconnect-messages.whitelist", "&5You are not on the whitelist!").replaceAll("(&([a-f0-9]))", "\u00A7$2"));
			Bukkit.broadcastMessage(ChatColor.GRAY + player.getName() + ChatColor.RED + " tried to join, Disconnected reason: Not on whitelist.");
			return;
		} else if(event.getResult().equals(Result.KICK_FULL)){
			event.setKickMessage(plugin.mainConf.getString("disconnect-messages.full", "&5The server is currently full, try again later!").replaceAll("(&([a-f0-9]))", "\u00A7$2"));
			Bukkit.broadcastMessage(ChatColor.GRAY + player.getName() + ChatColor.RED + " tried to join, Disconnected reason: Server full.");
			return;
		}
    }
    
    @EventHandler
    public void onServerListPing(ServerListPingEvent event) {
    	if(plugin.debug){
    		event.setMotd("This server is in Debug Mode.");
    	}
    	Util.debug("Recieved ping request, setting MOTD: " + event.getMotd());
    }
    
	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event){
		Player player = event.getPlayer();
		Util.debug("Set display name, for player: " + player.getName());
	    String dispname = ConfigUtil.getPValString(player, "dispname").trim();
	    if (dispname == null || dispname.equals("")) {
	    	dispname = player.getName().trim();
	    }
	    player.setDisplayName(dispname);
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event){
		Player player = event.getPlayer();
		String dispname = player.getDisplayName();
		if (dispname == null || dispname.equals("")) {
            dispname = event.getPlayer().getName();
        }
		Integer login_count = ConfigUtil.getPValInteger(player, "login_count");
		if(login_count == null){
			ConfigUtil.setPValInteger(player, 1, "login_count");
		} else {
			ConfigUtil.setPValInteger(player, login_count + 1, "login_count");
		}
		
		Util.debug("Show motd, for player: " + player.getName());
		String name = player.getName();
		Integer onlineplayers = Bukkit.getOnlinePlayers().length;
		String world = event.getPlayer().getWorld().getName();

		if(plugin.mainConf.getBoolean("general.use-motd", true)){
	        for (String s : plugin.mainConf.getStringList("motd")) {
	            if (s == null) {
	                continue;
	            }
	            s = s.replaceAll("(&([a-f0-9]))", "\u00A7$2");
	            s = s.replace("{name}", name);
	            s = s.replace("{dispname}", dispname);
	            s = s.replace("{online-count}", onlineplayers.toString());
	            s = s.replace("{world}", world);
	            player.sendMessage(s);
	        }
		}
		if(plugin.gui != null)
			plugin.gui.playerTab.listModel.addElement(event.getPlayer().getName());
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerJoinHigh(PlayerJoinEvent event){
		Player player = event.getPlayer();
		File datafile = new File(plugin.getDataFolder() + File.separator + "userdata" + File.separator + player.getName().toLowerCase() + ".yml");
		if(!datafile.exists()){
			Util.log("Creating data file for " + player.getName());
			try {
                FileConfiguration out = YamlConfiguration.loadConfiguration(datafile);
                out.set("name", event.getPlayer().getName());
                String dispname = event.getPlayer().getDisplayName();
                if (dispname == null || dispname.equals("")) {
                    dispname = event.getPlayer().getName();
                }
                out.set("dispname", dispname);
                out.set("ip", event.getPlayer().getAddress().getAddress().toString().replace("/", ""));
                out.save(datafile);
                Util.log("Userdata creation finished.");
            } catch (Exception e) {
                Util.log("Could not create userdata for user " + player.getName() + "!");
                Util.debug("Failed to create user file: " + e.getMessage());
            }
			if (plugin.mainConf.getBoolean("general.use-welcome-message", true)){
				Util.debug("First join for player, show welcome message.");
                String welcomemessage = plugin.mainConf.getString("messages.welcome-message", "Welcome {name} to {world}!").replaceAll("(&([a-f0-9]))", "\u00A7$2");
                welcomemessage = welcomemessage.replace("{name}", event.getPlayer().getName());
                String dispname = event.getPlayer().getDisplayName();
                if (dispname == null || dispname.equals("")) {
                    dispname = event.getPlayer().getName();
                }
                welcomemessage = welcomemessage.replace("{dispname}", dispname);
                welcomemessage = welcomemessage.replace("{world}", event.getPlayer().getWorld().getName());
                plugin.getServer().broadcastMessage(welcomemessage);
            }
		} else {
			String playerip = player.getAddress().getAddress().toString();
			Util.debug("Updating ip for player: " + player.getName() + " ip:" + playerip);
			playerip = playerip.replace("/", "");
			ConfigUtil.setPValString(event.getPlayer(), playerip, "ip");
		}
	}
}
