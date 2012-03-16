package me.ryan7745.servermanager.listeners;

import java.io.File;
import java.util.Date;

import me.ryan7745.servermanager.ConfigUtil;
import me.ryan7745.servermanager.ServerManager;
import me.ryan7745.servermanager.Util;
import me.ryan7745.servermanager.commands.BackCommand;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
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

public class PlayerListener implements Listener {
	
	ServerManager plugin;
	public PlayerListener(ServerManager instance){
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
		Util.debug("Update seend for player: " + event.getPlayer().getName());
        ConfigUtil.setPValString(event.getPlayer(), new Date().toString(), "seen");
    }

    @EventHandler
    public void onKick(PlayerKickEvent event) {
    	Util.debug("Update seend for player: " + event.getPlayer().getName());
        ConfigUtil.setPValString(event.getPlayer(), new Date().toString(), "seen");
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
    	
    	String chat = event.getMessage();
    	Player player = event.getPlayer();
    	
    	chat = chat.replaceAll("(&([a-f0-9]))", "\u00A7$2");
    	if(player.isOp()){
    		event.setFormat(ChatColor.GRAY + "[" + ChatColor.RED + player.getName() + ChatColor.GRAY + "]: " + ChatColor.WHITE + chat);
    	} else {
    		event.setFormat(ChatColor.GRAY + "[" + player.getName() + "]: " + ChatColor.WHITE + chat);
    	}
    }
    
    @EventHandler
    public void onEntityDamage(EntityDamageEvent event){
    	if(event.isCancelled()) return;
    	if(event.getEntity() instanceof Player){
    		//Player player = (Player) event.getEntity();
    		
    	}
    }
    
    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerLoginHigh(PlayerLoginEvent event){
    	Player player = event.getPlayer();
    	if(event.getResult().equals(Result.KICK_BANNED)){
			event.setKickMessage(plugin.disconnectMsgBanned);
			return;
		} else if(event.getResult().equals(Result.KICK_WHITELIST)){
			event.setKickMessage(plugin.disconnectMsgWhitelist);
			Bukkit.broadcastMessage(ChatColor.GRAY + player.getName() + ChatColor.RED + " tried to join, Disconnected reason: Not on whitelist.");
			return;
		} else if(event.getResult().equals(Result.KICK_FULL)){
			event.setKickMessage(plugin.disconnectMsgFull);
			Bukkit.broadcastMessage(ChatColor.GRAY + player.getName() + ChatColor.RED + " tried to join, Disconnected reason: Server full.");
			return;
		}
    }
    
    @EventHandler
    public void onServerListPing(ServerListPingEvent event) {
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
        for (String s : plugin.motd) {
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
			if (plugin.useWelcome){
				Util.debug("First join for player, show welcome message.");
                String welcomemessage = plugin.welcomeMessage;
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