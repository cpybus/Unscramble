package xxxteslaxxx.chris.unscramble.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import xxxteslaxxx.chris.unscramble.Vars;

public class Command_Reload
{
	public static void reload(Player p)
	{
		Vars.plugin.loadYamls();
		p.sendMessage("§aConfig File Reloaded");
	}
	
	public static void reload(CommandSender p)
	{
		Vars.plugin.loadYamls();
		p.sendMessage("[Unscramble] Config File Reloaded");
	}
}