package me.ryan7745.servermanager.commands;

import me.ryan7745.servermanager.ConfigUtil;
import me.ryan7745.servermanager.ServerManager;
import me.ryan7745.servermanager.Util;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NicknameCommand implements CommandExecutor {
	
	ServerManager plugin;
	public NicknameCommand(ServerManager instance) {
		plugin = instance;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(cmd.getName().equalsIgnoreCase("nickname")){
			if(!(sender instanceof Player)){
				Util.SendMessageNotPlayer(sender);
				return true;
			}
			
			Player player = (Player) sender;
			if(!plugin.hasPermission(player.getName(), "nickname")){
				Util.SendMessageNoPerms(sender);
				return true;
			}
			
			if(args.length > 0){
				player.setDisplayName(args[0]);
				ConfigUtil.setPValString(player, args[0], "dispname");
				player.sendMessage(Util.formatMessage("Your display name has been changed."));
			} else {
				player.setDisplayName(player.getName());
				ConfigUtil.setPValString(player, player.getName(), "dispname");
				player.sendMessage(Util.formatMessage("Your display name has been reset."));
			}
			return true;
		}
		return false;
	}
}
