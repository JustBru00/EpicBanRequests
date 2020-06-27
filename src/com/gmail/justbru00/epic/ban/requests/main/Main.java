package com.gmail.justbru00.epic.ban.requests.main;

import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.justbru00.epic.ban.requests.bstats.Metrics;
import com.gmail.justbru00.epic.ban.requests.listeners.GUIListener;
import com.gmail.justbru00.epic.ban.requests.utils.BanRequest;
import com.gmail.justbru00.epic.ban.requests.utils.GUIManager;
import com.gmail.justbru00.epic.ban.requests.utils.Messager;

public class Main extends JavaPlugin implements CommandExecutor{
	
	public static ConsoleCommandSender console = Bukkit.getConsoleSender();
	public static Logger logger = Bukkit.getLogger();
	public static String prefix = Messager.color("&8[&bEpic&fBanRequest&8] &f");
	private static Main plugin;
	private static int BSTATS_METRICS_ID = 404;

	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(command.getName().equalsIgnoreCase("requestban")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				
				if (player.hasPermission("requestban.use")) {									
					//Player can request a ban
					if (player.hasPermission("requestban.view")) {
						if (args.length == 1 && args[0].equalsIgnoreCase("view")) {
							player.openInventory(GUIManager.mainMenu());
							return true;
						} else if (args.length >= 2) {
							String playerToBanUUID = "";
							try {
								playerToBanUUID = Bukkit.getOfflinePlayer(args[0]).getUniqueId().toString();
								if (playerToBanUUID == null) {
									Messager.msgPlayer("&cThat player could not be found. Are they online?", player);
									return true;
								}
							} catch (Exception e) {
								Messager.msgPlayer("&cThat player could not be found.", player);
								return true;
							}
							
							StringBuilder sb = new StringBuilder(args[1]);
													
							for (int i = 2; i < args.length; i++) {
								sb.append(" " + args[i]);
							}
							
							BanRequest br = new BanRequest(player.getUniqueId().toString(), sb.toString(), playerToBanUUID);
							br.writeToConfig();
							Messager.msgPlayer("&aCreated ban request #" + br.id, player);
							notifyAdmins(br);
							return true;
						} else {
							Messager.msgPlayer("&cPlease use proper arguments. /requestban <player> <reason>", player);							
							return true;
						}						
					} // End /requestban view handling				
					
					if (args.length >= 2) {
						String playerToBanUUID = "";
						try {
							playerToBanUUID = Bukkit.getOfflinePlayer(args[0]).getUniqueId().toString();
							if (playerToBanUUID == null) {
								Messager.msgPlayer("&cThat player could not be found. Are they online?", player);
								return true;
							}
						} catch (Exception e) {
							Messager.msgPlayer("&cThat player could not be found.", player);
							return true;
						}
						
						StringBuilder sb = new StringBuilder(args[1]);
												
						for (int i = 2; i < args.length; i++) {
							sb.append(" " + args[i]);
						}
						
						BanRequest br = new BanRequest(player.getUniqueId().toString(), sb.toString(), playerToBanUUID);
						br.writeToConfig();
						Messager.msgPlayer("&aCreated ban request #" + br.id, player);
						notifyAdmins(br);
						return true;
						
					} else {
						Messager.msgPlayer("&cPlease use proper arguments. /requestban <player> <reason>", player);
						return true;
					}
					
				} else {
					Messager.msgPlayer("&cSorry you don't have permission.", player);
					return true;
				}
				
			} else {
				Messager.msgSender("&cSorry only players can use this command.", sender);
				return true;
			}
		}
		
		return false;
	}

	
	@Override
	public void onDisable() {
		
		plugin = null;
	}
	
	
	@Override
	public void onEnable() {
		plugin = this;
		Messager.msgConsole("&aEnable Starting!");
		
		Messager.msgConsole("&aStats start");
		Metrics bstats = new Metrics(this, BSTATS_METRICS_ID);
		
		saveDefaultConfig();
		
		Messager.msgConsole("&aSetting Prefix");
		prefix = Messager.color(this.getConfig().getString("prefix"));
		
		Bukkit.getServer().getPluginManager().registerEvents(new GUIListener(), plugin);
		
		Messager.msgConsole("&aEnable Finished!");
	}
	
	public static void notifyAdmins(BanRequest b) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (p.hasPermission("requestban.notify")) {
				Messager.msgPlayer("&a[#" + b.id + "] " + Bukkit.getOfflinePlayer(UUID.fromString(b.openerUUID)).getName() + 
						" just submitted a ban request -> Ban: " + Bukkit.getOfflinePlayer(UUID.fromString(b.playerToBanUUID)).getName() + " Reason: " + b.banReason, p);
			}
		}
		Messager.msgConsole("&a[#" + b.id + "] " + Bukkit.getOfflinePlayer(UUID.fromString(b.openerUUID)).getName() + 
				" just submitted a ban request -> Ban: " + Bukkit.getOfflinePlayer(UUID.fromString(b.playerToBanUUID)).getName() + " Reason: " + b.banReason);
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
