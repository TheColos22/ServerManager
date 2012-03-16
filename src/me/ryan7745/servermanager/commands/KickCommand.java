package me.ryan7745.servermanager.commands;

import me.ryan7745.servermanager.ServerManager;
import me.ryan7745.servermanager.Util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KickCommand implements CommandExecutor {
	ServerManager plugin;
	public KickCommand(ServerManager instance) {
		plugin = instance;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {	
		if(cmd.getName().equalsIgnoreCase("kick")){
			if(sender instanceof Player){
				Player player = (Player) sender;
				if(!plugin.hasPermission(player.getName(), "kick")){
					Util.SendMessageNoPerms(sender);
					return true;
				}
			}
			if(args.length > 0){
				Player player = Bukkit.getPlayer(args[0]);
				if(args.length > 1){
					String kickmsg = "";
					for (String s : args) { 
						if(!s.equals(args[0])){
							if(s.equals(args[1])){
								kickmsg += s;
							} else {
								kickmsg += " " + s;
							}
						}
					}
					player.kickPlayer(kickmsg);
					if(plugin.mainConf.getBoolean("general.broadcast-kick", true))
						Bukkit.broadcastMessage(Util.formatBroadcast(ChatColor.BLUE + player.getName() + " was kicked by " + sender.getName() + ". Reason: " + kickmsg));
				} else {
					String kickmsg = plugin.mainConf.getString("messages.default-kick-message", "&7You have been kicked!").replaceAll("(&([a-f0-9]))", "\u00A7$2");
					player.kickPlayer(kickmsg);
					if(plugin.mainConf.getBoolean("general.broadcast-kick", true))
						Bukkit.broadcastMessage(Util.formatBroadcast(ChatColor.BLUE + player.getName() + " was kicked by " + sender.getName() + ". Reason: " + kickmsg));
				}
				return true;
			}
			return false;
		} else if(cmd.getName().equalsIgnoreCase("kickall")){
			if(sender instanceof Player){
				Player player = (Player) sender;
				if(!plugin.hasPermission(player.getName(), "kickall")){
					Util.SendMessageNoPerms(sender);
					return true;
				}
			}
			String kickmsg = plugin.mainConf.getString("messages.default-kick-message", "&7You have been kicked!").replaceAll("(&([a-f0-9]))", "\u00A7$2");
			if(args.length > 0){
				kickmsg = "";
				for (String s : args) { 
					if(s.equals(args[0])){
						kickmsg += s;
					} else {
						kickmsg += " " + s;
					}
				}
			}
			for(Player p : Bukkit.getOnlinePlayers()){
				if(plugin.hasPermission(p.getName(), "kickall.imune")) continue;
				p.kickPlayer(kickmsg);
			}
			
			return true;
		}
		return false;
	}
}
