package com.gmail.justbru00.epic.ban.requests.listeners;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import com.gmail.justbru00.epic.ban.requests.main.Main;
import com.gmail.justbru00.epic.ban.requests.utils.BanRequest;
import com.gmail.justbru00.epic.ban.requests.utils.GUIManager;
import com.gmail.justbru00.epic.ban.requests.utils.Messager;
import com.gmail.justbru00.epic.ban.requests.utils.TimeGetter;

import net.md_5.bungee.api.ChatColor;

public class GUIListener implements Listener {

	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		
		if (e.getInventory() != null) {			
			String name = e.getInventory().getName();
			if (name.equals(Messager.color("&bMain Menu"))) {				
				e.setCancelled(true);
				if (e.getCurrentItem() != null) {
					if (e.getCurrentItem().getType() != Material.AIR) {
						if (e.getCurrentItem().getItemMeta().getDisplayName().equals(Messager.color("&cCurrent Ban Requests"))) {
							e.getWhoClicked().openInventory(GUIManager.pending((Player) e.getWhoClicked()));
							return;
						} else if (e.getCurrentItem().getItemMeta().getDisplayName().equals(Messager.color("&7Archived Requests"))) {
							e.getWhoClicked().openInventory(GUIManager.archived((Player) e.getWhoClicked()));
							return;
						}
					}
				}
			} else if (name.equals(Messager.color("&cPending Ban Requests"))) {
				e.setCancelled(true);
				
				if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) {
					return;
				}
				
				if (e.getCurrentItem().getType() == Material.ARROW) {
					e.getWhoClicked().openInventory(GUIManager.mainMenu());
					return;
				} else if (e.getCurrentItem().getType() == Material.PAPER) {					
					e.getWhoClicked().openInventory(GUIManager.acceptOrDeny(e.getCurrentItem()));		
					return;
				}			
				
			} else if (name.equals(Messager.color("&7Archived Requests"))) {
				e.setCancelled(true);
				e.getWhoClicked().openInventory(GUIManager.archived((Player) e.getWhoClicked()));
				return;
			}  else if (name.contains(Messager.color("&eRequest #"))) {
				e.setCancelled(true);		
				
				if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) {
					return;
				}
				
				if (e.getCurrentItem().getType() == Material.ARROW) {
					e.getWhoClicked().openInventory(GUIManager.pending((Player) e.getWhoClicked()));
					return;
				} else if (e.getCurrentItem().getType() == Material.EMERALD_BLOCK) {
					
					String temp = ChatColor.stripColor(e.getInventory().getItem(22).getItemMeta().getDisplayName());
					int clickedId = Integer.parseInt(temp.substring(9, temp.length()));
					
					BanRequest br = new BanRequest(clickedId);
					
					String banCommand = Main.getInstance().getConfig().getString("command_to_run");
					banCommand = banCommand.replace("{player}", Bukkit.getOfflinePlayer(UUID.fromString(br.playerToBanUUID)).getName());
					banCommand = banCommand.replace("{uuid}", br.playerToBanUUID);
					banCommand = banCommand.replace("{reason}", br.banReason);

					Bukkit.dispatchCommand(e.getWhoClicked(), banCommand);
					
					br.accepted = true;
					br.closed = true;
					br.denied = false;
					br.closerUUID = e.getWhoClicked().getUniqueId().toString();
					br.timeClosed = System.currentTimeMillis();
					br.timeClosedFormatted = TimeGetter.getCurrentTimeStamp();
					br.writeToConfig();
					
					e.getInventory().setItem(9, new ItemStack(Material.AIR));
					e.getInventory().setItem(10, new ItemStack(Material.AIR));
					e.getInventory().setItem(11, new ItemStack(Material.AIR));
					
					e.getInventory().setItem(15, new ItemStack(Material.AIR));
					e.getInventory().setItem(16, new ItemStack(Material.AIR));
					e.getInventory().setItem(17, new ItemStack(Material.AIR));
					
					e.getInventory().setItem(18, new ItemStack(Material.AIR));
					e.getInventory().setItem(19, new ItemStack(Material.AIR));
					e.getInventory().setItem(20, new ItemStack(Material.AIR));
					
					e.getInventory().setItem(24, new ItemStack(Material.AIR));
					e.getInventory().setItem(25, new ItemStack(Material.AIR));
					e.getInventory().setItem(26, new ItemStack(Material.AIR));
					
					e.getInventory().setItem(27, new ItemStack(Material.AIR));
					e.getInventory().setItem(28, new ItemStack(Material.AIR));
					e.getInventory().setItem(29, new ItemStack(Material.AIR));
					
					e.getInventory().setItem(33, new ItemStack(Material.AIR));
					e.getInventory().setItem(34, new ItemStack(Material.AIR));
					e.getInventory().setItem(35, new ItemStack(Material.AIR));
					
					Messager.msgPlayer("&aAccepted the ban request.", (Player) e.getWhoClicked()); 
					
					return;
				} else if (e.getCurrentItem().getType() == Material.REDSTONE_BLOCK) {
					String temp = ChatColor.stripColor(e.getInventory().getItem(22).getItemMeta().getDisplayName());
					int clickedId = Integer.parseInt(temp.substring(9, temp.length()));
					
					BanRequest br = new BanRequest(clickedId);
					
					br.accepted = false;
					br.closed = true;
					br.denied = true;
					br.closerUUID = e.getWhoClicked().getUniqueId().toString();
					br.timeClosed = System.currentTimeMillis();
					br.timeClosedFormatted = TimeGetter.getCurrentTimeStamp();
					br.writeToConfig();
					
					e.getInventory().setItem(9, new ItemStack(Material.AIR));
					e.getInventory().setItem(10, new ItemStack(Material.AIR));
					e.getInventory().setItem(11, new ItemStack(Material.AIR));
					
					e.getInventory().setItem(15, new ItemStack(Material.AIR));
					e.getInventory().setItem(16, new ItemStack(Material.AIR));
					e.getInventory().setItem(17, new ItemStack(Material.AIR));
					
					e.getInventory().setItem(18, new ItemStack(Material.AIR));
					e.getInventory().setItem(19, new ItemStack(Material.AIR));
					e.getInventory().setItem(20, new ItemStack(Material.AIR));
					
					e.getInventory().setItem(24, new ItemStack(Material.AIR));
					e.getInventory().setItem(25, new ItemStack(Material.AIR));
					e.getInventory().setItem(26, new ItemStack(Material.AIR));
					
					e.getInventory().setItem(27, new ItemStack(Material.AIR));
					e.getInventory().setItem(28, new ItemStack(Material.AIR));
					e.getInventory().setItem(29, new ItemStack(Material.AIR));
					
					e.getInventory().setItem(33, new ItemStack(Material.AIR));
					e.getInventory().setItem(34, new ItemStack(Material.AIR));
					e.getInventory().setItem(35, new ItemStack(Material.AIR));
					
					Messager.msgPlayer("&aDenied the ban request.", (Player) e.getWhoClicked()); 
					
					return;
				}
				
				return;
			}
		}
		
	}
	
}
