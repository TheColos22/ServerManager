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

public class BanCommand implements CommandExecutor {
	ServerManager plugin;
	public BanCommand(ServerManager instance) {
		plugin = instance;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(cmd.getName().equalsIgnoreCase("ban")){
			if(sender instanceof Player){
				Player player = (Player) sender;
				if(player != null && !plugin.hasPermission(player.getName(), "ban")){
					Util.SendMessageNoPerms(sender);
					return true;
				}
			}
			if(args.length > 0){
				Player target = Bukkit.getPlayer(args[0]);
				
				if(target != null){
					if(args.length > 1){
						String banmsg = "";
						for (String s : args) { 
							if(!s.equals(args[0])){
								if(s.equals(args[1])){
									banmsg += s;
								} else {
									banmsg += " " + s;
								}
							}
						}
						target.setBanned(true);
						target.kickPlayer(banmsg);
						if(plugin.broadcastban)
							Bukkit.broadcastMessage(Util.formatBroadcast(ChatColor.BLUE + target.getName() + " was " + ChatColor.RED + "banned" + ChatColor.BLUE + " by " + sender.getName() + ". Reason: " + banmsg));
					} else {
						target.setBanned(true);
						target.kickPlayer(plugin.defaultBanMessage);
						if(plugin.broadcastban)
							Bukkit.broadcastMessage(Util.formatBroadcast(ChatColor.BLUE + target.getName() + " was " + ChatColor.RED + "banned" + ChatColor.BLUE + " by " + sender.getName() + ". Reason: " + plugin.defaultBanMessage));
					}
					return true;
				} else {
					sender.sendMessage(Util.formatMessage(ChatColor.RED + "Player not found!"));
					return true;
				}
			}
			return false;
		} else if(cmd.getName().equalsIgnoreCase("unban")){
			if(sender instanceof Player){
				Player player = (Player) sender;
				if(player != null && !plugin.hasPermission(player.getName(), "ban")){
					Util.SendMessageNoPerms(sender);
					return true;
				}
			}
			if(args.length > 0){
				OfflinePlayer target = Bukkit.getPlayer(args[0]);
				if(target == null){
					target = Bukkit.getOfflinePlayer(args[0]);
				}
				
				if(target != null){
					if(target.isBanned()){
						target.setBanned(false);
						sender.sendMessage(Util.formatMessage(ChatColor.GRAY + target.getName() + ChatColor.BLUE + " has been unbanned."));
					} else {
						sender.sendMessage(Util.formatMessage(ChatColor.GRAY + target.getName() + ChatColor.BLUE + " is not banned!"));
					}
					return true;
				} else {
					sender.sendMessage(Util.formatMessage(ChatColor.RED + "Player not found!"));
					return true;
				}
			}
			return false;
		}
		return false;
	}
}
