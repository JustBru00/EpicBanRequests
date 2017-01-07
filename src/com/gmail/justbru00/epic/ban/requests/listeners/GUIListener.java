package com.gmail.justbru00.epic.ban.requests.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.gmail.justbru00.epic.ban.requests.utils.GUIManager;
import com.gmail.justbru00.epic.ban.requests.utils.Messager;

public class GUIListener implements Listener {

	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		
		if (e.getInventory() != null) {
			Messager.msgConsole("Player clicked");
			String name = e.getInventory().getName();
			if (name.equals(Messager.color("&bMain Menu"))) {
				Messager.msgConsole("in Main menu");
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
				// TODO CLOSE BAN REQUEST AND ACCEPT OR DENY
				return;
			}
		}
		
	}
	
}
