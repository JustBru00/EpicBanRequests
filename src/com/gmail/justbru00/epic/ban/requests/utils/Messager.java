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
	
}
