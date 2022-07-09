package fr.varowz.main;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import fr.varowz.main.commands.CommandMaintenance;

public class MainMaintenance extends JavaPlugin{
	
	@Override
	public void onEnable() {
		
		getCommand("Maintenance").setExecutor(new CommandMaintenance(this));
		getServer().getPluginManager().registerEvents(new OnJoin(this), this);
		getServer().getPluginManager().registerEvents(new OnInteract(this), this);
		
		saveDefaultConfig();
		
		Bukkit.getConsoleSender().sendMessage(getConfig().getString("Messages.Prefix").replace("&", "§") + "§aPlugin §7[§e2.0.0§7] §ais online.");
		Bukkit.getConsoleSender().sendMessage(getConfig().getString("Messages.Prefix").replace("&", "§") + "§aPlugin §7[§e2.0.0§7] §cDev by VaroWz.");
		Bukkit.getConsoleSender().sendMessage(getConfig().getString("Messages.Prefix").replace("&", "§") + "§aPlugin §7[§e2.0.0§7] §9Discord: https://discord.gg/SbKrKehCpq.");
		
		
	}
	
	@Override
	public void onDisable() {
		
		Bukkit.getConsoleSender().sendMessage(getConfig().getString("Messages.Prefix").replace("&", "§") + "§cPlugin §7[§e2.0.0§7] §cis offline.");

		
	}

}
