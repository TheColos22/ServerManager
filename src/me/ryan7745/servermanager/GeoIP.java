package me.ryan7745.servermanager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.GZIPInputStream;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.maxmind.geoip.Location;
import com.maxmind.geoip.LookupService;
import com.maxmind.geoip.regionName;

public class GeoIP implements Listener{
	static ServerManager plugin;

	public GeoIP(ServerManager instance) {
		plugin = instance;
	}
	
	File databaseFile;
	
	LookupService ls;
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event){
		Player player = event.getPlayer();
		InetAddress address = event.getPlayer().getAddress().getAddress();
		StringBuilder sb = new StringBuilder();

		Location loc = ls.getLocation(address);
		
		if(address.isAnyLocalAddress()){
			if(plugin.mainConf.getBoolean("general.show-location-message", true)){
				Bukkit.getServer().broadcastMessage(ChatColor.GRAY + player.getName() + ChatColor.BLUE + " joined from a localip");
			}
		}
		
		if(loc == null) return;
		
		if(loc.city != null){
			sb.append(loc.city).append(", ");
		}
		String region = regionName.regionNameByCode(loc.countryCode, loc.region);
		if(region != null){
			sb.append(region).append(", ");
		}
		sb.append(loc.countryName);
		
		String worldLocation = sb.toString();
		Util.debug("Updating location for player: " + player.getName() + " location:" + worldLocation);
		ConfigUtil.setPValString(player, worldLocation, "real-world-location");
		
		if(plugin.mainConf.getBoolean("general.show-location-message", true)){
			Bukkit.getServer().broadcastMessage(ChatColor.GRAY + player.getName() + ChatColor.BLUE + " comes from " + ChatColor.RED + worldLocation);
		}
	}
	
	public void load(){
		databaseFile = new File(plugin.getDataFolder(), "GeoIPCity.dat");
		
		if(!databaseFile.exists() && plugin.mainConf.getBoolean("geoip.download-if-missing", true)){
			downloadDatabase();
		}
		
		try
		{
			ls = new LookupService(databaseFile);
		}
		catch (IOException ex)
		{
			Util.log("Cannot read database file!");
		}
	}
	
	private void downloadDatabase()
	{
		try
		{
			String url = plugin.mainConf.getString("geoip.download-url");
			if (url == null || url.isEmpty()){
				Util.log("Invalid URL");
				return;
			}
			
			Util.log("Downloading geoip database.");
			
			URL downloadUrl = new URL(url);
			URLConnection conn = downloadUrl.openConnection();
			conn.setConnectTimeout(10000);
			conn.connect();
			InputStream input = conn.getInputStream();
			if (url.endsWith(".gz"))
			{
				input = new GZIPInputStream(input);
			}
			OutputStream output = new FileOutputStream(databaseFile);
			byte[] buffer = new byte[2048];
			int length = input.read(buffer);
			while (length >= 0)
			{
				output.write(buffer, 0, length);
				length = input.read(buffer);
			}
			output.close();
			input.close();
			
			Util.log("Downloaded GeoIP database.");
		}
		catch (MalformedURLException ex)
		{
			Util.log("Invalid URL for geoip database.");
			return;
		}
		catch (IOException ex)
		{
			Util.log("Geoip download failed.");
		}
	}
}
