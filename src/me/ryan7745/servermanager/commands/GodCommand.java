package me.ryan7745.servermanager.commands;

import me.ryan7745.servermanager.ConfigUtil;
import me.ryan7745.servermanager.ServerManager;
import me.ryan7745.servermanager.Util;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GodCommand implements CommandExecutor {

	ServerManager plugin;
	public GodCommand(ServerManager instance) {
		plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(cmd.getName().equalsIgnoreCase("god")){
			if(!(sender instanceof Player)){
				Util.SendMessageNotPlayer(sender);
				return true;
			}
			
			Player player = (Player) sender;
			if(!plugin.hasPermission(player.getName(), "god")){
				Util.SendMessageNoPerms(sender);
				return true;
			}
			
			if(args.length == 0){
				if(ConfigUtil.getPVal(player, "god") != null){
					if(ConfigUtil.getPValBoolean(player, "god")){
						ConfigUtil.setPValBoolean(player, false, "god");
						player.sendMessage(Util.formatMessage("You are no longer God."));
					} else {
						ConfigUtil.setPValBoolean(player, true, "god");
						player.sendMessage(Util.formatMessage("You are now a God."));
					}
				} else {
					ConfigUtil.setPValBoolean(player, true, "god");
					player.sendMessage(Util.formatMessage("You are now a God."));
				}
			}
			
			return true;
		}
		return false;
	}

}
