package com.gmail.justbru00.epic.ban.requests.utils;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.gmail.justbru00.epic.ban.requests.main.Main;

import net.md_5.bungee.api.ChatColor;

public class GUIManager {
	
	
	public static Inventory acceptOrDeny(ItemStack clickedRequest) {
		
		String temp = ChatColor.stripColor(clickedRequest.getItemMeta().getDisplayName());
		int clickedId = Integer.parseInt(temp.substring(9, temp.length()));
		
		BanRequest br = new BanRequest(clickedId);
		
		Inventory i = Bukkit.createInventory(null, 54, Messager.color("&eRequest #" + br.id));
		
		i.setItem(22, br.getItem());
		
		ItemStack em = new ItemStack(Material.EMERALD_BLOCK);
		ItemMeta emM = em.getItemMeta();
		emM.setDisplayName(Messager.color("&aAccept Ban Request."));
		emM.setLore(Arrays.asList(Messager.color("&7This can only be undone manually.")));
		em.setItemMeta(emM);
		
		ItemStack red = new ItemStack(Material.REDSTONE_BLOCK);
		ItemMeta redM = red.getItemMeta();
		redM.setDisplayName(Messager.color("&cDeny Ban Request."));
		redM.setLore(Arrays.asList(Messager.color("&7This can only be undone manually.")));
		red.setItemMeta(redM);
		
		i.setItem(9, em);
		i.setItem(10, em);
		i.setItem(11, em);
		
		i.setItem(15, red);
		i.setItem(16, red);
		i.setItem(17, red);
		
		i.setItem(18, em);
		i.setItem(19, em);
		i.setItem(20, em);
		
		i.setItem(24, red);
		i.setItem(25, red);
		i.setItem(26, red);
		
		i.setItem(27, em);
		i.setItem(28, em);
		i.setItem(29, em);
		
		i.setItem(33, red);
		i.setItem(34, red);
		i.setItem(35, red);
		
		ItemStack back = new ItemStack(Material.ARROW);
		ItemMeta im = back.getItemMeta();
		im.setDisplayName(Messager.color("&cBack"));
		back.setItemMeta(im);		
		i.setItem(45, back);
		
		return i;
	}

	public static Inventory mainMenu() {
		Inventory i = Bukkit.createInventory(null, 54, Messager.color("&bMain Menu"));
		
		
		ItemStack pending = new ItemStack(Material.BARRIER);
		ItemMeta pendingMeta = pending.getItemMeta();
		pendingMeta.setDisplayName(Messager.color("&cCurrent Ban Requests"));
		pendingMeta.setLore(Arrays.asList(Messager.color("&7Pending: " + Main.getPendingRequests())));
		pending.setItemMeta(pendingMeta);
		
		i.setItem(20, pending);
		
		ItemStack archive = new ItemStack(Material.CHEST);
		ItemMeta archiveMeta = archive.getItemMeta();
		archiveMeta.setDisplayName(Messager.color("&7Archived Requests"));
		archive.setItemMeta(archiveMeta);
		
		i.setItem(24, archive);
		
		
		return i;
	}
	
	public static Inventory pending(Player player) {
		Inventory i = Bukkit.createInventory(null, 54, Messager.color("&cPending Ban Requests"));
		
		ItemStack back = new ItemStack(Material.ARROW);
		ItemMeta im = back.getItemMeta();
		im.setDisplayName(Messager.color("&cBack"));
		back.setItemMeta(im);		
		i.setItem(45, back);
		
		int tillFull = 0;
		
		for (int x = 0; x <= Main.getInstance().getConfig().getInt("current_id"); x++) {			
			if (tillFull >= 53) {
				Messager.msgPlayer("&cAll of the requests could not fit in the GUI. Please accept or deny some requests.", player);
				break;
			}
			BanRequest br = new BanRequest(x);
			if (!br.closed) {
				i.addItem(br.getItem());
				tillFull++;
			}
		}
		
		return i;		
	}
	
	public static Inventory archived(Player player) {
		Inventory i = Bukkit.createInventory(null, 54, Messager.color("&7Archived Requests"));
		
		
		ItemStack back = new ItemStack(Material.ARROW);
		ItemMeta im = back.getItemMeta();
		im.setDisplayName(Messager.color("&cBack"));
		back.setItemMeta(im);		
		i.setItem(45, back);
		
		int tillFull = 0;
		for (int x = Main.getInstance().getConfig().getInt("current_id"); x <= 0; x--) {	
			
			if (tillFull >= 53) {
				Messager.msgPlayer("&cAll of the archived requests could not fit in the GUI. Showing the newest first.", player);
				break;
			}
			BanRequest br = new BanRequest(x);
			if (br.closed) {
				i.addItem(br.getArchivedItem());
				tillFull++;
			}
		}
		
		return i;
	}
	
}
