package fr.varowz.main;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class OnInteract implements Listener {
	
	MainMaintenance main;
	
	public OnInteract(MainMaintenance maintenance) {
		this.main = maintenance;
	}

	@EventHandler
	public void onClick(InventoryClickEvent event) {
		
		Player player = (Player) event.getWhoClicked();
		Inventory inv = event.getInventory();
		ItemStack current = event.getCurrentItem();
		
		if(current==null) {return;}
		
		if(inv.getName().equalsIgnoreCase(main.getConfig().getString("Messages.GuiMaintenanceName").replace("&", "§"))) {
		
			event.setCancelled(true);
			if(current.getType() == Material.valueOf(main.getConfig().getConfigurationSection("Items").getString("Enable.type"))
					&& current.hasItemMeta()
					&& current.getItemMeta().hasDisplayName()
					&& current.getItemMeta().getDisplayName().equalsIgnoreCase(main.getConfig().getString("Items.Enable.name").replace("&", "§"))){
				
				player.closeInventory();
				Bukkit.dispatchCommand(player, "maintenance enable");
				
			}
			if(current.getType() == Material.valueOf(main.getConfig().getConfigurationSection("Items").getString("Disable.type"))
					&& current.hasItemMeta()
					&& current.getItemMeta().hasDisplayName()
					&& current.getItemMeta().getDisplayName().equalsIgnoreCase(main.getConfig().getString("Items.Disable.name").replace("&", "§"))){
				
				player.closeInventory();
				Bukkit.dispatchCommand(player, "maintenance disable");
			}	
		}
		
	}
	
}
