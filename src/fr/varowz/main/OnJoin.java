package fr.varowz.main;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnJoin implements Listener {
	
	MainMaintenance main;
	
	public OnJoin(MainMaintenance maintenance) {
		this.main = maintenance;
	}
	
	@EventHandler (priority = EventPriority.HIGH)
	public void onJoin(PlayerJoinEvent event) {
		
		Player player = event.getPlayer();
		
		if(main.getConfig().getBoolean("Maintenance") == true) {
			
			if(!player.hasPermission("maintenance.bypass")) {
				
				player.kickPlayer(main.getConfig().getString("Messages.PlayerKick").replace("&", "§"));
				
			}
			else {
				player.sendMessage(main.getConfig().getString("Messages.Prefix").replace("&", "§") + main.getConfig().getString("Messages.PlayerBypass").replace("&", "§"));
			}
			
		}
		else {
			return;
		}
	}

}
