package com.gmail.justbru00.epic.ban.requests.utils;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.justbru00.epic.ban.requests.main.Main;

public class Messager {

	
	public static String color(String toColor) {
		return ChatColor.translateAlternateColorCodes('&', toColor);
	}
	
	public static void msgPlayer(String msg, Player player) {
		player.sendMessage(Messager.color(Main.prefix + msg));
	}
	
	public static void msgConsole(String msg) {
		if (Main.console == null) {
			Main.logger.info(ChatColor.stripColor(Messager.color(Main.prefix + msg)));
		} else {
			Main.console.sendMessage(Messager.color(Main.prefix + msg));
		}
	}
	
	public static void msgSender(String msg, CommandSender sender) {
		sender.sendMessage(Messager.color(Main.prefix + msg));
	}
	
	public static void msgPlayerWithPresetMessage(String messageKey, Player p) {
		String msg = Main.messages.getString(messageKey);
		if (msg == null) {
			msg = "Failed to get preset message from key: " + messageKey;
		}
		msgPlayer(msg, p);
	}
	
	public static void msgConsoleWithPresetMessage(String messageKey) {
		String msg = Main.messages.getString(messageKey);
		if (msg == null) {
			msg = "Failed to get preset message from key: " + messageKey;
		}
		msgConsole(msg);
	}
	
	public static void msgSenderWithPresetMessage(String messageKey, CommandSender s) {
		String msg = Main.messages.getString(messageKey);
		if (msg == null) {
			msg = "Failed to get preset message from key: " + messageKey;
		}
		msgSender(msg, s);
	}
	
	public static String getPresetMessage(String messageKey) {
		String msg = Main.messages.getString(messageKey);
		if (msg == null) {
			msg = "Failed to get preset message from key: " + messageKey;
		}
		return msg;
	}
	
}
