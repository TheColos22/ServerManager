package me.ryan7745.servermanager.commands;

import me.ryan7745.servermanager.ConfigUtil;
import me.ryan7745.servermanager.ServerManager;
import me.ryan7745.servermanager.Util;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ServerManagerCommand implements CommandExecutor {
	
	ServerManager plugin;
	public ServerManagerCommand(ServerManager instance) {
		plugin = instance;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(cmd.getName().equalsIgnoreCase("servermanager")){
			if(sender instanceof Player){
				Player player = (Player) sender;
				if(player != null && !plugin.hasPermission(player.getName(), "info")){
					Util.SendMessageNoPerms(sender);
					return true;
				}
			}

			ConfigUtil.loadConfig("config", "config");
			sender.sendMessage(Util.formatMessage("Config reloaded."));
			return true;
		}
		return false;
	}
}
