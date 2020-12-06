package com.gmail.justbru00.epic.ban.requests.main;

import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.justbru00.epic.ban.requests.bstats.Metrics;
import com.gmail.justbru00.epic.ban.requests.commands.RequestBanCommand;
import com.gmail.justbru00.epic.ban.requests.listeners.GUIListener;
import com.gmail.justbru00.epic.ban.requests.utils.*;

public class Main extends JavaPlugin {
	
	public static ConsoleCommandSender console = Bukkit.getConsoleSender();
	public static Logger logger = Bukkit.getLogger();
	public static String prefix = Messager.color("&8[&bEpic&fBanRequest&8] &f");
	private static Main plugin;
	private static int BSTATS_METRICS_ID = 404;
	public static PluginFile messages = null;
	
	@Override
	public void onDisable() {
		
		plugin = null;
	}
	
	
	@Override
	public void onEnable() {
		plugin = this;
		Messager.msgConsole("&aStarting to enable plugin...");
		Metrics bstats = new Metrics(this, BSTATS_METRICS_ID);
		
		saveDefaultConfig();
		
		messages = new PluginFile(this, "messages.yml", "messages.yml");
		
		Messager.msgConsole("&aSetting Plugin Prefix");
		prefix = Messager.color(this.getConfig().getString("prefix"));
		
		PluginManager pm = Bukkit.getServer().getPluginManager();
		pm.registerEvents(new GUIListener(), plugin);
		
		getCommand("requestban").setExecutor(new RequestBanCommand());
		
		Messager.msgConsole("&aPlugin enabled successfully!");
	}
	
	public static void notifyAdmins(BanRequest b) {
		String playerMessage = Messager.getPresetMessage("admin_notification.player");
		String consoleMessage = Messager.getPresetMessage("admin_notification.console");
		
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (p.hasPermission("requestban.notify")) {
				Messager.msgPlayer(replaceBanRequestTextVariables(playerMessage, b), p);
			}
		}
		Messager.msgConsole(replaceBanRequestTextVariables(consoleMessage, b));
	}
	
	public static String replaceBanRequestTextVariables(String msg, BanRequest request) {
		// {request_id}, {request_opener_name}, {request_player_to_ban_name}, {request_ban_reason}
		msg = msg.replace("{request_id}", String.valueOf(request.id));
		msg = msg.replace("{request_opener_name}", Bukkit.getOfflinePlayer(UUID.fromString(request.openerUUID)).getName());
		msg = msg.replace("{request_player_to_ban_name}", Bukkit.getOfflinePlayer(UUID.fromString(request.playerToBanUUID)).getName());
		msg = msg.replace("{request_ban_reason}", request.banReason);
		
		return msg;
	}

	
	public static Main getInstance() {
		return plugin;
	}
	
	public static int getPendingRequests() {
		
		FileConfiguration c = Main.getInstance().getConfig();
		
		int pending = 0;
		
		for (int i = 0; i <= c.getInt("current_id"); i++) {
			if (!c.getBoolean("ban_requests." + i + ".closed")) {
				pending = pending + 1;
			}
		}
		
		return pending;
	}

}
