package me.ryan7745.servermanager.commands;

import me.ryan7745.servermanager.ConfigUtil;
import me.ryan7745.servermanager.ServerManager;
import me.ryan7745.servermanager.Util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WhoCommand implements CommandExecutor {
	
	ServerManager plugin;
	public WhoCommand(ServerManager instance) {
		plugin = instance;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(cmd.getName().equalsIgnoreCase("who")){
			if(sender instanceof Player){
				Player player = (Player) sender;
				if(player != null && !plugin.hasPermission(player.getName(), "who")){
					Util.SendMessageNoPerms(sender);
					return true;
				}
			}
			
			if(args.length == 1){
				OfflinePlayer oplayer = Bukkit.getPlayer(args[0]);
				if(ConfigUtil.getPConfExists(oplayer)){
					String ip = ConfigUtil.getPValString(oplayer, "ip");
					String seen = ConfigUtil.getPValString(oplayer, "seen");
					Integer login_count = ConfigUtil.getPValInteger(oplayer, "login_count");
					sender.sendMessage(Util.formatMessage(ChatColor.GRAY + "---------------" + ChatColor.BLUE + "Who is " + oplayer.getName() + ChatColor.GRAY + "---------------"));
					sender.sendMessage(Util.formatMessage("IP: " + ChatColor.GRAY + ip));
					sender.sendMessage(Util.formatMessage("Last Seen: " + ChatColor.GRAY + seen));
					sender.sendMessage(Util.formatMessage("No. of times on server: " + ChatColor.GRAY + login_count));
				}
				return true;
			}
			return false;
		}
		return false;
	}
}
