package fr.varowz.main.commands;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.varowz.main.MainMaintenance;
import utils.TitleManager;

public class CommandMaintenance implements CommandExecutor {
	
	private MainMaintenance main;
	
	public CommandMaintenance(MainMaintenance maintenance) {
		this.main =  maintenance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		
		if(cmd.getName().equalsIgnoreCase("Maintenance")) {
			
			if(args.length == 0) {
				if(sender instanceof Player) {
					Player player = (Player)sender;
					if(player.hasPermission("maintenance.gui")) {
						
						Inventory inv = Bukkit.createInventory(null, 27, main.getConfig().getString("Messages.GuiMaintenanceName").replace("&", "§"));
						
						ItemStack enable = new ItemStack(Material.valueOf(main.getConfig().getConfigurationSection("Items").getString("Enable.type")), main.getConfig().getConfigurationSection("Items").getInt("Enable.quantity"), (byte)main.getConfig().getConfigurationSection("Items").getInt("Enable.data"));
						ItemMeta it = enable.getItemMeta();
						it.setDisplayName(main.getConfig().getString("Items.Enable.name").replace("&", "§"));
						it.setLore(Arrays.asList(" ", main.getConfig().getString("Items.Enable.lore").replace("&", "§")));
						if(main.getConfig().getBoolean("Maintenance") == true) {
							it.addEnchant(Enchantment.DURABILITY, 1, true);
							it.addItemFlags(ItemFlag.HIDE_ENCHANTS);
						}
						enable.setItemMeta(it);
						
						ItemStack disable = new ItemStack(Material.valueOf(main.getConfig().getString("Items.Disable.type")), main.getConfig().getConfigurationSection("Items").getInt("Disable.quantity"), (byte)main.getConfig().getConfigurationSection("Items").getInt("Disable.data"));
						ItemMeta it1 = disable.getItemMeta();
						it1.setDisplayName(main.getConfig().getString("Items.Disable.name").replace("&", "§"));
						it1.setLore(Arrays.asList(" ", main.getConfig().getString("Items.Disable.lore").replace("&", "§")));
						if(main.getConfig().getBoolean("Maintenance") == false) {
							it.addEnchant(Enchantment.DURABILITY, 1, true);
							it.addItemFlags(ItemFlag.HIDE_ENCHANTS);
						}
						disable.setItemMeta(it1);
						
						inv.setItem(11, enable);
						inv.setItem(15, disable);
						
						player.openInventory(inv);
						
					}
					else {
						player.sendMessage(main.getConfig().getString("Messages.NoPerm").replace("&", "§"));
					}
				}
			}
			if(args.length == 1) {
				
				String arg = args[0];
				
				if(arg.equalsIgnoreCase("enable")) {
					
					if(sender.hasPermission("maintenance.enable")) {
						
						if(main.getConfig().getBoolean("Maintenance") == true) {
							sender.sendMessage(main.getConfig().getString("Messages.Prefix").replace("&", "§") + main.getConfig().getString("Messages.MaintenanceAlreadyEnable").replace("&", "§"));
							return true;
						}
						
						else {
							
							main.getConfig().set("Maintenance", true);
							main.saveConfig();
							
							for(Player player : Bukkit.getServer().getOnlinePlayers()) {
								if(!player.hasPermission("maintenance.bypass")) {
									player.kickPlayer(main.getConfig().getString("Messages.PlayerKick").replace("&", "§"));
								}
								else {
									player.sendMessage(main.getConfig().getString("Messages.Prefix").replace("&", "§") + main.getConfig().getString("Messages.PlayerBypass").replace("&", "§"));
									TitleManager.sendTitle(player, main.getConfig().getString("Messages.MaintenanceEnableTitle").replace("&", "§"), "", 20*3);
									
								}
							}
							
							sender.sendMessage(main.getConfig().getString("Messages.Prefix").replace("&", "§") + main.getConfig().getString("Messages.SuccesEnable").replace("&", "§"));
							Bukkit.getConsoleSender().sendMessage(main.getConfig().getString("Messages.Prefix").replace("&", "§") + main.getConfig().getString("Messages.MaintenanceEnableLog").replace("&", "§"));
							
						}
						
					}
					else {
						sender.sendMessage(main.getConfig().getString("Messages.NoPerm").replace("&", "§"));
					}
						
				}
				if(arg.equalsIgnoreCase("disable")) {
						
					if(sender.hasPermission("maintenance.disable")) {
						
						if(main.getConfig().getBoolean("Maintenance") == false) {
							sender.sendMessage(main.getConfig().getString("Messages.Prefix").replace("&", "§") + main.getConfig().getString("Messages.MaintenanceAlreadyDisable").replace("&", "§"));
							return true;
						}
						else {
							main.getConfig().set("Maintenance", false);
							main.saveConfig();
							sender.sendMessage(main.getConfig().getString("Messages.Prefix").replace("&", "§") + main.getConfig().getString("Messages.SuccesDisable").replace("&", "§"));
							Bukkit.getConsoleSender().sendMessage(main.getConfig().getString("Messages.Prefix").replace("&", "§") + main.getConfig().getString("Messages.MaintenanceDisableLog").replace("&", "§"));
							
						}
						
					}
					else {
						sender.sendMessage(main.getConfig().getString("Messages.NoPerm").replace("&", "§"));
					}
						
				}
				if(arg.equalsIgnoreCase("reload")) {
					
					if(sender.hasPermission("maintenance.reload")) {
						
						main.reloadConfig();
						
						Bukkit.getConsoleSender().sendMessage(main.getConfig().getString("Messages.Prefix").replace("&", "§") + "§aConfig reloaded !");
						sender.sendMessage(main.getConfig().getString("Messages.Prefix").replace("&", "§") + "§aConfig reloaded !");
						
					}
					else {
						sender.sendMessage(main.getConfig().getString("Messages.NoPerm").replace("&", "§"));
					}
					
				}
			}
		}
		
		return false;
	}

}
