package me.ryan7745.servermanager.commands;

import me.ryan7745.servermanager.ServerManager;
import me.ryan7745.servermanager.Util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportCommand implements CommandExecutor {
	
	ServerManager plugin;
	public TeleportCommand(ServerManager instance) {
		plugin = instance;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(cmd.getName().equalsIgnoreCase("tp")){
			if(!(sender instanceof Player)){
				Util.SendMessageNotPlayer(sender);
				return true;
			}
			
			Player player = (Player) sender;
			if(!plugin.hasPermission(player.getName(), "tp")){
				Util.SendMessageNoPerms(sender);
				return true;
			}
			
			if(args.length == 1){
				Player target = Bukkit.getPlayer(args[0]);
				if(target == null){
					player.sendMessage(Util.formatMessage("Could not find player."));
					return true;
				}
				player.sendMessage(Util.formatMessage("Teleporting to " + ChatColor.GRAY + target.getName() + ChatColor.BLUE + "."));
				player.teleport(target.getLocation());
				return true;
			}
			return false;
		} else if(cmd.getName().equalsIgnoreCase("tphere")){
			if(!(sender instanceof Player)){
				Util.SendMessageNotPlayer(sender);
				return true;
			}
			
			Player player = (Player) sender;
			if(!plugin.hasPermission(player.getName(), "tphere")){
				Util.SendMessageNoPerms(sender);
				return true;
			}
			
			if(args.length == 1){
				Player target = Bukkit.getPlayer(args[0]);
				player.sendMessage(Util.formatMessage("Teleporting " + ChatColor.GRAY + target.getName() + ChatColor.BLUE + " to you."));
				target.teleport(player.getLocation());
				return true;
			}
			return false;
		} else if(cmd.getName().equalsIgnoreCase("tpall")){
			if(!(sender instanceof Player)){
				Util.SendMessageNotPlayer(sender);
				return true;
			}
			
			Player player = (Player) sender;
			if(!plugin.hasPermission(player.getName(), "tpall")){
				Util.SendMessageNoPerms(sender);
				return true;
			}
			
			for(Player p : Bukkit.getOnlinePlayers()){
				if(p.getName().equalsIgnoreCase(player.getName())){
					p.teleport(player.getLocation());
				}
			}
			player.sendMessage(Util.formatMessage("Teleporting " + ChatColor.GRAY + "everyone" + ChatColor.BLUE + " to you."));
			return true;
		}
		return false;
	}
}
