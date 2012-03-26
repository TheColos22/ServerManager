package me.ryan7745.servermanager.gui;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.ryan7745.servermanager.ServerManager;

public class ActionHandler {
	
	ServerManagerGUI smg;
	public ActionHandler(ServerManagerGUI smginstance) {
		smg = smginstance;
		
		Logger logger = Logger.getLogger("Minecraft");
		logger.addHandler(new LogHandler(smg));
	}

	public static void sendConsoleMessage(String input){
		if(input.length() == 0 || input.equals("")) return;
		if(input.startsWith("/")){
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), input.split("/")[1]);
		} else {
			Bukkit.broadcastMessage("<Console>: " + input);
		}
	}

	public static void timeDay() {
		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "time day");
	}
	
	public static void timeNight() {
		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "time night");
	}
	
	public static void weatherSun() {
		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "weather sun");
	}
	
	public static void weatherRain() {
		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "weather rain");
	}
	
	public static void weatherThunder() {
		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "weather thunder");
	}

	public static void OpPlayer(Object pObjName) {
		Player p = Bukkit.getPlayer((String) pObjName);
		if(p != null){
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "op " + p.getName());
		}
	}
	
	public static void DeOpPlayer(Object pObjName) {
		Player p = Bukkit.getPlayer((String) pObjName);
		if(p != null){
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "deop " + p.getName());
		}
	}
	
	public static void BanPlayer(Object pObjName) {
		Player p = Bukkit.getPlayer((String) pObjName);
		if(p != null){
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "ban " + p.getName());
		}
	}

	public static void kickPlayer(Object pObjName) {
		Player p = Bukkit.getPlayer((String) pObjName);
		if(p != null){
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "kick " + p.getName());
		}
	}
	
}
