package com.gmail.justbru00.epic.ban.requests.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.justbru00.epic.ban.requests.main.Main;
import com.gmail.justbru00.epic.ban.requests.utils.BanRequest;
import com.gmail.justbru00.epic.ban.requests.utils.GUIManager;
import com.gmail.justbru00.epic.ban.requests.utils.Messager;

public class RequestBanCommand implements CommandExecutor {

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
									Messager.msgPlayerWithPresetMessage("commands.requestban.cannot_find_player_online", player);
									return true;
								}
							} catch (Exception e) {
								Messager.msgPlayerWithPresetMessage("commands.requestban.cannot_find_player", player);
								return true;
							}
							
							StringBuilder sb = new StringBuilder(args[1]);
													
							for (int i = 2; i < args.length; i++) {
								sb.append(" " + args[i]);
							}
							
							BanRequest br = new BanRequest(player.getUniqueId().toString(), sb.toString(), playerToBanUUID);
							br.writeToConfig();
							String varReplacedMsg = Main.replaceBanRequestTextVariables(Messager.getPresetMessage("commmands.requestban.created_ban_request"), br);
							Messager.msgPlayer(varReplacedMsg, player);
							Main.notifyAdmins(br);
							return true;
						} else {
							Messager.msgPlayerWithPresetMessage("commands.requestban.bad_arguments", player);							
							return true;
						}						
					} // End /requestban view handling				
					
					if (args.length >= 2) {
						String playerToBanUUID = "";
						try {
							playerToBanUUID = Bukkit.getOfflinePlayer(args[0]).getUniqueId().toString();
							if (playerToBanUUID == null) {
								Messager.msgPlayerWithPresetMessage("commands.requestban.cannot_find_player_online", player);
								return true;
							}
						} catch (Exception e) {
							Messager.msgPlayerWithPresetMessage("commands.requestban.cannot_find_player", player);
							return true;
						}
						
						StringBuilder sb = new StringBuilder(args[1]);
												
						for (int i = 2; i < args.length; i++) {
							sb.append(" " + args[i]);
						}
						
						BanRequest br = new BanRequest(player.getUniqueId().toString(), sb.toString(), playerToBanUUID);
						br.writeToConfig();
						String varReplacedMsg = Main.replaceBanRequestTextVariables(Messager.getPresetMessage("commmands.requestban.created_ban_request"), br);
						Messager.msgPlayer(varReplacedMsg, player);
						Main.notifyAdmins(br);
						return true;
						
					} else {
						Messager.msgPlayerWithPresetMessage("commands.requestban.bad_arguments", player);
						return true;
					}
					
				} else {
					Messager.msgPlayerWithPresetMessage("commands.requestban.no_permission", player);
					return true;
				}				
			} else {
				Messager.msgSenderWithPresetMessage("commands.requestban.no_console", sender);
				return true;
			}
		}
		
		return false;
	}

}
