package com.gmail.justbru00.epic.ban.requests.utils;

import java.util.Arrays;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.gmail.justbru00.epic.ban.requests.main.Main;

public class BanRequest {

	public long timeOpened = -1;
	public long timeClosed = -1;
	public String openerUUID = "null";
	public String closerUUID = "null";
	public String timeOpenedFormatted = "null";
	public String timeClosedFormatted = "null";
	public boolean closed = false;
	public boolean accepted = false;
	public boolean denied = false;
	public String banReason = "null";
	public int id = -1;	
	public String playerToBanUUID = "null";
	
	 /**
	  * Use for opening a new ban request.	  
	  * @param _openerUUID
	  * @param banReason
	  * @param banLengthDay
	  * @param banLengthMins
	  */
	public BanRequest(String _openerUUID, String _banReason, String _playerToBanUUID) {
		id = Main.getInstance().getConfig().getInt("current_id") + 1;
		Main.getInstance().getConfig().set("current_id", Main.getInstance().getConfig().getInt("current_id") + 1);
		Main.getInstance().saveConfig();
		timeOpened = System.currentTimeMillis();
		timeOpenedFormatted = TimeGetter.getCurrentTimeStamp();
		timeClosed = -1;
		openerUUID = _openerUUID;
		closerUUID = "null";
		banReason = _banReason;
		playerToBanUUID = _playerToBanUUID;
		}
	
	
	public ItemStack getItem() {
		ItemStack is = new ItemStack(Material.PAPER);
		ItemMeta im = is.getItemMeta();
		
		/**
		 * Item:
		 * 
		 * Name = Request #x
		 * Lore 1: Requested by: _name_
		 * Lore :  Ban: 
		 * Lore 2: Reason: reason
		 * Lore 3: Time: time 
		 */
		
		im.setDisplayName(Messager.color("&eRequest #" + id));
		im.setLore(Arrays.asList(Messager.color("&eRequested by: &7" + Bukkit.getOfflinePlayer(UUID.fromString(openerUUID)).getName()), 
				Messager.color("&eBan: &7" + Bukkit.getOfflinePlayer(UUID.fromString(playerToBanUUID)).getName()),
				Messager.color("&eReason: &7" + banReason), 
				Messager.color("&eRequested at: &7" + timeOpenedFormatted)));
		
		is.setItemMeta(im);
		
		return is;
	}
	
	public ItemStack getArchivedItem() {
		ItemStack is = new ItemStack(Material.PAPER);
		ItemMeta im = is.getItemMeta();
		
		/**
		 * Item:
		 * 
		 * Name = Request #x
		 * Lore 1: Requested by: _name_
		 * Lore 2: Reason: reason
		 * Lore 3: Requested at: time 
		 * Lore 4: Closed by: name
		 * Lore 5: Closed at: time
		 * Lore 6: Request was: accept/deny
		 */
		
		im.setDisplayName(Messager.color("&eRequest #" + id));
		String temp = null;
		if (accepted) {
			temp = "&aAccepted";
		} else  if (denied) {
			temp = "&cDenied";
		}
		im.setLore(Arrays.asList(Messager.color("&eRequested by: &7" + Bukkit.getOfflinePlayer(UUID.fromString(openerUUID)).getName()), Messager.color("&eReason: &7" + banReason), 
				Messager.color("&eRequested at: &7" + timeOpenedFormatted), Messager.color("&eClosed by: &7" + Bukkit.getOfflinePlayer(UUID.fromString(closerUUID)).getName()), 
				Messager.color("&eClosed at: &7" + timeClosedFormatted), Messager.color("&eRequest was: " + temp)));
		
		is.setItemMeta(im);
		
		return is;
	}
	
	
	public BanRequest(int _id) {
		id = _id;
		FileConfiguration c = Main.getInstance().getConfig();
		timeOpened = c.getLong("ban_requests." + id + ".timeOpened");
		timeClosed = c.getLong("ban_requests." + id + ".timeClosed");
		openerUUID = c.getString("ban_requests." + id + ".openerUUID");
		closerUUID = c.getString("ban_requests." + id + ".closerUUID");
		timeOpenedFormatted = c.getString("ban_requests." + id + ".timeOpenedFormatted");
		timeClosedFormatted = c.getString("ban_requests." + id + ".timeClosedFormatted");
		closed = c.getBoolean("ban_requests." + id + ".closed");
		accepted = c.getBoolean("ban_requests." + id + ".accepted");
		denied = c.getBoolean("ban_requests." + id + ".denied");
		banReason = c.getString("ban_requests." + id + ".banReason");
		playerToBanUUID = c.getString("ban_requests." + id + ".playerToBanUUID");
	}
	
	public void writeToConfig() {
		FileConfiguration c = Main.getInstance().getConfig();
		c.set("ban_requests." + id + ".timeOpened", timeOpened);
		c.set("ban_requests." + id + ".timeClosed", timeClosed);
		c.set("ban_requests." + id + ".openerUUID", openerUUID);
		c.set("ban_requests." + id + ".closerUUID", closerUUID);
		c.set("ban_requests." + id + ".timeOpenedFormatted", timeOpenedFormatted);
		c.set("ban_requests." + id + ".timeClosedFormatted", timeClosedFormatted);
		c.set("ban_requests." + id + ".closed", closed);
		c.set("ban_requests." + id + ".accepted", accepted);
		c.set("ban_requests." + id + ".denied", denied);
		c.set("ban_requests." + id + ".banReason", banReason);
		c.set("ban_requests." + id + ".playerToBanUUID", playerToBanUUID);
		Main.getInstance().saveConfig();
	}
	
	
}
